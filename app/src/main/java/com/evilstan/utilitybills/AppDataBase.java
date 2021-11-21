package com.evilstan.utilitybills;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.evilstan.utilitybills.interfaces.daos.ApartmentDao;
import com.evilstan.utilitybills.interfaces.daos.MeterDao;
import com.evilstan.utilitybills.interfaces.daos.TariffDao;

@Database(entities = {Meter.class, Apartment.class, Tariff.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract MeterDao meterDao();

    public abstract ApartmentDao apartmentDao();

    public abstract TariffDao tariffDao();
}
