package com.evilstan.utilitybills;


import java.util.LinkedHashMap;
import java.util.Map;

/** Represents cost of every item, that can bwe measured**/

public class Tariff {
    private double coldWaterCost;
    private double hotWaterCost;
    private double hotWaterDrainCost;
    private double heatCost;
    private double electricityCost;
    private double gasCost;
    private Map<String, Double> utilityPayments;

    public Tariff() {
        coldWaterCost = 0;
        hotWaterCost = 0;
        heatCost = 0;
        electricityCost = 0;
        gasCost = 0;
        utilityPayments = new LinkedHashMap<>();
    }

    //TODO make read from sharedPreferences

    public Map<String, Double> getUtilityPayments() {
        return utilityPayments;
    }

    public void addUtilityPayments(String name, double value) {
        utilityPayments.put(name, value);
    }

    public double getHotWaterDrainCost() {
        return hotWaterDrainCost;
    }

    public void setHotWaterDrainCost(double hotWaterDrainCost) {
        this.hotWaterDrainCost = hotWaterDrainCost;
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
