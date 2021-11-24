package com.evilstan.utilitybills.activities;


import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.evilstan.utilitybills.R;
import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class SettingsSecondFragment extends MyCustomFragment implements
    CompoundButton.OnCheckedChangeListener,
OnEditorActionListener{

    private MaterialRadioButton mrb1;
    private MaterialRadioButton mrb2;
    private MaterialRadioButton mrb3;
    private MaterialRadioButton mrb4;

    private TextInputLayout textInputLayout1;
    private TextInputLayout textInputLayout2;

    private TextInputEditText editText1;
    private TextInputEditText editText2;

    private int layoutResId;

    public SettingsSecondFragment() {
        super();
    }

    public SettingsSecondFragment(int layoutResId) {
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
        init(view);
    }

    private void init(View view) {
        mrb1 = view.findViewById(R.id.rb_cw_one);
        mrb2 = view.findViewById(R.id.rb_cw_two);
        mrb3 = view.findViewById(R.id.rb_hw_one);
        mrb4 = view.findViewById(R.id.rb_hw_two);

        textInputLayout1 = view.findViewById(R.id.first_group_text);
        textInputLayout2 = view.findViewById(R.id.second_group_text);

        editText1 = view.findViewById(R.id.edit1);
        editText2 = view.findViewById(R.id.edit2);

        mrb1.setOnCheckedChangeListener(this);
        mrb2.setOnCheckedChangeListener(this);
        mrb3.setOnCheckedChangeListener(this);
        mrb4.setOnCheckedChangeListener(this);

        editText1.setOnEditorActionListener(this);
        editText2.setOnEditorActionListener(this);

        mrb1.setChecked(true);
        mrb3.setChecked(true);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (mrb2.isChecked() || mrb4.isChecked()) {
            textInputLayout1.setVisibility(View.VISIBLE);
            textInputLayout2.setVisibility(View.VISIBLE);
        } else {
            textInputLayout1.setVisibility(View.INVISIBLE);
            textInputLayout2.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        String id = getResources().getResourceEntryName(textView.getId());
        switch (id) {
            case "editText1":
                editText2.requestFocus();
                break;
            case "editText2":
                hideKeyboard();
                break;
        }
        return false;
    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    //Cold1, Hot1
    public List<Boolean> getRadiosState() {
        return new ArrayList<>(
            Arrays.asList(mrb1.isChecked(), mrb3.isChecked()));
    }

    public List<String> getPlacement() {
        String s1 = Objects.requireNonNull(editText1.getText()).toString();
        String s2 = Objects.requireNonNull(editText2.getText()).toString();
        return new ArrayList<>(Arrays.asList(s1, s2));

    }
}
