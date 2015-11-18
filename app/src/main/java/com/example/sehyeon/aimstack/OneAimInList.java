package com.example.sehyeon.aimstack;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by sehyeon on 2015-11-15.
 */
public class OneAimInList {

    //private TextView titleTextView;
    //private TextView dateTextView;
    private Drawable mIcon;
    private String[] mData;



    public OneAimInList(Drawable icon, String[] obj){
        mIcon=icon;
        mData=obj;
    }

    public OneAimInList( String title, String date, String percent){

        mData = new String[3];
        mData[0]=title;
        mData[1]=date;
        mData[2]=percent;
    }
    public OneAimInList(Drawable icon, String title, String date){
        mIcon=icon;
        mData = new String[2];
        mData[0]=title;
        mData[1]=date;
    }
/*
    public OneAimInList(TextView titleTextView, TextView dateTextView)
    {
        this.titleTextView=titleTextView;
        this.dateTextView=dateTextView;
    }

    public OneAimInList(TextView titleTextView, TextView dateTextView, String title, String date)
    {
        this.titleTextView=titleTextView;
        this.dateTextView=dateTextView;

        mData = new String[2];
        mData[0]=title;
        mData[1]=date;
    }
*/
    public void setData(String[] obj){
        mData = obj;
    }

    public void setmIcon(Drawable icon){
        mIcon=icon;
    }

    public String getData(int index){
        return mData[index];
    }


}
