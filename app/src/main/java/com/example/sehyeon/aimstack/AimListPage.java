package com.example.sehyeon.aimstack;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AimListPage extends AppCompatActivity {

    Button statisticsButton;
    Button addAimButton;
    Button emailButton;

    ListView listView;
    AimListAdapter adapter;
    Resources res;

    private SQLiteDatabase db;
    private ArrayList<OneAim> aimList;

    int position;

    public void setPosition(int position) {
        this.position = position;
    }

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


        // http://codedb.tistory.com/entry/Android-%EC%95%94%EC%8B%9C%EC%A0%81-Intent-%EC%82%AC%EC%9A%A9-%EC%A0%84%ED%99%94%EA%B1%B8%EA%B8%B0-%EB%A9%94%EC%9D%BC%EB%B3%B4%EB%82%B4%EA%B8%B0-%EC%82%AC%EC%9A%A9%EC%9E%90%EC%9D%91%EC%9A%A9%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0
        emailButton = (Button) findViewById(R.id.email);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

         showDialog();
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

    private void showList() {
        adapter.clean();
        if (aimList != null) {
            for (int i = 0; i < aimList.size(); i++) {
                String date = aimList.get(i).getStartDate() + " ~ " + aimList.get(i).getEndDate();
                String percent = (Integer.parseInt(aimList.get(i).getDoingSec()) * 100) / (Integer.parseInt(aimList.get(i).getAimSec())) + "%";
                adapter.addItem(new OneAimInList(aimList.get(i).getTitle() + " " + aimList.get(i).getTime(), date, percent));
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
            aim.setStartDaySecond(cursor.getString(2));
            aim.setEndDaySecond(cursor.getString(3));
            aim.setDoingSec(cursor.getString(4));

            aimList.add(aim);
            Log.d("--", "title : " + cursor.getString(0) + ", " + cursor.getString(1) + ", " + cursor.getString(2) + ", " + cursor.getString(3) + ", " + cursor.getString(4));

        }
        // Log.d("-----", "size of list: " + aimList.size());
        cursor.close();
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
            extend();
        } else if (item.getTitle() == "삭제") {
            delete();
        } else {
            return false;
        }
        return true;
    }

    private void extend() {

        Log.d("extend", "extend 진입");
        Long extendEnd = Long.parseLong(aimList.get(position).getEndDaySecond()) + 604800;
        String sql = "delete from myTable"
                + " WHERE title='" + aimList.get(position).getTitle()
                + "' and time='" + aimList.get(position).getAimSec()
                + "' and start_second='" + aimList.get(position).getStartDaySecond()
                + "' and end_second='" + aimList.get(position).getEndDaySecond()
                + "';";
        db.execSQL(sql);

        sql = "insert into myTable"
                + "(TITLE, TIME, START_SECOND, END_SECOND, DOING_SEC) values ('" + aimList.get(position).getTitle()+ "', '" +aimList.get(position).getAimSec() + "', '"   + aimList.get(position).getStartDaySecond() + "','" +extendEnd + "', '" + aimList.get(position).getDoingSec() + "');";
        db.execSQL(sql);
        aimList.remove(position);

        loadAllItems();
        showList();
    }

    private void delete() {

        Log.d("delete", "delete 진입");

        if (Long.parseLong(aimList.get(position).getAimSec()) > Long.parseLong(aimList.get(position).getDoingSec())) {
            Toast.makeText(getApplicationContext(), "삭제하기 전에 완료해보세요!", Toast.LENGTH_SHORT).show();
        } else {
            String sql = "delete from myTable"
                    + " WHERE title='" + aimList.get(position).getTitle()
                    + "' and time='" + aimList.get(position).getAimSec()
                    + "' and start_second='" + aimList.get(position).getStartDaySecond()
                    + "' and end_second='" + aimList.get(position).getEndDaySecond()
                    + "';";
            db.execSQL(sql);

            aimList.remove(position);

            loadAllItems();
            showList();
        }
    }
    private void showDialog(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("메일로 현재 리스트를 보냅니다.");
        alert.setMessage("메일 주소를 입력해주세요.");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("보내기", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String mailBody="";

                for(int i=0; i<aimList.size(); i++)
                    mailBody = mailBody + aimList.get(i).getTitle()+" "+aimList.get(i).getTime()+" "+aimList.get(i).getStartDate()+"~"+aimList.get(i).getEndDate()+" "+aimList.get(i).getPercent()+"\n";

                sendMail(input.getText().toString(),mailBody);
            }
        });
        alert.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        AlertDialog dialog = alert.create();
        dialog.show();

    }
    private void sendMail(final String to, final String mailBody){

    new AsyncTask<String, Void, Boolean>(){
        @Override
        protected Boolean doInBackground(String... params) {
            GMailSender sender = new GMailSender();
            try {
                sender.sendMail(
                       mailBody,
                        to
                );
            } catch (Exception e) {
                Log.e("SendMail", e.getMessage(), e);
            }
            return null;
        }
    }.execute();

}

}
