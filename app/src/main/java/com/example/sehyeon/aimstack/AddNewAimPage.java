package com.example.sehyeon.aimstack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNewAimPage extends AppCompatActivity {

    EditText aimNameEditText;
    EditText aimTimeEditText;
    Button startDayButton;
    Button endDayButton;
    Button saveAimButton;

    String aimName;
    String aimTime;

    int startYear;
    int startMonth;
    int startDay;

    int endYear;
    int endMonth;
    int endDay;

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date selectedDate;

    final int START_DAY_FLAG = 1;
    final int END_DAY_FLAG = 2;
    int DATE_SETTING_BUTTON_FLAG;
    int ERROR_FLAG;

    private SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_aim_page);

        aimNameEditText = (EditText) findViewById(R.id.aimEditText);
        aimTimeEditText = (EditText) findViewById(R.id.timeEditText);
        startDayButton = (Button) findViewById(R.id.startDayButton);
        startDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATE_SETTING_BUTTON_FLAG = START_DAY_FLAG;
                showDialog(START_DAY_FLAG);
            }
        });

        endDayButton = (Button) findViewById(R.id.endDayButton);
        endDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATE_SETTING_BUTTON_FLAG = END_DAY_FLAG;
                showDialog(END_DAY_FLAG);
            }
        });

        saveAimButton = (Button) findViewById(R.id.saveNewAimButton);
        saveAimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aimName = aimNameEditText.getText().toString();
                aimTime = aimTimeEditText.getText().toString();
              //  Toast.makeText(getApplicationContext(), startYear + "-" + startMonth + "-" + startDay, Toast.LENGTH_LONG).show();

                db = openOrCreateDatabase("myDatabase", MODE_WORLD_READABLE, null);

                insertRecord(db, aimName, aimTime, startYear, startMonth, startDay, endYear, endMonth, endDay, 5000);

                finish();
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        String dateString;
        switch (id) {
            case START_DAY_FLAG:
                dateString = startDayButton.getText().toString();
                break;
            case END_DAY_FLAG:
                dateString = endDayButton.getText().toString();
                break;
            default:
                dateString = null;
                break;
        }
        Calendar calendar = Calendar.getInstance();
        Date currentDate = new Date();
        try {
            currentDate = dateFormat.parse(dateString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        calendar.setTime(currentDate);


        switch (id) {
            case START_DAY_FLAG:
                startYear = calendar.get(Calendar.YEAR);
                startMonth = calendar.get(Calendar.MONTH) + 1;
                startDay = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(this, dateSetListener, startYear, startMonth-1, startDay);
            case END_DAY_FLAG:
                endYear = calendar.get(Calendar.YEAR);
                endMonth = calendar.get(Calendar.MONTH) + 1;
                endDay = calendar.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(this, dateSetListener, endYear, endMonth-1, endDay);
            default:
                return null;
        }
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, monthOfYear);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Date curDate = selectedCalendar.getTime();
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        selectedDate = curDate;
        String selectedDateStr = dateFormat.format(curDate);
        switch (DATE_SETTING_BUTTON_FLAG) {
            case START_DAY_FLAG:
                startDayButton.setText(selectedDateStr);
                break;
            case END_DAY_FLAG:
                endDayButton.setText(selectedDateStr);
                break;
        }
    }

    private void insertRecord(SQLiteDatabase _db, String title, String time, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, int doingSec) {
        try {
            _db.execSQL("insert into " + "myTable"
                    + "(TITLE, TIME, START_YEAR, START_MONTH, START_DAY, END_YEAR, END_MONTH, END_DAY, DOING_SEC) values ('" + title + "', '" + time + "', '" + startYear + "', '" + startMonth + "', '" + startDay + "','" + endYear + "','" + endMonth + "','" + endDay + "', '" + doingSec + "');");
            Log.d("OH!!!!", "insert!!!");
        } catch (Exception ex) {
            Log.e("fail to insert!", "Exception in executing insert SQL.", ex);
        }
        _db.close();
    }

}
