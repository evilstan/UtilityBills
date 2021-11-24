package com.evilstan.utilitybills.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.evilstan.utilitybills.App;
import com.evilstan.utilitybills.AppDataBase;
import com.evilstan.utilitybills.R;
import com.evilstan.utilitybills.data.Apartment;
import com.evilstan.utilitybills.data.Meter;
import com.evilstan.utilitybills.data.Tariff;
import com.evilstan.utilitybills.enums.MeterType;
import com.evilstan.utilitybills.interfaces.Gauge;
import com.evilstan.utilitybills.interfaces.daos.ApartmentDao;
import com.evilstan.utilitybills.interfaces.daos.MeterDao;
import com.evilstan.utilitybills.interfaces.daos.TariffDao;
import com.github.appintro.AppIntro;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FirstLaunchSettingActivity extends AppIntro {

    List<Boolean> metersState;
    List<Boolean> isWaterMeterSingle;
    List<String> placement;
    List<String> payments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(false);
        setWizardMode(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        SettingsFirstFragment firstFragment = new SettingsFirstFragment(R.layout.settings_layout_1);
        addSlide(firstFragment);

        SettingsSecondFragment secondFragment = new SettingsSecondFragment(
            R.layout.settings_layout_2);
        addSlide(secondFragment);

        SettingsThirdFragment thirdFragment = new SettingsThirdFragment(R.layout.settings_layout_3);
        addSlide(thirdFragment);

        SettingsFourthFragment fourthFragment = new SettingsFourthFragment(
            R.layout.settings_layout_4);
        addSlide(fourthFragment);
    }

    private void createAccount() {
        List<Gauge> meters = createMeters();
        Tariff tariff = new Tariff();
        tariff.setUtilityPayments(getPayments());
        Apartment apartment = new Apartment("new", meters,tariff);

        AppDataBase db = App.getInstance().getDatabase();
        MeterDao meterDao = db.meterDao();
        TariffDao tariffDao = db.tariffDao();
        ApartmentDao apartmentDao = db.apartmentDao();

        int apartmentId = apartment.getId();
        tariff.setApartmentId(apartmentId);
        apartmentDao.insert(apartment);
        tariffDao.insert(tariff);

        for (Gauge meter : meters) {
            meter.setApartmentId(apartmentId);
            meterDao.insert((Meter) meter);
        }

    }

    private Map<String,Double> getPayments() {
        Map<String, Double> utilityPayments = new LinkedHashMap<>();
        for (String s : payments) {
            utilityPayments.put(s, 0.0);
        }
        return utilityPayments;
    }

    private List<Gauge> createMeters() {
        List<Gauge> gauges = new ArrayList<>();
        if (metersState.get(0)) {
            gauges.add(new Meter("default", MeterType.ELECTRICITY, 0, 0));
        }
        if (metersState.get(1)) {
            if (isWaterMeterSingle.get(0)) {
                gauges.add(new Meter("default", MeterType.HEAT_CALORIE, 0, 0));
            } else {
                gauges.add(new Meter(placement.get(0), MeterType.COLD_WATER, 0, 0));
                gauges.add(new Meter(placement.get(1), MeterType.COLD_WATER, 0, 0));
            }
        }
        if (metersState.get(2)) {
            if (isWaterMeterSingle.get(1)) {
                gauges.add(new Meter("default", MeterType.HEAT_CALORIE, 0, 0));
            } else {
                gauges.add(new Meter(placement.get(0), MeterType.HOT_WATER, 0, 0));
                gauges.add(new Meter(placement.get(1), MeterType.HOT_WATER, 0, 0));
            }
        }
        if (metersState.get(3)) {
            gauges.add(new Meter("default", MeterType.GAS, 0, 0));
        }
        if (metersState.get(4)) {
            gauges.add(new Meter("default", MeterType.HEAT_CALORIE, 0, 0));
        }
        return gauges;
    }


    @Override
    protected void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finish();
    }

    @Override
    protected void onNextPressed(Fragment currentFragment) {
        super.onNextPressed(currentFragment);

        if (currentFragment instanceof SettingsFirstFragment) {
            metersState = ((SettingsFirstFragment) currentFragment).getSwitchesState();
        } else if (currentFragment instanceof SettingsSecondFragment) {
            isWaterMeterSingle = ((SettingsSecondFragment) currentFragment).getRadiosState();
            if (isWaterMeterSingle.get(0) || isWaterMeterSingle.get(1)) {
                placement = ((SettingsSecondFragment) currentFragment).getPlacement();
            }
        } else if ((currentFragment instanceof SettingsThirdFragment)) {
            payments = ((SettingsThirdFragment) currentFragment).getPayments();
        }else if ((currentFragment instanceof SettingsFourthFragment)) {
            createAccount();
        }
    }

    @Override
    protected void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finish();
    }
}