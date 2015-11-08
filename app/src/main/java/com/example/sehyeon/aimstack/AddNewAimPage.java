package com.example.sehyeon.aimstack;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

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
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date selectedDate;

    final int START_DAY_FLAG = 1;
    final int END_DAY_FLAG = 2;
    int DATE_SETTING_BUTTON_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_aim_page);

        aimNameEditText = (EditText)findViewById(R.id.aimEditText);
        aimTimeEditText = (EditText)findViewById(R.id.timeEditText);
        startDayButton = (Button)findViewById(R.id.startDayButton);
        startDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATE_SETTING_BUTTON_FLAG=START_DAY_FLAG;
                showDialog(START_DAY_FLAG);
            }
        });

        endDayButton = (Button)findViewById(R.id.endDayButton);
        endDayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DATE_SETTING_BUTTON_FLAG=END_DAY_FLAG;
                showDialog(END_DAY_FLAG);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        String dateString;
               switch(id) {
                   case START_DAY_FLAG :
                       dateString  = startDayButton.getText().toString();
                       break;
                   case END_DAY_FLAG :
                       dateString  = endDayButton.getText().toString();
                       break;
                   default:
                       dateString  = null;
                       break;
               }
                Calendar calendar = Calendar.getInstance();
                Date currentDate = new Date();
                try {
                    currentDate = dateFormat.parse(dateString);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }

                calendar.setTime(currentDate);

                int curYear = calendar.get(Calendar.YEAR);
                int curMonth = calendar.get(Calendar.MONTH);
                int curDay = calendar.get(Calendar.DAY_OF_MONTH);

                return new DatePickerDialog(this,  dateSetListener,  curYear, curMonth, curDay);
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
        switch(DATE_SETTING_BUTTON_FLAG)
        {
            case START_DAY_FLAG:
                startDayButton.setText(selectedDateStr);
                break;
            case END_DAY_FLAG :
                endDayButton.setText(selectedDateStr);
                break;


        }

    }
}
