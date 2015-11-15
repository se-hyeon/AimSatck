package com.example.sehyeon.aimstack;

import android.util.Log;

/**
 * Created by sehyeon on 2015-11-15.
 */
public class OneAim {

    private String title;
    private String time;
    private String startYear;
    private String startMonth;
    private String startDay;
    private String endYear;
    private String endMonth;
    private String endDay;
    private String doingHour;
    private String doingMinute;
    private String doingSec;

    public void check(){
        Log.d("진입","OK");

    }
    public void setTitle(String title) {
        this.title = title;

    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public void setStartMonth(String startMonth) {
        this.startMonth = startMonth;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public void setEndMonth(String endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public void setDoingHour(String doingHour) {
        this.doingHour = doingHour;
    }

    public void setDoingMinute(String doingMinute) {
        this.doingMinute = doingMinute;
    }

    public void setDoingSec(String doingSec) {
        this.doingSec = doingSec;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getStartMonth() {
        return startMonth;
    }

    public String getStartDay() {
        return startDay;
    }

    public String getEndYear() {
        return endYear;
    }

    public String getEndMonth() {
        return endMonth;
    }

    public String getEndDay() {
        return endDay;
    }

    public String getDoingHour() {
        return doingHour;
    }

    public String getDoingMinute() {
        return doingMinute;
    }

    public String getDoingSec() {
        return doingSec;
    }
}
