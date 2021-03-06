package com.evilstan.utilitybills.dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.evilstan.utilitybills.R;
import java.util.Calendar;
import java.util.TimeZone;

public class MonthYearPickerDialog extends DialogFragment {

    private static final int MIN_YEAR = 2020;
    private static final int MAX_YEAR = 2030;
    private DatePickerDialog.OnDateSetListener listener;

    public void setListener(DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        Calendar cal = Calendar.getInstance();

        View dialog = inflater.inflate(R.layout.date_picker_dialog, null);
        final NumberPicker monthPicker = dialog.findViewById(R.id.picker_month);
        final NumberPicker yearPicker = dialog.findViewById(R.id.picker_year);

        final String[] MONTH_NAMES = getResources().getStringArray(R.array.month_names);

        int month = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH);
        monthPicker.setMinValue(0);
        monthPicker.setMaxValue(11);
        monthPicker.setDisplayedValues(MONTH_NAMES);
        monthPicker.setValue(month);

        int year = cal.get(Calendar.YEAR);
        yearPicker.setMinValue(MIN_YEAR);
        yearPicker.setMaxValue(MAX_YEAR);
        yearPicker.setValue(year);
        String dialogTitle = getResources().getString(R.string.date_picket_title);

        builder.setView(dialog)
            .setTitle(dialogTitle)
            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    listener.onDateSet(null, yearPicker.getValue(), monthPicker.getValue(), 0);
                }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    MonthYearPickerDialog.this.getDialog().cancel();
                }
            });
        return builder.create();
    }
}
