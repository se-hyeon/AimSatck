package com.example.sehyeon.aimstack;

import android.util.Log;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by sehyeon on 2015-11-15.
 */
public class OneAim {

    private String title;
    private String time;
    private String startDaySecond;
    private String endDaySecond;
    private String doingSec;

    public String getStartDaySecond() {
        return startDaySecond;
    }

    public void setStartDaySecond(String startDaySecond) {
        this.startDaySecond = startDaySecond;
        Log.d("setStartDaySecond",this.startDaySecond);
    }

    public String getEndDaySecond() {
        return endDaySecond;
    }

    public void setEndDaySecond(String endDaySecond) {
        this.endDaySecond = endDaySecond;
    }

    public String getStartDate() {
        long temp = Long.parseLong(startDaySecond)*1000;
        //Integer.parseInt(startDaySecond) * 1000;
        Date date = new Date(Long.parseLong(startDaySecond)*1000);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        int month =(calendar.get(Calendar.MONTH)+1);
        return calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getEndDate() {
        Date date = new Date(Long.parseLong(endDaySecond)*1000);
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        int month =(calendar.get(Calendar.MONTH)+1);
        return calendar.get(Calendar.YEAR) + "-" + month + "-" + calendar.get(Calendar.DAY_OF_MONTH);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
    Long convertTime = Long.parseLong(time);
        String converted = (convertTime/3600)+"시간 "+(convertTime%3600/60)+"분";
        Log.d("getTime", converted);
        return converted;
    }

    public String getAimSec(){
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }


    public String getDoingSec() {
        return doingSec;
    }

    public void setDoingSec(String doingSec) {
        this.doingSec = doingSec;
    }
}
