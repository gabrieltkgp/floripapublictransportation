package com.arctouch.floripapublictransportation.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.arctouch.floripapublictransportation.entities.Departure;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 18/01/2016.
 */
public class GridViewDepartureAdapter extends BaseAdapter {

    private Context context;
    private String[] time;

    public GridViewDepartureAdapter(Context context) {
        this.context = context;
    }

    public int getCount() {
        return time.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
            tv.setLayoutParams(new GridView.LayoutParams(120, 60));
        } else {
            tv = (TextView) convertView;
        }

        tv.setText(time[position]);
        return tv;
    }

    public void createArray(ArrayList<Departure> items){

        Departure departure;
        time = new String[items.size()];

        for (int i = 0; i < items.size(); i++) {
            departure = items.get(i);
            time[i] = departure.getTime().toString();
        }

    }
}
