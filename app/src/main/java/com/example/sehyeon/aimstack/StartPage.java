package com.example.sehyeon.aimstack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class StartPage extends AppCompatActivity {

    Button startButton;
   // AimStackDatabase database;
    SQLiteDatabase database;
    int recordCount;
    int[] recordIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AimListPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
/*
        if(database !=null){
            database.close();
            database=null;
        }

        database = AimStackDatabase.getInstance(this);
        boolean isOpen = database.open();

        if(isOpen){
            Log.d("-----", "database is open");
        }
        else{
            Log.d("-----", "database is not open");
        }

        loadAllItems();
*/
        createDatabase("myDatabase");

    }

    private void createDatabase(String databaseName){
        //   database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);

        try {
            database = openOrCreateDatabase(databaseName, MODE_WORLD_READABLE, null);
            createTable("myTable");
            //databaseCreated = true;
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
                + " start_second, "
                + " end_second, "
                + " doing_sec);" );
        Log.d("YES!", "create table");
    }


}