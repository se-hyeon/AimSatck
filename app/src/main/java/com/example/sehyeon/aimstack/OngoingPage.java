package com.example.sehyeon.aimstack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class OngoingPage extends AppCompatActivity {

    SQLiteDatabase db;
    OneAim aim;
    Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_page);

        Bundle bundle = getIntent().getExtras();
        aim = new OneAim();
        db = openOrCreateDatabase("myDatabase", MODE_WORLD_READABLE, null);
        position = (Integer) bundle.get("thisAim");

        // Toast.makeText(getApplicationContext(),"click position : "+bundle.get("thisAim"),Toast.LENGTH_LONG).show();

        loadItem();
        Toast.makeText(getApplicationContext(),"you are in : "+aim.getTitle(),Toast.LENGTH_LONG).show();
    }


    private void loadItem() {

        int recordCount;
        Cursor cursor = db.rawQuery("select * from myTable ", null);

        recordCount = cursor.getCount();
        Log.d("-----", "count of all items : " + recordCount);

        if (recordCount < 1) {
            return;
        }

        for (int i = 0; i < position+1; i++)
            cursor.moveToNext();

        aim.setTitle(cursor.getString(0));
        aim.setTime(cursor.getString(1));
        aim.setStartYear(cursor.getString(2));
        aim.setStartMonth(cursor.getString(3));
        aim.setStartDay(cursor.getString(4));
        aim.setEndYear(cursor.getString(5));
        aim.setEndMonth(cursor.getString(6));
        aim.setEndDay(cursor.getString(7));
        aim.setDoingHour(cursor.getString(8));
        aim.setDoingMinute(cursor.getString(9));
        aim.setDoingSec(cursor.getString(10));

        Log.d("--", "title : " + cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2));

    }

}
