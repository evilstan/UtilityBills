package com.evilstan.utilitybills;

import java.util.ArrayList;
import java.util.List;

public class Flat {


    private List<Gauge> meters;
    private List<MonthlyPayment> payments;
    private String address;
    private String name;
    private Tariff tariff;
    //TODO add list of payments to flat

    // other pays - utility, TV, Internet, Phone, etc...
    private final List<String> utilityPays;

    public Flat() {
        this.name = "";
        this.address = "";
        meters = new ArrayList<>();
        utilityPays = new ArrayList<>();
    }

    public Flat(String name, String address) {
        this.name = name;
        this.address = address;
        meters = new ArrayList<>();
        utilityPays = new ArrayList<>();
    }

    public void addMeter(Gauge gauge) {
        meters.add(gauge);
    }

    public void addUtilityPayment(String name) {
        utilityPays.add(name);
    }

    public List<Gauge> getMeters() {
        return meters;
    }

    public List<String> getUtilityPays() {
        return utilityPays;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addPayment(){
        payments.add(new MonthlyPayment(meters,tariff));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
