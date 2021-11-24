package com.evilstan.utilitybills.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.appcompat.app.AppCompatActivity;
import com.evilstan.utilitybills.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.chip.Chip;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test extends AppCompatActivity implements OnEditorActionListener, OnItemClickListener {

    FlexboxLayout root;
    AutoCompleteTextView editText;
    ArrayAdapter<String> adapter;
    List<String> adapterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        init();
    }

    private void init() {
        root = findViewById(R.id.flex_box);
        editText = findViewById(R.id.input_payments_autocomplete);

        adapterList = new ArrayList<>(
            Arrays.asList(getResources().getStringArray(R.array.payments_names)));
        adapter = new ArrayAdapter<>(Test.this, android.R.layout.simple_list_item_1, adapterList);
        editText.setAdapter(adapter);
        editText.setOnEditorActionListener(this);
        editText.setOnItemClickListener(this);
        addNewChip("HelloWorld", root);
    }

    private void addNewChip(String string, FlexboxLayout flexboxLayout) {
        Chip chip = new Chip(Test.this);
        chip.setText(string);
        chip.setCloseIconVisible(true);
        chip.setClickable(true);
        chip.setCheckable(false);
        flexboxLayout.addView(chip, flexboxLayout.getChildCount() - 1);
        chip.setOnCloseIconClickListener(view -> flexboxLayout.removeView(chip));
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        addNewChip(textView.getText().toString(), root);
        textView.setText("");
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String s = adapterView.getItemAtPosition(i).toString();
        addNewChip(s,root);
        editText.setText("");
    }
}