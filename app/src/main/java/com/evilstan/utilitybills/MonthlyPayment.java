package com.evilstan.utilitybills;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MonthlyPayment {

    private final List<Gauge> meters;

    private int month;
    private int year;

    private double totalMoneyValue;
    private Map<String, Double> utilityPaysValues;
    private List<String> utilityPays;
    private Tariff tariff;
    private final Double JOULE_TO_CALORIE_COEFFICIENT = 0.2388458966275;

    public MonthlyPayment() {
        this.meters = new ArrayList<>();
        this.tariff = new Tariff();
        this.month = 0;
        this.year = 2021;
    }

    public MonthlyPayment(List<Gauge> meters, Tariff tariff) {
        this.meters = meters;
        this.tariff = tariff;
    }


    public void calculateExpenses() {
        double metersCost = 0;
        double utilityCost = 0;

        for (Gauge m : meters) {
            metersCost += calculateMetersExpenses(m);
        }

        for (Double value : tariff.getUtilityPayments().values()) {
            utilityCost += value;
        }
        this.totalMoneyValue = metersCost + utilityCost;
    }


    public double getTotalMoneyValue() {
        return totalMoneyValue;
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

    private double calculateMetersExpenses(Gauge gauge) {
        MeterType meterType = gauge.getType();
        double value = gauge.getValue();

        switch (meterType) {
            case GAS:
                return value * tariff.getGasCost();
            case HEAT_CALORIE:
                return value * tariff.getHeatCost();
            // in case of joule heat meter convert to calories
            case HEAT_JOULE:
                return value * tariff.getHeatCost() * JOULE_TO_CALORIE_COEFFICIENT;
            //in case of hot water calculate hot water supply and waste water drain
            case HOT_WATER:
                return value * (tariff.getHotWaterDrainCost() + tariff.getHotWaterCost());
            case COLD_WATER:
                return value * tariff.getColdWaterCost();
            case ELECTRICITY:
                return value * tariff.getElectricityCost();

            default:
                return 0;
        }
    }


}
