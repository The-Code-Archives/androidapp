package com.example.d308realproject.database;

import android.app.Application;
import android.util.Log;

import com.example.d308realproject.dao.ExcursionDAO;
import com.example.d308realproject.dao.VacationDAO;
import com.example.d308realproject.entities.Excursion;
import com.example.d308realproject.entities.Vacations;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {
    private ExcursionDAO mexcursionDAO;
    private VacationDAO mvacationDAO;

    private List<Vacations> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS=4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        AppDatabase db=AppDatabase.getDatabase(application);
        mexcursionDAO=db.excursionDAO();
        mvacationDAO=db.vacationDAO();
    }

    public List<Vacations>getmAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations=mvacationDAO.getAllVacations();
            Log.d("Repository", "All Vacations: " + mAllVacations.toString());
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }
    public List<Excursion>getmAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions=mexcursionDAO.getAllExcursion();
            Log.d("Repository", "All Vacations: " + mAllExcursions.toString());
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public List<Excursion>getAssociatedExcursion(int vacationID) {
        databaseExecutor.execute(() -> {
            mAllExcursions=mexcursionDAO.getAllAssociatedExcursion(vacationID);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    public void delete(Vacations vacations) {
        databaseExecutor.execute(() -> {
            mvacationDAO.delete(vacations);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void update(Vacations vacations) {
        databaseExecutor.execute(() -> {
            mvacationDAO.update(vacations);
        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void insert(Vacations vacations) {
        databaseExecutor.execute(() -> {
            mvacationDAO.insert(vacations);
        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mexcursionDAO.update(excursion);
        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mexcursionDAO.insert(excursion);
        });
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mexcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
