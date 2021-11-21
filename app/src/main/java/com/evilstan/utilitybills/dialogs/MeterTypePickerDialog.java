package com.evilstan.utilitybills.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.evilstan.utilitybills.R;
import com.google.android.material.textfield.TextInputEditText;

public class MeterTypePickerDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private int id;
    private String placement;

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialog = inflater.inflate(R.layout.meters_picker_dialog, null);

        final TextInputEditText idPicker = dialog.findViewById(R.id.id_picker);
        final TextInputEditText placementPicker = dialog.findViewById(R.id.name_picker);
        final AutoCompleteTextView typeSelector = dialog.findViewById(R.id.type_selector);

        String[] types = getResources().getStringArray(R.array.meters_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
            R.layout.list_item, types);
        typeSelector.setAdapter(adapter);

        //final TextInputLayout typePicker = dialog.findViewById(R.id.select_type);
/*      final Spinner typePicker = dialog.findViewById(R.id.select_type_spinner);
        HintAdapter hintAdapter =
            new HintAdapter(getContext(), R.array.meters_types,android.R.layout.simple_spinner_item);
        typePicker.setSelection(hintAdapter.getCount());*/

        builder.setView(dialog)
            .setTitle(R.string.meter_type_picker_title)
            .setPositiveButton(R.string.add_meter, null)
            .setNegativeButton(R.string.cancel_selector, null);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
