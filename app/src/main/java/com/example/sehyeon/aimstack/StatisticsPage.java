package com.example.sehyeon.aimstack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class StatisticsPage extends AppCompatActivity {

    /*
    필요한 정보
        1. 총 갯수
        2. 완료한 것 갯수
        3. 완료하지 못한 것 갯수
    */

    private SQLiteDatabase db;

    private int totalCount;
    private int completedCount;
    private int uncompletedCount;
    private int completePercent;

    private TextView totalText;
    private TextView completedText;
    private TextView uncompletedText;
    private TextView percentText;

    private com.example.sehyeon.aimstack.StatisticsCircle percentView;
    private com.example.sehyeon.aimstack.StatisticsCircle totalView;
    private com.example.sehyeon.aimstack.StatisticsCircle completedView;
    private com.example.sehyeon.aimstack.StatisticsCircle uncompletedView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_page);

        init();

        completedCount = countCompleted();
        totalCount = countTotal();
        uncompletedCount = totalCount - completedCount;

        completePercent = (completedCount * 100 / totalCount);
        percentView.setPercent(completePercent);
        percentView.setColor("#FF323024");
        completedView.setColor("#FFFF5900");
        totalView.setColor("#FF1C639D");
        uncompletedView.setColor("#FF178A50");

        totalText.setText(totalCount + "");
        completedText.setText(completedCount + "");
        uncompletedText.setText(uncompletedCount + "");
        percentText.setText(completePercent + "%");
        // thread.start();
    }

    public void init() {
        db = openOrCreateDatabase("myDatabase", MODE_WORLD_READABLE, null);


        totalText = (TextView) findViewById(R.id.totalText);
        completedText = (TextView) findViewById(R.id.completedText);
        uncompletedText = (TextView) findViewById(R.id.uncompletedText);
        percentText = (TextView) findViewById(R.id.percentText);

        percentView = (com.example.sehyeon.aimstack.StatisticsCircle) findViewById(R.id.percentView);
        percentView.setPercentFlag(true);

        totalView = (com.example.sehyeon.aimstack.StatisticsCircle) findViewById(R.id.totalView);
        completedView  = (com.example.sehyeon.aimstack.StatisticsCircle) findViewById(R.id.completedView);
        uncompletedView  = (com.example.sehyeon.aimstack.StatisticsCircle) findViewById(R.id.uncompletedView);

    }

    private int countCompleted() {
        String sql = "select * from myTable where (cast(doing_sec as integer))>(cast(time as integer))";
        Cursor cursor = db.rawQuery(sql, null);

        int completedCount = cursor.getCount();
        Log.d("complete", cursor.getCount() + "");
        cursor.close();
        return completedCount;
    }

    private int countTotal() {
        String sql = "select * from myTable";
        Cursor cursor = db.rawQuery(sql, null);

        int totalCount = cursor.getCount();
        Log.d("total", cursor.getCount() + "");
        cursor.close();

        return totalCount;
    }


}