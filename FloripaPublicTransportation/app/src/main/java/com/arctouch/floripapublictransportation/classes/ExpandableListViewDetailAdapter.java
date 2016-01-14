package com.arctouch.floripapublictransportation.classes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.arctouch.floripapublictransportation.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class ExpandableListViewDetailAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<StopItemExpandableListView> items;
    private List<Object> subItemsStop;
    private List<DepartureSubItemExpandableListView> subItems;

    public ExpandableListViewDetailAdapter(Context context, List<StopItemExpandableListView> items, List<Object> subItems) {
        this.context = context;
        this.items = items;
        this.subItemsStop = subItems;
    }

    @Override
    public int getGroupCount() {
        return items.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) subItemsStop.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_list_stop, null);
        }
        TextView textViewStopName = (TextView) convertView.findViewById(R.id.textViewStopName);
        StopItemExpandableListView stopItemExpandableListView = items.get(groupPosition);
        textViewStopName.setText(stopItemExpandableListView.getStopName());
        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.subitem_list_departure, null);
        }

        subItems = (ArrayList<DepartureSubItemExpandableListView>) subItemsStop.get(groupPosition);

        TextView textViewDeparture = (TextView) convertView.findViewById(R.id.textViewDeparture);

        DepartureSubItemExpandableListView departureSubItemExpandableListView = subItems.get(childPosition);

        textViewDeparture.setText(departureSubItemExpandableListView.getDepartureTime());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
