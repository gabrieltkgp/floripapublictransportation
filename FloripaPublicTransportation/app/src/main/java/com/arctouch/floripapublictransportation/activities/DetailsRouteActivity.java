package com.arctouch.floripapublictransportation.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapters.ExpandableListViewDetailAdapter;
import com.arctouch.floripapublictransportation.adapters.ListViewStopsAdapter;
import com.arctouch.floripapublictransportation.components.FindDeparturesRest;
import com.arctouch.floripapublictransportation.components.FindStopsRest;
import com.arctouch.floripapublictransportation.components.RestConfiguration;
import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.entities.Stop;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DetailsRouteActivity extends AppCompatActivity implements AsyncResponse {

    private String routeName;
    private String routeId;

    private List<Stop> itemsStops = new ArrayList<>();
    private List<Object> subItemsStops = new ArrayList<>();

    private ListViewStopsAdapter listViewStopsAdapter;
    private ListView listView;
    private ExpandableListView expandableListViewTimetable;
    private ExpandableListViewDetailAdapter expandableListViewDetailAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent it = getIntent();
        this.routeId = it.getStringExtra("routeId");
        this.routeName = it.getStringExtra("routeName");

        TextView textViewRoute = (TextView) findViewById(R.id.textViewRoute);
        textViewRoute.setText(this.routeName);

        initializeComponents();

        executeTestNetwork();
        executeQueryDepartureRest();

        //createExpandableListView();
    }

    public void onButtonBackClick(View v) {
        Toast.makeText(DetailsRouteActivity.this, "click", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, ListRouteActivity.class);
        setResult(1, it);
        finish();
    }

    private void initializeComponents() {
        listView = (ListView) findViewById(R.id.listViewStops);
        //expandableListViewTimetable = (ExpandableListView) findViewById(R.id.expandableListViewTimetable);
        //listView.setOnItemClickListener(this);
        listView.setCacheColorHint(Color.TRANSPARENT);

        listViewStopsAdapter = new ListViewStopsAdapter();

        expandableListViewDetailAdapter = new ExpandableListViewDetailAdapter();
    }

    public void executeTestNetwork() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            RestConfiguration restConfiguration = new RestConfiguration(this);

            try {
                restConfiguration.readProperties();
            } catch (IOException e) {
                showMessageToast("Properties not found.");
                return;
            }

            FindStopsRest findStopsRest = new FindStopsRest(this, restConfiguration.getUser(), restConfiguration.getPassword(), this.routeId);

            findStopsRest.execute(restConfiguration.getUrlFindStops());
        } else {
            showMessageToast("No network connection available.");
        }
    }

    public void executeQueryDepartureRest() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            RestConfiguration restConfiguration = new RestConfiguration(this);

            try {
                restConfiguration.readProperties();
            } catch (IOException e) {
                showMessageToast("Properties not found.");
                return;
            }

            FindDeparturesRest findDeparturesRest = new FindDeparturesRest(this, restConfiguration.getUser(), restConfiguration.getPassword(), this.routeId);

            findDeparturesRest.execute(restConfiguration.getUrlFindDepartures());
        } else {
            showMessageToast("No network connection available.");
        }
    }

    @Override
    public void processFinish(ArrayList items, String param) {
        if (param.equals("STOP")) {
            listViewStopsAdapter.setItems(items);
            listViewStopsAdapter.setMInflater(this);

            listView.setAdapter(listViewStopsAdapter);

            ListUtils.setDynamicHeight(listView);

        } else if (param.equals("DEPARTURE")) {
//            expandableListViewDetailAdapter.setContext(this);
//            expandableListViewDetailAdapter.buildExpandableListView(items);
//
//            expandableListViewTimetable.setAdapter(expandableListViewDetailAdapter);
//
//            ListUtils.setDynamicHeight2(expandableListViewTimetable);
//
//            for (int i = 0; i < expandableListViewDetailAdapter.getGroupCount(); i++) {
//                expandableListViewTimetable.expandGroup(i);
//            }

            buildGridViewWeekDay((GridView) findViewById(R.id.gridViewWeekday), items, "WEEKDAY");
            buildGridViewWeekDay((GridView) findViewById(R.id.gridViewSaturday), items, "SATURDAY");
            buildGridViewWeekDay((GridView) findViewById(R.id.gridViewSunday), items, "SUNDAY");
        }
    }

    public void buildGridViewWeekDay(GridView gridView, ArrayList<Departure> items, String calendar) {
        String[] time;

        String calendarCompare = "";

        int initialPosition = -1;

        for (Departure departure : items) {
            initialPosition++;
            if (departure.getCalendar().equals(calendar)) {
                break;
            }
        }

        int finalPosition = initialPosition;

        Departure departure;
        while(finalPosition < items.size()){
            departure = items.get(finalPosition);

            if(!departure.getCalendar().equals(calendar)){
                break;
            }

            finalPosition++;
        }

        finalPosition--;

        int arraySize = finalPosition - initialPosition + 1;

        time = new String[arraySize];

        for (int i = 0; i < arraySize; i++) {
            departure = items.get(initialPosition + i);
            time[i] = departure.getTime().toString();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, time);

        gridView.setAdapter(adapter);
    }

    @Override
    public void showMessageToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }

        public static void setDynamicHeight2(ExpandableListView mListView) {
            ExpandableListAdapter mListAdapter = mListView.getExpandableListAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            int count = 0;
            int childrenCount = 0;
            for (int i = 0; i < mListAdapter.getGroupCount(); i++) {
                count++;
                View listItem = mListAdapter.getGroupView(i, true, null, null);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
                childrenCount = mListAdapter.getChildrenCount(i);
                for (int j = 0; j < childrenCount; j++) {
                    count++;
                    View listItem2 = mListAdapter.getChildView(i, j, false, null, null);
                    listItem2.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    height += listItem2.getMeasuredHeight();
                }
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (count - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

}
