package com.evilstan.utilitybills.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.evilstan.utilitybills.R;
import com.github.appintro.AppIntroCustomLayoutFragment;
import com.google.android.material.switchmaterial.SwitchMaterial;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;

public class MyCustomFragment extends Fragment {
    private int layoutResId = 0;

    public MyCustomFragment(){
        super();
    }

    public MyCustomFragment(int layoutResId) {
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


    public static final class Companion {

        @JvmStatic
        @NotNull
        public final AppIntroCustomLayoutFragment newInstance(int layoutResId) {
            AppIntroCustomLayoutFragment customSlide = new AppIntroCustomLayoutFragment();
            Bundle args = new Bundle();
            args.putInt("layoutResId", layoutResId);
            customSlide.setArguments(args);
            return customSlide;
        }

        private Companion() {
        }

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
