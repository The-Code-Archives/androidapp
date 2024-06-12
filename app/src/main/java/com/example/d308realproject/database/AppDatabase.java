package com.example.d308realproject.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.d308realproject.dao.ExcursionDAO;
import com.example.d308realproject.dao.VacationDAO;
import com.example.d308realproject.entities.Excursion;
import com.example.d308realproject.entities.Vacations;

@Database(entities = {Vacations.class, Excursion.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if(INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if(INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "MyAppDatabase.db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return INSTANCE;
    }

}
