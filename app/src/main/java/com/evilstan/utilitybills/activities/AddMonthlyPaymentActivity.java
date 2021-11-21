package com.evilstan.utilitybills.activities;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.evilstan.utilitybills.Apartment;
import com.evilstan.utilitybills.App;
import com.evilstan.utilitybills.AppDataBase;
import com.evilstan.utilitybills.Calculator;
import com.evilstan.utilitybills.dialogs.MonthYearPickerDialog;
import com.evilstan.utilitybills.R;
import com.evilstan.utilitybills.interfaces.Gauge;
import com.evilstan.utilitybills.interfaces.daos.ApartmentDao;

public class AddMonthlyPaymentActivity extends AppCompatActivity implements OnDateSetListener {

    int apartmentId;
    Apartment apartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_monthly_payment);
        setTitle(R.string.add_payment);
        getDate();
        Bundle argument = getIntent().getExtras();
        apartmentId = argument.getInt("apartment_id");
    }

    private void calculate(int year, int month) {
        AppDataBase db = App.getInstance().getDatabase();
        ApartmentDao apartmentDao = db.apartmentDao();
        Apartment apartment = apartmentDao.getById(apartmentId);
        Calculator calculator = new Calculator(apartment);

    }

    private void getDate(){
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(this);
        pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int i2) {
        Toast.makeText(AddMonthlyPaymentActivity.this, "Year " + year + " Month " + month, Toast.LENGTH_LONG).show();
        calculate(year,month);
    }
}