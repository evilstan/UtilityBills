package com.evilstan.utilitybills.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.evilstan.utilitybills.R;
import com.evilstan.utilitybills.interfaces.OnValuesSetListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.List;

public class MetersValuesPickerDialog extends DialogFragment {

    private LinearLayout rootLayout;
    private LinearLayout.LayoutParams layoutParams;
    private List<String> metersList;
    private OnValuesSetListener listener;
    private List<Double> values;
    private List<TextInputEditText> textInputs;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        values = new ArrayList<>();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT);

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialog = inflater.inflate(R.layout.meters_values_picker_dialog, null);
        rootLayout = dialog.findViewById(R.id.root_layout);
        rootLayout.setLayoutParams(layoutParams);
        addPositions();

        builder.setView(dialog)
            .setTitle(R.string.meters_values_picker_title)
            .setPositiveButton(R.string.ok, new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    for (TextInputEditText t : textInputs) {
                        String s = t.getText().toString();
                        double d = Double.parseDouble(s);
                        values.add(d);
                    }
                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    listener.onSet(values);
                }
            })
            .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    public void setListener(OnValuesSetListener listener) {
        this.listener = listener;
    }

    public void setMeters(List<String> meters) {
        this.metersList = meters;
    }


    public void addPositions() {
        int rows = metersList.size();

        for (int i = 0; i < rows; i++) {
            TextInputLayout textInputLayout = new TextInputLayout(getActivity());
            TextInputEditText textInputEditText = new TextInputEditText(getActivity());
            textInputs.add(textInputEditText);
            textInputEditText.setInputType(InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_FLAG_DECIMAL);

            textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    textInputLayout.setHintEnabled(false);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            textInputLayout.setLayoutParams(layoutParams);
            textInputEditText.setHint(metersList.get(i));

            textInputLayout.addView(textInputEditText);
            rootLayout.addView(textInputLayout);
        }

    }

}
