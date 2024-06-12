package com.example.d308realproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308realproject.R;
import com.example.d308realproject.adapter.VacationAdapter;
import com.example.d308realproject.database.Repository;
import com.example.d308realproject.entities.Excursion;
import com.example.d308realproject.entities.Vacations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class VacationActivity extends AppCompatActivity {
    private Repository repository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationActivity.this, VacationDetails.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView=findViewById(R.id.recyclerview);
        repository = new Repository(getApplication());
        List<Vacations> allVacations = repository.getmAllVacations();
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.vacationOne) {
//            Toast.makeText(VacationActivity.this, "put in sample data", Toast.LENGTH_LONG).show();

            repository = new Repository(getApplication());
            Vacations vacations = new Vacations(0, "Miami", 2200.0);
            repository.insert(vacations);
            vacations = new Vacations(1, "Dubai", 5000.0);
            repository.insert(vacations);
            Excursion excursion = new Excursion(0, "snorkeling", 15.0, 1);
            repository.insert(excursion);
            excursion = new Excursion(1, "bus tour", 10.0, 1);
            repository.insert(excursion);

//            List<Vacations> vacationLog = repository.getmAllVacations();
//            Log.d("MainActivity", "Vacations: " + vacationLog.size());
//            Log.d("MainActivity", "Inserting Dubai and Bus Tour");
//
//            // Fetch data to verify insertion
//            new Thread(() -> {
//                List<Vacations> vacationsLogged = repository.getmAllVacations();
//                Log.d("MainActivity", "Vacations size: " + vacationsLogged.size());
//
//                // Check if specific entries are inserted
//                for (Vacations vacation : vacationsLogged) {
//                    Log.d("MainActivity", "Vacation: " + vacation.getVacationName());
//                }
//            }).start();

            return true;
        }
        if(item.getItemId()==android.R.id.home) {
            this.finish();
//            Intent intent = new Intent(VacationActivity.this, VacationDetails.class);
//            startActivity(intent);
            return true;
        }
        return true;
    }

    @Override
    protected void onResume() {

        super.onResume();
        List<Vacations> allVacations = repository.getmAllVacations();
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        vacationAdapter.setVacations(allVacations);
    }
}