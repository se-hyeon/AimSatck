package com.example.sehyeon.aimstack;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class OngoingPage extends AppCompatActivity {

    String TAG = "ongoing page";
    private SQLiteDatabase db;
    private OneAim aim;
    private Integer position;

    private Timer timer;
    private TimerTask timerTask;
    private int hour, minute, sec;
    private String time;
    private int totalSec;

    private TextView timerTextView;
    private TextView ongoingTitle;
    private Button stopButton;

    com.example.sehyeon.aimstack.ProgressCircle progressCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        setContentView(R.layout.activity_ongoing_page);

        Bundle bundle = getIntent().getExtras();

        aim = new OneAim();
        db = openOrCreateDatabase("myDatabase", MODE_WORLD_READABLE, null);
        position = (Integer) bundle.get("thisAim");

        loadItem();

        timer = new Timer();
        timerTextView = (TextView) findViewById(R.id.timer);

        ongoingTitle = (TextView) findViewById(R.id.ongoingTitle);
        ongoingTitle.setText(aim.getTitle());

        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                updateItem();
            }
        });

        progressCircle = (com.example.sehyeon.aimstack.ProgressCircle)findViewById(R.id.progressView);
        progressCircle.setDoingSec(Integer.parseInt(aim.getDoingSec()));
        progressCircle.setTotalSec(Integer.parseInt(aim.getTime())*3600);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        updateItem();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState");
    }
    @Override
    protected void onResume() {
        super.onResume();
        timer.cancel();
        timer =null;
        timer = new Timer();
        Log.i(TAG, "onResume");
        totalSec = Integer.parseInt(aim.getDoingSec());

        timerTask = new TimerTask() {
            @Override
            public void run() {

                hour = totalSec / 3600;
                minute = totalSec % 3600 / 60;
                sec = totalSec % 3600 % 60;

                time = hour + ":" + minute + ":" + sec;

                totalSec++;
                aim.setDoingSec(totalSec + "");

                progressCircle.setDoingSec(Integer.parseInt(aim.getDoingSec()));
                progressCircle.setTotalSec(Integer.parseInt(aim.getTime()) * 3600);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timerTextView.setText(time);
                        progressCircle.invalidate();
                  //      Log.d("timer", time);
                    }
                });

            }
        };
        timer.schedule(timerTask, 100, 1000);

    }

    private void updateItem() {
        aim.setDoingSec(totalSec + "");

        String sql = "update myTable set doing_sec=" + totalSec
                + " WHERE title='" + aim.getTitle() +"';";
/*
                + "', time="+ aim.getTime()
                + "', start_year='"+ aim.getStartYear()
                + "', start_month='"+ aim.getStartMonth()
                + "', start_day='"+ aim.getStartDay()
                + "', end_year='"+ aim.getEndYear()
                + "', end_month='"+ aim.getEndMonth()
                + "', end_day='"+ aim.getEndDay()
                +"';";
*/
        db.execSQL(sql);

        loadItem();
    }

    private void loadItem() {

        int recordCount;
        Cursor cursor = db.rawQuery("select * from myTable ", null);

        recordCount = cursor.getCount();
    //    Log.d("-----", "count of all items : " + recordCount);

        if (recordCount < 1) {
            return;
        }

        for (int i = 0; i < position + 1; i++)
            cursor.moveToNext();

        aim.setTitle(cursor.getString(0));
        aim.setTime(cursor.getString(1));
        aim.setStartYear(cursor.getString(2));
        aim.setStartMonth(cursor.getString(3));
        aim.setStartDay(cursor.getString(4));
        aim.setEndYear(cursor.getString(5));
        aim.setEndMonth(cursor.getString(6));
        aim.setEndDay(cursor.getString(7));
        aim.setDoingSec(cursor.getString(8));

        //  Log.d("--", "title : " + cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4) + ", " + cursor.getString(5) + ", " + cursor.getString(6) + ", " + cursor.getString(7) + ", " + cursor.getString(8));

        cursor.close();
    }

}
