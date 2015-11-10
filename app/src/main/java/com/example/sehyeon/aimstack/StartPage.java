package com.example.sehyeon.aimstack;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class StartPage extends AppCompatActivity {

    Button startButton;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);

        startButton = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AimListPage.class);
                startActivity(intent);
                finish();
            }
        });

        createDatabase("myDatabase");
     //   createTable("myTable");
    }

    private void createDatabase(String databaseName){
           database = openOrCreateDatabase(databaseName, MODE_PRIVATE, null);
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
                + " doing_time);" );
    }
}