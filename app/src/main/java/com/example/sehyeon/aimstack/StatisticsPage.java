package com.example.sehyeon.aimstack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StatisticsPage extends AppCompatActivity {

    private ProgressCircle view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new ProgressCircle(this);
        setContentView(R.layout.activity_statistics_page);
        //setContentView(view);
    }
}