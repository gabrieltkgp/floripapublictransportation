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
import android.widget.BaseAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapters.ExpandableListViewDetailAdapter;
import com.arctouch.floripapublictransportation.adapters.GridViewDepartureAdapter;
import com.arctouch.floripapublictransportation.adapters.ListViewStopsAdapter;
import com.arctouch.floripapublictransportation.components.FindDeparturesRest;
import com.arctouch.floripapublictransportation.components.FindStopsRest;
import com.arctouch.floripapublictransportation.components.RestConfiguration;
import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.entities.Stop;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;
import com.arctouch.floripapublictransportation.tools.ProcessDeparture;

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
            GridView gridView = (GridView) findViewById(R.id.gridViewWeekday);
            GridViewDepartureAdapter gridViewDepartureAdapter = new GridViewDepartureAdapter(this);

            ProcessDeparture processDeparture = new ProcessDeparture();
            ArrayList<Departure> itemsWeekDay = processDeparture.createArrayListDepartureWeekDay(items);
            gridViewDepartureAdapter.createArray(itemsWeekDay);

            gridView.setAdapter(gridViewDepartureAdapter);

            ListUtils.setDynamicHeightGridView(gridView);
        }
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

        public static void setDynamicHeightGridView(GridView gridView) {
            ListAdapter gridViewAdapter = gridView.getAdapter();

            if (gridViewAdapter == null) {
                // when adapter is null
                return;
            }

            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.UNSPECIFIED);

            double count = gridView.getCount();
            double columns = gridView.getNumColumns();
            double result = count / columns;
            int nNumLines = (int) Math.ceil(result);

            for (int i = 0; i < nNumLines; i++) {
                View listItem = gridViewAdapter.getView(i * gridView.getNumColumns(), null, gridView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = gridView.getLayoutParams();
            params.height = height + (gridView.getMeasuredHeight() * (nNumLines - 1));
            //params.height = height;
            gridView.setLayoutParams(params);
            gridView.requestLayout();
        }
    }

}
