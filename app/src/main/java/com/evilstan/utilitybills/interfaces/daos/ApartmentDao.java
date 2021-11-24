package com.evilstan.utilitybills.interfaces.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.evilstan.utilitybills.data.Apartment;
import java.util.List;

@Dao
public interface ApartmentDao {

    @Query("SELECT * FROM apartment")
    List<Apartment> getAll();

    @Query("SELECT * FROM apartment WHERE name = :name")
    Apartment getByName(String name);

    @Query("SELECT * FROM apartment WHERE id = :id")
    Apartment getById(int id);

    @Insert
    void insert(Apartment apartment);

    @Update
    void update(Apartment apartment);

    @Delete
    void delete(Apartment apartment);
}
