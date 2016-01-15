package com.arctouch.floripapublictransportation.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapter.DepartureSubItemExpandableListView;
import com.arctouch.floripapublictransportation.adapter.ExpandableListViewDetailAdapter;
import com.arctouch.floripapublictransportation.adapter.ListViewRoutesAdapter;
import com.arctouch.floripapublictransportation.adapter.ListViewStopsAdapter;
import com.arctouch.floripapublictransportation.classes.FindRoutesRest;
import com.arctouch.floripapublictransportation.classes.FindStopsRest;
import com.arctouch.floripapublictransportation.classes.RestConfiguration;
import com.arctouch.floripapublictransportation.entity.Stop;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DetailsRouteActivity extends AppCompatActivity implements AsyncResponse {

    private String routeName;
    private String routeId;

    private List<Stop> itemsStops = new ArrayList<>();
    private List<Object> subItemsStops = new ArrayList<>();

    private ListViewStopsAdapter listViewStopsAdapter;
    private ListView listView;

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

        createExpandableListView();
    }

    public void onButtonBackClick(View v){
        Toast.makeText(DetailsRouteActivity.this, "click", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, ListRouteActivity.class);
        setResult(1, it);
        finish();
    }

    private void initializeComponents(){
        listView = (ListView) findViewById(R.id.listViewStops);
        //listView.setOnItemClickListener(this);
        listView.setCacheColorHint(Color.TRANSPARENT);

        listViewStopsAdapter = new ListViewStopsAdapter();
    }

    public void executeTestNetwork(){
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

    @Override
    public void processFinish(ArrayList items) {
        listViewStopsAdapter.setItems(items);
        listViewStopsAdapter.setMInflater(this);

        listView.setAdapter(listViewStopsAdapter);
    }

    @Override
    public void showMessageToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void createExpandableListView(){
        ExpandableListView expandableListViewDetails = (ExpandableListView) findViewById(R.id.expandableListViewTimetable);

        Stop stop1 = new Stop(1, "teste1", 1, 1);
        itemsStops.add(stop1);

        Stop stop2 = new Stop(2, "teste1", 1, 1);
        itemsStops.add(stop2);

       /* Stop stopItemExpandableListView3 = new Stop();
        stopItemExpandableListView3.setId(3);
        stopItemExpandableListView3.setName("teste3");
        itemsStops.add(stopItemExpandableListView3);*/

        List<DepartureSubItemExpandableListView> subItemsDeparture = new ArrayList<>();

        DepartureSubItemExpandableListView departureSubItemExpandableListView1 = new DepartureSubItemExpandableListView();
        departureSubItemExpandableListView1.setDepartureTime("06:00");
        departureSubItemExpandableListView1.setDepartureCalendar("WEEKDAY");
        subItemsDeparture.add(departureSubItemExpandableListView1);

        DepartureSubItemExpandableListView departureSubItemExpandableListView2 = new DepartureSubItemExpandableListView();
        departureSubItemExpandableListView2.setDepartureTime("07:00");
        departureSubItemExpandableListView2.setDepartureCalendar("SATURDAY");
        subItemsDeparture.add(departureSubItemExpandableListView2);

        DepartureSubItemExpandableListView departureSubItemExpandableListView3 = new DepartureSubItemExpandableListView();
        departureSubItemExpandableListView3.setDepartureTime("08:00");
        departureSubItemExpandableListView3.setDepartureCalendar("SUNDAY");
        subItemsDeparture.add(departureSubItemExpandableListView3);

        subItemsStops.add(subItemsDeparture);

        List<DepartureSubItemExpandableListView> subItemsDeparture2 = new ArrayList<>();

        DepartureSubItemExpandableListView departureSubItemExpandableListView4 = new DepartureSubItemExpandableListView();
        departureSubItemExpandableListView4.setDepartureTime("10:00");
        departureSubItemExpandableListView4.setDepartureCalendar("SUNDAY");
        subItemsDeparture2.add(departureSubItemExpandableListView4);

        subItemsStops.add(subItemsDeparture2);

        ExpandableListViewDetailAdapter adapter = new ExpandableListViewDetailAdapter(getBaseContext(), itemsStops, subItemsStops);
        expandableListViewDetails.setAdapter(adapter);
    }
}
