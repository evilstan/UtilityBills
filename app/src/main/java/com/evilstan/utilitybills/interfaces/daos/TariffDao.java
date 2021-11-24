package com.evilstan.utilitybills.interfaces.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.evilstan.utilitybills.data.Tariff;
import java.util.List;

@Dao
public interface TariffDao {

    @Query("SELECT * FROM tariff WHERE apartment_id = :id")
    Tariff getByApartmentId(int id);

    @Query("SELECT * FROM tariff")
    List<Tariff> getAllTariffs();

    @Insert
    void insert(Tariff tariff);

    @Update
    void update(Tariff tariff);

    @Delete
    void delete(Tariff tariff);
}
