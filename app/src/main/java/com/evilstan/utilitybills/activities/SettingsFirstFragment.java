package com.evilstan.utilitybills.activities;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.evilstan.utilitybills.R;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.ArrayList;
import java.util.List;

public class SettingsFirstFragment extends MyCustomFragment{

    public SettingsFirstFragment(){
        super();
    }

    public SettingsFirstFragment(int layoutResId) {
        super(layoutResId);
        this.layoutResId = layoutResId;
    }

    private int layoutResId = 0;
    private List<SwitchMaterial> switches;

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
        ViewGroup viewGroup = view.findViewById(R.id.switches_layout);
        switches = new ArrayList<>();

        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View v = viewGroup.getChildAt(i);

            if (v instanceof SwitchMaterial) {
                switches.add((SwitchMaterial) v);
            }

        }
    }

    public List<Boolean> getSwitchesState() {
        List<Boolean> switchesState = new ArrayList<>();
        for (SwitchMaterial s :switches){
            switchesState.add(s.isChecked());
        }
        return switchesState;
    }

}
