package com.evilstan.utilitybills.data;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import com.evilstan.utilitybills.enums.MeterType;
import com.evilstan.utilitybills.interfaces.Gauge;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity(tableName = "monthly_payment" ,foreignKeys = @ForeignKey(entity = Tariff.class,parentColumns = "apartment_id", childColumns = "ta"))
public class MonthlyPayment {

    private List<Gauge> meters;

    private int month;
    private int year;

    private double totalCost;
    private int tariffId;

    @Ignore
    private Tariff tariff;
    @Ignore
    private MetersValues metersValues;

    private final Double JOULE_TO_CALORIE_COEFFICIENT = 0.2388458966275;

    public MonthlyPayment() {

    }

    public MonthlyPayment(int month, int year, Apartment apartment) {
        this.meters = apartment.getMetersList();
        this.tariff = apartment.getTariff();
        this.month = month;
        this.year = year;
        metersValues = new MetersValues(month, year, meters);
    }

    public void calculateExpenses() {
        double utilityCost = calculateUtilityExpenses();
        double metersCost = calculateMetersExpenses();

        totalCost = metersCost + utilityCost;
    }


    private double calculateUtilityExpenses() {
        double result = 0;

        for (Double value : tariff.getUtilityPayments().values()) {
            result += value;
        }
        return result;
    }

    private double calculateMetersExpenses() {
        double cost = 0;
        Map<Integer, Double> values = metersValues.getValues();

        for (Integer key : values.keySet()) {
            double value = 0;
            Double d = values.get(key);
            if (d != null) {
                value = d;
            }

            int meterType = key / 100;

            switch (meterType) {
                case 1:
                    cost += value * tariff.getColdWaterCost();
                    break;
                case 2:
                    cost += value * tariff.getHotWaterCost();
                    break;
                case 3:
                    cost += value * tariff.getGasCost();
                case 4:
                    cost += value * tariff.getElectricityCost();
                case 5:
                    cost += value * tariff.getHeatCost();
                    break;
                case 6:
                    cost += value * tariff.getHeatCost() * JOULE_TO_CALORIE_COEFFICIENT;
                    break;
                default:
                    throw new IllegalArgumentException();
            } //switch

        } //for

        return cost;
    }

    private double calculateMetersExpenses(Gauge gauge) {
        MeterType meterType = gauge.getType();
        double value = gauge.getAmount();

        switch (meterType) {
            case GAS:
                return value * tariff.getGasCost();
            case HEAT_CALORIE:
                return value * tariff.getHeatCost();
            // in case of joule heat meter convert to calories
            case HEAT_JOULE:
                return value * tariff.getHeatCost() * JOULE_TO_CALORIE_COEFFICIENT;
            case HOT_WATER:
                return value * tariff.getHotWaterCost();
            case COLD_WATER:
                return value * tariff.getColdWaterCost();
            case ELECTRICITY:
                return value * tariff.getElectricityCost();

            default:
                return 0;
        }
    }

    public List<Gauge> getMeters() {
        return meters;
    }

    public void setMeters(List<Gauge> meters) {
        this.meters = meters;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getTariffId() {
        return tariffId;
    }

    public void setTariffId(int tariffId) {
        this.tariffId = tariffId;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public MetersValues getMetersValues() {
        return metersValues;
    }

    public void setMetersValues(MetersValues metersValues) {
        this.metersValues = metersValues;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public Tariff getTariffs() {
        return tariff;
    }

    public void setTariffs(Tariff tariff) {
        this.tariff = tariff;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }


}
