package com.evilstan.utilitybills;

public interface Gauge {

    int getId();

    void setId(int id);

    double getValue();

    void setValue(double value);

    String getPlacement();

    void setPlacement(String placement);

    void setType(MeterType type);

    MeterType getType();

}
