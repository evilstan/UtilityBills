package com.evilstan.utilitybills;

public class Meter implements Gauge {

    private MeterType type;
    private int id;
    private double value;
    private String placement;

    public Meter(){
        this.id = 1;
        this.value = 0.0;
        this.placement = "";
        this.type = MeterType.COLD_WATER;
    }

    public Meter(int id, double value, String placement, MeterType type) {
        this.id = id;
        this.value = value;
        this.placement = placement;
        this.type = type;
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
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
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
    public void setType(MeterType type) {
        this.type = type;
    }

    @Override
    public MeterType getType() {
        return this.type;
    }

}
