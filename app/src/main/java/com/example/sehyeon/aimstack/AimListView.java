package com.example.sehyeon.aimstack;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by sehyeon on 2015-11-15.
 */
public class AimListView extends RelativeLayout{

    TextView titleTextView;
    TextView dateTextView;

    public AimListView(Context context) {
        super(context);
    }
    public AimListView(Context context, OneAimInList oneAimInList) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.one_item, this, true);

        titleTextView=(TextView)findViewById(R.id.title);
        titleTextView.setText("ho");
        dateTextView=(TextView)findViewById(R.id.date);
        dateTextView.setText("yes!");
    }

    public void setTitle(String title){
        titleTextView.setText(title);
    }

    public void setDate(String date){
        dateTextView.setText(date);
    }

}
