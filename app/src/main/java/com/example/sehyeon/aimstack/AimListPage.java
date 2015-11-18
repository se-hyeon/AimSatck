package com.example.sehyeon.aimstack;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AimListPage extends AppCompatActivity {

    Button statisticsButton;
    Button addAimButton;

    ListView listView;
    AimListAdapter adapter;
    Resources res;

    private SQLiteDatabase db;
    private ArrayList<OneAim> aimList;

    int position;

    public void setPosition(int position){this.position=position;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aim_list_page);

        statisticsButton = (Button) findViewById(R.id.statisticsButton);
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StatisticsPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        addAimButton = (Button) findViewById(R.id.addAimButton);
        addAimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewAimPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

        db = openOrCreateDatabase("myDatabase", MODE_WORLD_READABLE, null);
        loadAllItems();

        listView = (ListView) findViewById(R.id.aimListView);
        adapter = new AimListAdapter(this);
        res = getResources();
        showList();

    }

    @Override
     protected void onResume() {
        super.onResume();
        loadAllItems();
        showList();
    }

    private void showList(){
        adapter.clean();
        if (aimList != null) {
            for (int i = 0; i < aimList.size(); i++) {
                String date = aimList.get(i).getStartYear() + "-" + aimList.get(i).getStartMonth() + "-" + aimList.get(i).getStartDay() + "~" + aimList.get(i).getEndYear() + "-" + aimList.get(i).getEndMonth() + "-" + aimList.get(i).getEndDay();
                String percent = (Integer.parseInt(aimList.get(i).getDoingSec())*100)/(Integer.parseInt(aimList.get(i).getTime())*3600)+"%";
                adapter.addItem(new OneAimInList(aimList.get(i).getTitle() + " "+aimList.get(i).getTime()+"시간", date, percent));
            }

        }

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), OngoingPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("thisAim", position);
                startActivity(intent);
            }
        });
        registerForContextMenu(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                setPosition(position);
                return false;
            }
        });

    }
    private void loadAllItems() {

        int recordCount;
        Cursor cursor = db.rawQuery("select * from myTable ", null);

        recordCount = cursor.getCount();
      //  Log.d("-----", "count of all items : " + recordCount);

        if (recordCount < 1) {
            return;
        }

        aimList = new ArrayList<>();

        while (cursor.moveToNext()) {

            OneAim aim = new OneAim();

            aim.setTitle(cursor.getString(0));
            aim.setTime(cursor.getString(1));
            aim.setStartYear(cursor.getString(2));
            aim.setStartMonth(cursor.getString(3));
            aim.setStartDay(cursor.getString(4));
            aim.setEndYear(cursor.getString(5));
            aim.setEndMonth(cursor.getString(6));
            aim.setEndDay(cursor.getString(7));
          //  aim.setDoingHour(cursor.getString(8));
          //  aim.setDoingMinute(cursor.getString(9));
            aim.setDoingSec(cursor.getString(8));

            aimList.add(aim);
        //    Log.d("--", "title : " + cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4) + ", " + cursor.getString(5) + ", " + cursor.getString(6) + ", " + cursor.getString(7) + ", " + cursor.getString(8));

        }
       // Log.d("-----", "size of list: " + aimList.size());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(aimList.get(position).getTitle());
        menu.add(0, v.getId(), 0, "기간 연장");
        menu.add(0, v.getId(), 0, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "기간 연장") {
            Toast.makeText(this, "기간 연장", Toast.LENGTH_SHORT).show();
        }
        else if (item.getTitle() == "삭제") {
            Toast.makeText(this, "삭제", Toast.LENGTH_SHORT).show();
        }
        else {
            return false;
        }
        return true;
    }
}
