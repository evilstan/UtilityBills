package com.evilstan.utilitybills.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.evilstan.utilitybills.App;
import com.evilstan.utilitybills.AppDataBase;
import com.evilstan.utilitybills.activities.MainActivity;
import com.evilstan.utilitybills.interfaces.Gauge;
import com.evilstan.utilitybills.interfaces.daos.MeterDao;
import com.evilstan.utilitybills.interfaces.daos.TariffDao;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "apartment")
public class Apartment {

    @PrimaryKey()
    private int id;

    private String name;

    @Ignore
    List<Gauge> metersList;

    @Ignore
    private Tariff tariff;

    public Apartment() {

    }

    public Apartment(String name, List<Gauge> metersIdList, Tariff tariff) {
        this.name = name;
        this.metersList = metersIdList;
        this.tariff = new Tariff();
        generateId();
    }

    private void getLists(){
        AppDataBase db = App.getInstance().getDatabase();
        MeterDao meterDao = db.meterDao();
        TariffDao tariffDao = db.tariffDao();
        metersList = new ArrayList<>();
        metersList.addAll(meterDao.getByApartmentId(id));
        tariff = tariffDao.getByApartmentId(id);
    }

    public void generateId() {
        Context context = MainActivity.context;
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(context);
        int newId = sPref.getInt("ApartmentID", 0);

        this.id = newId;
        newId++;
        SharedPreferences.Editor sPrefEdit = sPref.edit();
        sPrefEdit.putInt("ApartmentID", newId);
        sPrefEdit.apply();
    }

    @Override
    public String toString() {
        String buf =
            "Apartment name = " + name + "; ID = " + id + "; \nTariff: " + tariff.toString()
                + "; \nMeters: ";
        for (Gauge meter : metersList) {
            buf += meter.toString();
        }

        return buf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        getLists();
    }

    public List<Gauge> getMetersList() {
        return metersList;
    }

    public void setMetersList(List<Gauge> metersList) {
        this.metersList = metersList;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
