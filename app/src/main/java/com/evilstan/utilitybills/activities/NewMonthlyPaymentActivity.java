package com.evilstan.utilitybills.activities;

import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.evilstan.utilitybills.MonthYearPickerDialog;
import com.evilstan.utilitybills.R;

public class NewMonthlyPaymentActivity extends AppCompatActivity implements OnDateSetListener {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_monthly_payment);

    }


    private void getDate(){
        //Month and Year picker dialog
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(this);
        pd.show(getSupportFragmentManager(), "MonthYearPickerDialog");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}