package com.evilstan.utilitybills.activities;

import android.app.DatePickerDialog.OnDateSetListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.evilstan.utilitybills.data.Apartment;
import com.evilstan.utilitybills.App;
import com.evilstan.utilitybills.AppDataBase;
import com.evilstan.utilitybills.Calculator;
import com.evilstan.utilitybills.data.MetersValues;
import com.evilstan.utilitybills.dialogs.MetersValuesPickerDialog;
import com.evilstan.utilitybills.dialogs.MonthYearPickerDialog;
import com.evilstan.utilitybills.R;
import com.evilstan.utilitybills.enums.MeterType;
import com.evilstan.utilitybills.interfaces.Gauge;
import com.evilstan.utilitybills.interfaces.OnValuesSetListener;
import com.evilstan.utilitybills.interfaces.daos.ApartmentDao;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AddMonthlyPaymentActivity extends AppCompatActivity implements OnDateSetListener,
    OnValuesSetListener {

    private int apartmentId;
    private Apartment apartment;
    private List<Gauge> meters;
    private int year;
    private int month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_monthly_payment);
        setTitle(R.string.add_payment);
        init();
    }

    private void init() {
        getDate();
        Bundle argument = getIntent().getExtras();
        apartmentId = argument.getInt("apartment_id");
        AppDataBase db = App.getInstance().getDatabase();
        ApartmentDao apartmentDao = db.apartmentDao();
        apartment = apartmentDao.getById(apartmentId);
        meters = apartment.getMetersList();
    }

    private void calculate(int year, int month) {
        Calculator calculator = new Calculator(apartment);
    }

    private void getDate(){
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(this);
        pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }

    private void getMetersValues(){
        List<String> types = new ArrayList<>();

        for (Gauge meter : meters) {
            String type = selectTypeString(meter.getType());
            type += meter.getPlacement().toLowerCase(Locale.ROOT);
            types.add(type);
        }

        MetersValuesPickerDialog mvp = new MetersValuesPickerDialog();
        mvp.setMeters(types);
        mvp.setListener(this);
        mvp.show(getSupportFragmentManager(), "MetersValuesPickerDialog");
    }

    private String selectTypeString(MeterType meterType) {

        switch (meterType) {
            case HOT_WATER:
                return getResources().getString(R.string.of_hot_water);
            case COLD_WATER:
                return getResources().getString(R.string.of_cold_water);
            case HEAT_JOULE:
                return getResources().getString(R.string.of_heat_joules);
            case HEAT_CALORIE:
                return getResources().getString(R.string.of_heat_calories);
            case ELECTRICITY:
                return getResources().getString(R.string.of_electricity);
            case GAS:
                return getResources().getString(R.string.of_gas);
            default:
                System.out.println("Not defined, set to cold water");
                return getResources().getString(R.string.of_cold_water);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
        Toast.makeText(AddMonthlyPaymentActivity.this, "Year " + year + " Month " + month, Toast.LENGTH_LONG).show();
        this.year = year;
        this.month = month;
        getMetersValues();
    }

    @Override
    public void onSet(List<Double> values) {
        for (int i = 0; i < meters.size(); i++) {
            double currentValue = values.get(i);
            meters.get(i).setCurrentValue(currentValue);
        }
        apartment.setMetersList(meters);
        calculate(year,month);
    }
}