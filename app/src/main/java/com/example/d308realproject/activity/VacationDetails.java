package com.example.d308realproject.activity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308realproject.R;
import com.example.d308realproject.adapter.ExcursionAdapter;
import com.example.d308realproject.database.Repository;
import com.example.d308realproject.entities.Excursion;
import com.example.d308realproject.entities.Vacations;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VacationDetails extends AppCompatActivity {

    String name;
    double price;
    int vacationID;
    EditText editName;
    EditText editPrice;
    Repository repository;

    Vacations currentVacation;

    int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);

        editName = findViewById(R.id.titletext);
        editPrice = findViewById(R.id.pricetext);
        vacationID = getIntent().getIntExtra("id", -1);
        name = getIntent().getStringExtra("name");
        price = getIntent().getDoubleExtra("price", 0.0);
        editName.setText(name);
        editPrice.setText(Double.toString(price));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
                intent.putExtra("vacationID", vacationID);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursion = new ArrayList<>();
        for(Excursion e : repository.getmAllExcursions()) {
            if(e.getVacationID() == vacationID) filteredExcursion.add(e);
        }
        excursionAdapter.setExcursion(filteredExcursion);




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacationdetails, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;
        }
        if(item.getItemId() == R.id.vacationsave) {
            Vacations vacations;
            if(vacationID == -1) {
                if (repository.getmAllVacations().size() == 0) vacationID = 1;
                else vacationID = repository.getmAllVacations().get(repository.getmAllVacations().size() - 1).getVacationID() + 1;
                vacations = new Vacations(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                repository.insert(vacations);
                this.finish();
            }
            else {
                vacations = new Vacations(vacationID, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()));
                repository.update(vacations);
                this.finish();
            }
            return true;
        }

        if(item.getItemId() == R.id.vacationdelete) {
            for(Vacations vac:repository.getmAllVacations()) {
                if(vac.getVacationID() == vacationID) currentVacation = vac;
            }
            num = 0;
            for(Excursion excursion:repository.getmAllExcursions()) {
                if(excursion.getVacationID() == vacationID) ++num;
            }
            if(num == 0) {
                repository.delete(currentVacation);
                Toast.makeText(VacationDetails.this, currentVacation.getVacationName() + " was deleted ", Toast.LENGTH_LONG).show();
                VacationDetails.this.finish();
            }
            else {
                Toast.makeText(VacationDetails.this, "Can't delete a vacation with excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionrecyclerview);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursion = repository.getAssociatedExcursion(vacationID);
        excursionAdapter.setExcursion(filteredExcursion);
    }

}