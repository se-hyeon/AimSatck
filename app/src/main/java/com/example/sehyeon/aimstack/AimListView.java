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
    TextView percentView;

    public AimListView(Context context) {
        super(context);
    }
    public AimListView(Context context, OneAimInList oneAimInList) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.one_item, this, true);

        titleTextView=(TextView)findViewById(R.id.title);
        dateTextView=(TextView)findViewById(R.id.date);
        percentView=(TextView)findViewById(R.id.showPercent);

    }

    public void setTitle(String title){
        titleTextView.setText(title);
    }

    public void setDate(String date){
        dateTextView.setText(date);
    }

    public void setPercent(String percent){percentView.setText(percent);}

}
