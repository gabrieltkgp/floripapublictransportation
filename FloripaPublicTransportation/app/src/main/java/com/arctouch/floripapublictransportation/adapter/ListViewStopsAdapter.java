package com.arctouch.floripapublictransportation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.entity.Route;
import com.arctouch.floripapublictransportation.entity.Stop;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class ListViewStopsAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private ArrayList<Stop> items;

    public ListViewStopsAdapter(Context context, ArrayList<Stop> items) {
        this.items = items;
        this.mInflater = LayoutInflater.from(context);
    }

    public ListViewStopsAdapter() {
    }


    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Stop item = items.get(position);

        convertView = mInflater.inflate(R.layout.item_list_result, null);

        ((TextView) convertView.findViewById(R.id.textView)).setText(item.getName());

        return convertView;
    }

    public void setMInflater(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setItems(ArrayList<Stop> items) {
        this.items = items;
    }
}
