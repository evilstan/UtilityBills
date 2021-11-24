package com.evilstan.utilitybills.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.evilstan.utilitybills.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SettingsThirdFragment extends MyCustomFragment implements
    OnEditorActionListener,
    OnItemClickListener ,
    OnClickListener {

    FlexboxLayout flexBoxChipsContainer;
    MaterialAutoCompleteTextView editText;
    ArrayAdapter<String> adapter;
    List<String> adapterList;
    ExtendedFloatingActionButton addButton;

    List<String> payments;

    Context context;

    int layoutResId;

    public SettingsThirdFragment() {
        super();
    }

    public SettingsThirdFragment(int layoutResId) {
        super(layoutResId);
        this.layoutResId = layoutResId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(
        LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = view.getContext();
        init(view);
    }

    private void init(View view) {
        flexBoxChipsContainer = view.findViewById(R.id.flex_box);
        editText = view.findViewById(R.id.input_payments_autocomplete);
        addButton = view.findViewById(R.id.add_payment_to_list);
        payments = new ArrayList<>();

        adapterList = new ArrayList<>(
            Arrays.asList(getResources().getStringArray(R.array.payments_names)));
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, adapterList);
        editText.setAdapter(adapter);
        editText.setOnEditorActionListener(this);
        editText.setOnItemClickListener(this);

        addButton.setOnClickListener(this);

        //editText.requestFocus();
    }


    public List<String> getPayments() {
        int children = flexBoxChipsContainer.getChildCount();
        for (int i = 0; i < children; i++) {
            Chip chip = (Chip)flexBoxChipsContainer.getChildAt(i);
            String text = chip.getText().toString();
            payments.add(text);
        }
        return payments;
    }

    private void addNewChip(String string, FlexboxLayout flexboxLayout) {
        Chip chip = new Chip(context);
        chip.setText(string);
        chip.setCloseIconVisible(true);
        chip.setClickable(true);
        chip.setCheckable(false);
        flexboxLayout.addView(chip, flexboxLayout.getChildCount());
        chip.setOnCloseIconClickListener(view -> flexboxLayout.removeView(chip));
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        addNewChip(textView.getText().toString(), flexBoxChipsContainer);
        textView.setText("");
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String s = adapterView.getItemAtPosition(i).toString();
        addNewChip(s, flexBoxChipsContainer);
        editText.setText("");
    }

    @Override
    public void onClick(View view) {
        addNewChip(editText.getText().toString(), flexBoxChipsContainer);
        editText.setText("");
    }
}
