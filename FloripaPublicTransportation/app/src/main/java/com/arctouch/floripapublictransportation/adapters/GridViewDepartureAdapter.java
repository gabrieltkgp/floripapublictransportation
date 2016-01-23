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
    private ArrayList<Departure> items;

    public GridViewDepartureAdapter(Context context, ArrayList<Departure> items) {
        this.context = context;
        this.items = items;
    }

    public int getCount() {
        return items.size();
    }

    public Object getItem(int position) {
        return items.get(position);
    }

    public long getItemId(int position) {
        return items.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView == null) {
            tv = new TextView(context);
            tv.setLayoutParams(new GridView.LayoutParams(120, 50));
        } else {
            tv = (TextView) convertView;
        }

        tv.setText(items.get(position).getTime().toString());
        return tv;
    }

    public void setItems(ArrayList<Departure> items) {
        this.items = items;
    }

}
