package com.evilstan.utilitybills;

import com.evilstan.utilitybills.data.Apartment;
import com.evilstan.utilitybills.data.MetersValues;
import com.evilstan.utilitybills.data.Tariff;
import com.evilstan.utilitybills.enums.MeterType;
import com.evilstan.utilitybills.interfaces.Gauge;
import java.util.List;
import java.util.Map;

public class Calculator {

    private double totalCost;

    private Map<String, Double> utilityPayments;
    private List<Gauge> meters;
    private Tariff tariff;
    MetersValues metersValues;

    private final Double JOULE_TO_CALORIE_COEFFICIENT = 0.23885;


    public Calculator() {
        this.tariff = new Tariff();
    }

    public Calculator(Apartment apartment) {
        this.tariff = apartment.getTariff();
        this.meters = apartment.getMetersList();
        this.utilityPayments = tariff.getUtilityPayments();
    }


    public void calculateExpenses() {
        double metersCost = 0;
        double utilityCost = 0;

        for (Gauge meter : meters) {
            metersCost += calculateMetersExpenses(meter);
        }

        for (Double value : utilityPayments.values()) {
            utilityCost += value;
        }

        this.totalCost = metersCost + utilityCost;
    }

    private double calculateMetersExpenses(Gauge gauge) {
        MeterType meterType = gauge.getType();
        double value = gauge.getLastValue();

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
}
