package com.example.notesshvets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecordingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText editTitle;
    private EditText editSubtitle;
    private EditText editTextDate;
    private CheckBox checkDeadlineOn;
    private ImageButton imageBtnCalendar;
    private Calendar dateAndTime = Calendar.getInstance();
    private final String INTENT_DATA = "positionAdapter";
    private static String timeDate;
    private boolean usedNote = false;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recording, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ItemDataAdapter adapterPosition = new ItemDataAdapter(null);
        String title = editTitle.getText().toString();
        String subtitle = editSubtitle.getText().toString();
        String textDate = editTextDate.getText().toString();

        if (usedNote) {
            ItemData itemData = adapterPosition.getItem(position);

            adapterPosition.setItem(position, new ItemData(title,subtitle,textDate));
        } else{
            adapterPosition.addItem(new ItemData(title,subtitle,textDate));
        }
        return super.onOptionsItemSelected(item);
    }

    private void init() {
        editTitle = findViewById(R.id.editTitle);
        editSubtitle = findViewById(R.id.editSubtitle);
        editTextDate = findViewById(R.id.editTextDate);
        checkDeadlineOn = findViewById(R.id.checkDeadlineOn);
        imageBtnCalendar = findViewById(R.id.imageBtnDate);

        Intent intent = this.getIntent();
        position = intent.getIntExtra(INTENT_DATA, 0);
        if (position >= 0) {
            takeNoteInformation(position);
            usedNote = true;

        }


        checkDeadlineOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkDeadlineOn.isChecked()) {
                    dateAndTime = Calendar.getInstance();
                    setInitialDateTime();
                    editTextDate.setEnabled(true);
                    imageBtnCalendar.setClickable(true);

                } else {
                    editTextDate.setEnabled(false);
                    editTextDate.setText("");
                    imageBtnCalendar.setClickable(false);
                }
            }
        });


        imageBtnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(RecordingActivity.this, t,
                        dateAndTime.get(Calendar.HOUR_OF_DAY),
                        dateAndTime.get(Calendar.MINUTE), true)
                        .show();
                new DatePickerDialog(RecordingActivity.this, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();


            }
        });
        imageBtnCalendar.setClickable(false);
    }


    private void takeNoteInformation(int position) {
        ItemDataAdapter adapterPosition = new ItemDataAdapter(null);
        ItemData itemData = adapterPosition.getItem(position);
        if (!itemData.getTitle().equals(null)) {
            editTitle.setText(itemData.getTitle());
        }
        if (!itemData.getSubtitle().equals(null)) {
            editSubtitle.setText(itemData.getSubtitle());
        }
        if (!itemData.getTextDate().equals(null)) {
            checkDeadlineOn.setChecked(true);
            editTextDate.setText(itemData.getTextDate());
        }
    }

    final TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    final DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();

        }
    };

    private void setInitialDateTime() {

        timeDate = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME);

        if (!timeDate.equals(null)) {
            editTextDate.setText(timeDate);
        }
    }

}