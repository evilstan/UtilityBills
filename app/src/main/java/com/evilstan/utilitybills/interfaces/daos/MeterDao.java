package com.evilstan.utilitybills.interfaces.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.evilstan.utilitybills.data.Meter;
import java.util.List;

@Dao
public interface MeterDao {

    @Query("SELECT * FROM meter WHERE apartment_id = :id")
    List<Meter> getByApartmentId(int id);

    @Query("SELECT * FROM meter WHERE id = :id")
    Meter getById(int id);

    @Query("SELECT * FROM meter")
    List<Meter> getAllMeters();

    @Insert
    void insert(Meter meter);

    @Update
    void update(Meter meter);

    @Delete
    void delete(Meter meter);
}
