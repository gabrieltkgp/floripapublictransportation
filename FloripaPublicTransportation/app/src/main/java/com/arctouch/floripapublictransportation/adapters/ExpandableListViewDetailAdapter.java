package com.arctouch.floripapublictransportation.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.entities.Stop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class ExpandableListViewDetailAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> itemsCalendar;
    private List<Object> subItemsCalendar;

    public ExpandableListViewDetailAdapter() {
    }

    public void buildExpandableListView(ArrayList<Departure> items){
        itemsCalendar = new ArrayList<>();
        subItemsCalendar = new ArrayList<>();

        Departure departure;
        String calendar = "";

        for(int i = 0; i < items.size(); i++) {
            departure = items.get(i);

            if (!calendar.toString().equals(departure.getCalendar().toString())){
                calendar = departure.getCalendar();
                itemsCalendar.add(calendar);
            }
        }

        List<Departure> itemsDeparture = new ArrayList<>();
        calendar = items.get(0).getCalendar();

        int i=0;
        do {
            departure = items.get(i);

            if (calendar.toString().equals(departure.getCalendar().toString())){
                itemsDeparture.add(departure);
                i++;
            } else {
                subItemsCalendar.add(itemsDeparture);

                itemsDeparture = new ArrayList<>();

                calendar = departure.getCalendar();
            }
        }while(i < items.size());

        subItemsCalendar.add(itemsDeparture);
    }

    @Override
    public int getGroupCount() {
        return itemsCalendar.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (groupPosition >= subItemsCalendar.size())
            return 0;

        ArrayList<String> subItem = (ArrayList<String>) subItemsCalendar.get(groupPosition);

        return subItem.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return subItemsCalendar.get(groupPosition);
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
        String calendar = itemsCalendar.get(groupPosition);
        textViewStopName.setText(calendar.toString());

        return convertView;
    }

    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.subitem_list_departure, null);
        }

        List<Departure> itemsDeparture = (ArrayList<Departure>) subItemsCalendar.get(groupPosition);
        Departure departure = itemsDeparture.get(childPosition);

        TextView textViewDeparture = (TextView) convertView.findViewById(R.id.textViewDeparture);
        textViewDeparture.setText(departure.getTime());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
