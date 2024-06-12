package com.example.d308realproject.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308realproject.entities.Excursion;
import com.example.d308realproject.entities.Vacations;

import java.util.List;

@Dao
public interface VacationDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacations vacations);

    @Update
    void update(Vacations vacations);

    @Delete
    void delete(Vacations vacations);

    @Query("SELECT * FROM vacations ORDER BY vacationID ASC")
    List<Vacations> getAllVacations();

    @Query("SELECT * FROM vacations WHERE vacationID=:vac ORDER BY vacationID ASC;")
    List<Vacations> getAssociatedVacations(int vac);

}
