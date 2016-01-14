package com.arctouch.floripapublictransportation.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.classes.DepartureSubItemExpandableListView;
import com.arctouch.floripapublictransportation.classes.ExpandableListViewDetailAdapter;
import com.arctouch.floripapublictransportation.classes.StopItemExpandableListView;

import java.util.ArrayList;
import java.util.List;

public class DetailsRouteActivity extends AppCompatActivity {

    String routeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent it = getIntent();
        routeId = it.getStringExtra("RouteId");

        Toast.makeText(DetailsRouteActivity.this, routeId, Toast.LENGTH_SHORT).show();

        createExpandableListView();
    }

    public void onButtonBackClick(View v){
        Toast.makeText(DetailsRouteActivity.this, "click", Toast.LENGTH_SHORT).show();
        Intent it = new Intent(this, ListRouteActivity.class);
        setResult(1, it);
        finish();
    }

    private void createExpandableListView(){
        ExpandableListView expandableListViewDetails = (ExpandableListView) findViewById(R.id.expandableListViewDetails);
        List<StopItemExpandableListView> itemsStops = new ArrayList<>();
        StopItemExpandableListView stopItemExpandableListView1 = new StopItemExpandableListView();
        stopItemExpandableListView1.setStopId(1);
        stopItemExpandableListView1.setStopName("teste1");
        itemsStops.add(stopItemExpandableListView1);

        StopItemExpandableListView stopItemExpandableListView2 = new StopItemExpandableListView();
        stopItemExpandableListView2.setStopId(2);
        stopItemExpandableListView2.setStopName("teste2");
        itemsStops.add(stopItemExpandableListView2);

        StopItemExpandableListView stopItemExpandableListView3 = new StopItemExpandableListView();
        stopItemExpandableListView3.setStopId(3);
        stopItemExpandableListView3.setStopName("teste3");
        itemsStops.add(stopItemExpandableListView3);

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

        ExpandableListViewDetailAdapter adapter = new ExpandableListViewDetailAdapter(getBaseContext(), itemsStops, subItemsDeparture);
        expandableListViewDetails.setAdapter(adapter);
    }
}
