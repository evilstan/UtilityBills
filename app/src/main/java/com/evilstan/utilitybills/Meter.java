package com.evilstan.utilitybills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import com.evilstan.utilitybills.activities.MainActivity;
import com.evilstan.utilitybills.enums.MeterType;
import com.evilstan.utilitybills.interfaces.Gauge;

@Entity(tableName = "meter",foreignKeys = @ForeignKey(entity = Apartment.class,parentColumns = "id", childColumns = "apartment_id"))
//@Entity(tableName = "meter")
public class Meter implements Gauge {

    //id for DB
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "serial_number")
    private int serialNumber;
    private MeterType type;
    @ColumnInfo(name = "current_value")
    private double currentValue;
    @ColumnInfo(name = "last_value")
    private double lastValue;
    private String placement;
    @ColumnInfo(name = "apartment_id")
    private int apartmentId;

    public Meter() {
    }

    public Meter(String placement, MeterType type, int serialNumber, double value) {
        this.placement = placement;
        this.type = type;
        this.serialNumber = serialNumber;
        this.id = generateId();
        this.lastValue = value;
    }

    private int generateId() {
        Context c = MainActivity.context;
        SharedPreferences sPref = PreferenceManager.getDefaultSharedPreferences(c);

        int identifier;
        int lastId = sPref.getInt("MeterID", 0);
        switch (this.type) {
            case COLD_WATER:
                identifier = 100;
                break;
            case HOT_WATER:
                identifier = 200;
                break;
            case GAS:
                identifier = 300;
                break;
            case ELECTRICITY:
                identifier = 400;
                break;
            case HEAT_CALORIE:
                identifier = 500;
                break;
            case HEAT_JOULE:
                identifier = 600;
                break;
            default:
                throw new IllegalArgumentException();
        }
        int result = identifier + lastId;
        lastId++;

        SharedPreferences.Editor editPref = sPref.edit();
        editPref.putInt("MeterID", lastId);
        editPref.apply();

        return result;
    }

    @Override
    public String toString() {
        String result;
        result = "Placement: " + placement + "; Type: " + type + "; ID: " + id +
            "; Manufacturer's ID: " + serialNumber + "; CurrentValue " + currentValue +
            "; Previous value: " + lastValue + "; ApartmentID: " + apartmentId;
        return result;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public double getAmount() {
        return currentValue - lastValue;
    }

    @Override
    public double getLastValue() {
        return lastValue;
    }

    @Override
    public void setLastValue(double lastValue) {
        this.lastValue = lastValue;
    }

    @Override
    public double getCurrentValue() {
        return currentValue;
    }

    @Override
    public void setCurrentValue(double currentValue) {
        this.lastValue = this.currentValue;
        this.currentValue = currentValue;
    }

    @Override
    public String getPlacement() {
        return placement;
    }

    @Override
    public void setPlacement(String placement) {
        this.placement = placement;
    }

    @Override
    public MeterType getType() {
        return this.type;
    }

    @Override
    public void setType(MeterType type) {
        this.type = type;
    }

    @Override
    public int getSerialNumber() {
        return serialNumber;
    }

    @Override
    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
    }

}
