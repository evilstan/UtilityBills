package com.evilstan.utilitybills;

import com.evilstan.utilitybills.enums.MeterType;
import com.evilstan.utilitybills.interfaces.Gauge;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonthlyPayment {

    private final List<Gauge> meters;

    private int month;
    private int year;

    private double totalCost;

    private Map<String, Double> utilityPaysValues;
    private Tariff tariff;
    MetersValues metersValues;

    private final Double JOULE_TO_CALORIE_COEFFICIENT = 0.2388458966275;

    public MonthlyPayment() {
        this.meters = new ArrayList<>();
        this.tariff = new Tariff();
        this.month = 0;
        this.year = 2021;
    }

    public MonthlyPayment(int month, int year, List<Gauge> meters, Tariff tariff) {
        this.meters = meters;
        this.tariff = tariff;
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
