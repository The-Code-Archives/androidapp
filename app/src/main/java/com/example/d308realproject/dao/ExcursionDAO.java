package com.example.d308realproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308realproject.entities.Excursion;

import java.util.List;

@Dao
public interface ExcursionDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    @Update
    void update(Excursion excursion);

    @Delete
    void delete(Excursion excursion);

    @Query("SELECT * FROM excursion ORDER BY id ASC")
    List<Excursion> getAllExcursion();

    @Query("SELECT * FROM excursion WHERE vacationID=:vac ORDER BY id ASC")
    List<Excursion> getAllAssociatedExcursion(int vac);

}
