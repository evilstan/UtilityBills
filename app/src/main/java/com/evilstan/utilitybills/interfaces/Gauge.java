package com.evilstan.utilitybills.interfaces;

import androidx.room.Entity;
import com.evilstan.utilitybills.enums.MeterType;
@Entity
public interface Gauge {

    void setApartmentId(int id);

    int getId();

    void setId(int id);

    double getAmount();

    double getLastValue();

    void setLastValue(double value);

    double getCurrentValue();

    void setCurrentValue(double value);

    String getPlacement();

    void setPlacement(String placement);

    MeterType getType();

    void setType(MeterType type);

    int getSerialNumber();

    void setSerialNumber(int serialNumber);
}
