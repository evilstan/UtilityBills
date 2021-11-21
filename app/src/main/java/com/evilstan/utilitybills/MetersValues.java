package com.evilstan.utilitybills;

import android.os.Build.VERSION_CODES;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.evilstan.utilitybills.interfaces.Gauge;
import java.util.List;
import java.util.Map;

@Entity
public class MetersValues {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int year;
    private int month;

    private List<Gauge> meters;
    private Map<Integer, Double> values;

    public MetersValues(){

    }

    public MetersValues(int month, int year, List<Gauge> meters) {
        this.month = month;
        this.year = year;
        this.meters = meters;
        fillMap();
    }

    private void fillMap() {
        meters.forEach(meter ->{
            int id = meter.getId();
            double value = meter.getCurrentValue();
        });
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public List<Gauge> getMeters() {
        return meters;
    }

    public void setMeters(List<Gauge> meters) {
        this.meters = meters;
    }

    public Map<Integer, Double> getValues() {
        return values;
    }

    public void setValues(Map<Integer, Double> values) {
        this.values = values;
    }
}
