package com.arctouch.floripapublictransportation.classes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import com.arctouch.floripapublictransportation.R;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class ListViewResultAdapter extends BaseAdapter{

    private LayoutInflater mInflater;
    private ArrayList<RouteItemListView> items;

    public ListViewResultAdapter(Context context, ArrayList<RouteItemListView> items) {
        this.items = items;
        this.mInflater = LayoutInflater.from(context);
    }

    public ListViewResultAdapter() {
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
        RouteItemListView item = items.get(position);

        convertView = mInflater.inflate(R.layout.item_list_result, null);

        ((TextView) convertView.findViewById(R.id.textView)).setText(item.getText());

        return convertView;
    }

    public void setMInflater(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setItems(ArrayList<RouteItemListView> items) {
        this.items = items;
    }
}
