package com.example.sehyeon.aimstack;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.*;
import android.database.sqlite.*;
import android.graphics.AvoidXfermode;
import android.util.Log;

/**
 * Created by sehyeon on 2015-11-11.
 */
public class AimStackDatabase {

    public static final String DATABASE_NAME = "aimDatabase";
    public static final String TABLE_NAME = "aimTable";

    SQLiteDatabase database;

    private void createDatabase(){
        try {
      //      database = openOrCreateDatabase(DATABASE_NAME, MODE_WORLD_READABLE, null);

            createTable(TABLE_NAME);
            Log.d("database", "-----created-----");
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("database", "-----not created-----");
        }
    }

    private void createTable(String tableName){
        database.execSQL("create table " + tableName + "("
                + " title, "
                + " time, "
                + " start_year, "
                + " start_month, "
                + " start_day, "
                + " end_year, "
                + " end_month, "
                + " end_day, "
                + " doing_hour, "
                + " doing_minute, "
                + " doing_sec);" );
        Log.d("YES!", "create table");
    }
    public Cursor rawQuery(String SQL) {

        Cursor c1 = null;
        try {
            c1 = database.rawQuery(SQL, null);
            Log.d("---AimStack---", "cursor count : " + c1.getCount());

        } catch(Exception ex) {
            Log.e("---AimStack---", "Exception in executeQuery", ex);
        }

        return c1;
    }

    public boolean execSQL(String SQL) {

        try {
            Log.d("---AimStack---", "SQL : " + SQL);
            database.execSQL(SQL);
        } catch(Exception ex) {
            Log.e("---AimStack---", "Exception in executeQuery", ex);
            return false;
        }

        return true;
    }




    private void insertRecord(SQLiteDatabase _db, String title, int time, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay, int doingHour, int doingMinute, int doingSec) {
        try {
            _db.execSQL("insert into " + "myTable"
                    + "(TITLE, TIME, START_YEAR, START_MONTH, START_DAY, END_YEAR, END_MONTH, END_DAY, DOING_HOUR, DOING_MINUTE, DOING_SEC) values ('" + title + "', '" + time + "', '" + startYear + "', '" + startMonth + "', '" + startDay + "','" + endYear + "','" + endMonth + "','" + endDay + "', '" + doingHour + "','" + doingMinute + "', '" + doingSec + "');");
            Log.d("OH!!!!", "insert!!!");
        } catch (Exception ex) {
            Log.e("fail to insert!", "Exception in executing insert SQL.", ex);
        }
    }
}




