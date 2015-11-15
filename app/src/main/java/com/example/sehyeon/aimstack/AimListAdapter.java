package com.example.sehyeon.aimstack;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sehyeon on 2015-11-15.
 */
public class AimListAdapter extends BaseAdapter {
    private Context mContext;
    private List<OneAimInList> mItems = new ArrayList<OneAimInList>();

    public AimListAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }
    public void addItem(OneAimInList it)
    {
        mItems.add(it);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AimListView itemView;
        if (convertView == null) {

            itemView = new AimListView(mContext, mItems.get(position));
        } else {

            itemView = (AimListView) convertView;
        }

        itemView.setTitle(mItems.get(position).getData(0));
        itemView.setDate(mItems.get(position).getData(1));
        return itemView;
    }
}
