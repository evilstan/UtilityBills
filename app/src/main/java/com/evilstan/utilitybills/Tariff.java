package com.evilstan.utilitybills;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.evilstan.utilitybills.enums.MeterType;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents cost of every item, that can be measured
 **/

@Entity(tableName = "tariff", foreignKeys = @ForeignKey(entity = Apartment.class, parentColumns = "id", childColumns = "apartment_id"))
public class Tariff {

    @PrimaryKey
    @ColumnInfo(name = "apartment_id")
    private int apartmentId;
    @ColumnInfo(name = "cold_water_cost")
    private double coldWaterCost;
    @ColumnInfo(name = "hot_water_cost")
    private double hotWaterCost;
    @ColumnInfo(name = "heat_cost")
    private double heatCost;
    @ColumnInfo(name = "electricity_cost")
    private double electricityCost;
    @ColumnInfo(name = "gas_cost")
    private double gasCost;

    @TypeConverters({MapConverter.class})
    private Map<String, Double> utilityPayments;

/*    public Tariff(){
        utilityPayments = new LinkedHashMap<>();
    }*/

    public Tariff() {
/*        coldWaterCost = 0;
        hotWaterCost = 0;
        heatCost = 0;
        electricityCost = 0;
        gasCost = 0;
        utilityPayments = new LinkedHashMap<>();*/
    }

    //TODO make read from sharedPreferences

    public void setCost(MeterType meterType, Double cost) {
        switch (meterType) {
            case GAS:
                setGasCost(cost);
                break;
            case HEAT_CALORIE:
            case HEAT_JOULE:
                setHeatCost(cost);
                break;
            case HOT_WATER:
                setHotWaterCost(cost);
                break;
            case COLD_WATER:
                setColdWaterCost(cost);
                break;
            case ELECTRICITY:
                setElectricityCost(cost);
                break;
        }
    }

    @Override
    public String toString() {
        String s = "Hot water = " + hotWaterCost + "; Cold water = " + coldWaterCost + "; Heat = "
            + heatCost + "; Electricity = " + electricityCost + "; Gas = " + gasCost;
        for (String key : utilityPayments.keySet()) {
            s += "; " + key + " = " + utilityPayments.get(key);
        }
        return s;
    }

    public Map<String, Double> getUtilityPayments() {
        return utilityPayments;
    }

    public void setUtilityPayments(Map<String, Double> utilityPayments) {
        this.utilityPayments = utilityPayments;
    }

    public int getApartmentId() {
        return apartmentId;
    }

    public void setApartmentId(int apartmentId) {
        this.apartmentId = apartmentId;
    }

    public void addUtilityPayments(String name, double value) {
        utilityPayments.put(name, value);
    }

    public double getColdWaterCost() {
        return coldWaterCost;
    }

    public void setColdWaterCost(double coldWaterCost) {
        this.coldWaterCost = coldWaterCost;
    }

    public double getHotWaterCost() {
        return hotWaterCost;
    }

    public void setHotWaterCost(double hotWaterCost) {
        this.hotWaterCost = hotWaterCost;
    }

    public double getHeatCost() {
        return heatCost;
    }

    public void setHeatCost(double heatCost) {
        this.heatCost = heatCost;
    }

    public double getElectricityCost() {
        return electricityCost;
    }

    public void setElectricityCost(double electricityCost) {
        this.electricityCost = electricityCost;
    }

    public double getGasCost() {
        return gasCost;
    }

    public void setGasCost(double gasCost) {
        this.gasCost = gasCost;
    }
}
