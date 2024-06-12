package com.example.d308realproject.activity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308realproject.R;
import com.example.d308realproject.database.Repository;
import com.example.d308realproject.entities.Excursion;
import com.example.d308realproject.MainActivity;
import com.example.d308realproject.entities.Vacations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    String name;
    Double price;
    int excursionId;
    int vacationId;
    EditText editName;
    EditText editPrice;
    EditText editNote;
    TextView editDate;
    Repository repository;
    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalendarStart = Calendar.getInstance();
    final String myDateFormat = "MM/dd/yy";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        repository = new Repository(getApplication());
        name = getIntent().getStringExtra("name");
        editName = findViewById(R.id.editExcursionTitle);
        editName.setText(name);
        price = getIntent().getDoubleExtra("price", -1.0);
        editPrice = findViewById(R.id.editExcursionPrice);
        editPrice.setText(Double.toString(price));
        excursionId = getIntent().getIntExtra("id", -1);
        vacationId = getIntent().getIntExtra("vacationID", -1);
        editNote = findViewById(R.id.editExcursionNote);
        editDate = findViewById(R.id.editExcursionDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myDateFormat, Locale.US);

        ArrayList<Vacations> vacationsArrayList = new ArrayList<>(repository.getmAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for(Vacations vacations : vacationsArrayList) {
            vacationIdList.add(vacations.getVacationID());
        }
        ArrayAdapter<Integer> vacationIdAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);

        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            ExcursionDetails.this.updateLabelStart();
        };

        editDate.setOnClickListener(view -> {
            String currentDate = editDate.getText().toString();

            if(currentDate.isEmpty())
                currentDate = simpleDateFormat.format(Calendar.getInstance().getTime());
            try {
                myCalendarStart.setTime(simpleDateFormat.parse(currentDate));
            } catch(ParseException e) {
                throw new RuntimeException(e);
            }
            new DatePickerDialog(ExcursionDetails.this, startDate, myCalendarStart.get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH), myCalendarStart.get(Calendar.DAY_OF_MONTH)).show();
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateLabelStart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myDateFormat, Locale.US);

        editDate.setText(simpleDateFormat.format(myCalendarStart.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.excursion_details, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if(item.getItemId() == R.id.saveExcursion) {
            Excursion excursion;
            if(excursionId == -1) {
                if (repository.getmAllExcursions().isEmpty())
                    excursionId = 1;
                else
                    excursionId = repository.getmAllExcursions().get(repository.getmAllExcursions().size() - 1).getId() + 1;
                excursion = new Excursion(excursionId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId);
                repository.insert(excursion);
                this.finish();
            } else {
                excursion = new Excursion(excursionId, editName.getText().toString(), Double.parseDouble(editPrice.getText().toString()), vacationId);
                repository.update(excursion);
                this.finish();
            }
            return true;
        }

        if (item.getItemId() == R.id.share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, editNote.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Message Title");
            sendIntent.setType("text/plain");
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if(item.getItemId() == R.id.notify) {
            String dataFromScreen = editDate.getText().toString();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myDateFormat, Locale.US);
            Date myDate;

            try {
                myDate = simpleDateFormat.parse(dataFromScreen);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            try {
                assert myDate != null;
                long trigger = myDate.getTime();
                Intent intent = new Intent(ExcursionDetails.this, MyReceiver.class);
                intent.putExtra("key", "Notification Message Successful!");
                PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}