package com.arctouch.floripapublictransportation.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapters.GridViewDepartureAdapter;
import com.arctouch.floripapublictransportation.adapters.ListViewStopsAdapter;
import com.arctouch.floripapublictransportation.controllers.DetailsRouteController;
import com.arctouch.floripapublictransportation.general.DepartureDay;
import com.arctouch.floripapublictransportation.general.RestType;
import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.entities.Stop;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;
import com.arctouch.floripapublictransportation.tools.ProcessDeparture;
import com.arctouch.floripapublictransportation.tools.VisualComponentsUtils;

import java.util.ArrayList;
import java.util.List;

public class DetailsRouteActivity extends AppCompatActivity implements AsyncResponse {

    private String routeName;
    private String routeId;

    private List<Stop> itemsStops = new ArrayList<>();
    private List<Object> subItemsStops = new ArrayList<>();

    private ListViewStopsAdapter listViewStopsAdapter;
    private ListView listViewStop;

    private DetailsRouteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        defineRouteInformation();

        initializeComponents();

        createObjects();

        controller.executeStopsRestConnection(this.routeId);

        controller.executeDeparturesRestConnection(this.routeId);
    }

    private void defineRouteInformation() {
        Intent it = getIntent();
        this.routeId = it.getStringExtra("routeId");
        this.routeName = it.getStringExtra("routeName");

        TextView textViewRoute = (TextView) findViewById(R.id.textViewRoute);
        textViewRoute.setText(this.routeName);
    }

    private void initializeComponents() {
        listViewStop = (ListView) findViewById(R.id.listViewStops);
        listViewStop.setCacheColorHint(Color.TRANSPARENT);
    }

    private void createObjects() {
        controller = new DetailsRouteController(this);

        listViewStopsAdapter = new ListViewStopsAdapter();
    }

    private void DefineListViewAdapter(ArrayList items) {
        listViewStopsAdapter.setItems(items);
        listViewStopsAdapter.setMInflater(this);

        listViewStop.setAdapter(listViewStopsAdapter);

        VisualComponentsUtils.setDynamicHeight(listViewStop);
    }

    private void executeBackAction() {
        Intent it = new Intent(this, ListRouteActivity.class);
        setResult(1, it);
        finish();
    }

    public void onButtonBackClick(View v) {
        executeBackAction();
    }

    @Override
    public void processFinish(ArrayList items, RestType restType) {

        if (restType.equals(RestType.STOP)) {
            DefineListViewAdapter(items);
            return;
        }

        if (restType.equals(RestType.DEPARTURE)) {
            configureGridViewDay(items, DepartureDay.WEEKDAY);
            configureGridViewDay(items, DepartureDay.SATURDAY);
            configureGridViewDay(items, DepartureDay.SUNDAY);
        }
    }

    private void configureGridViewDay(ArrayList items, DepartureDay departureDay) {
        GridView gridView = null;
        ProcessDeparture processDeparture = new ProcessDeparture();
        ArrayList<Departure> itemsDay = null;

        switch (departureDay) {
            case WEEKDAY: {
                gridView = (GridView) findViewById(R.id.gridViewWeekday);
                itemsDay = processDeparture.createArrayListDepartureWeekDay(items);
                break;
            }
            case SATURDAY: {
                gridView = (GridView) findViewById(R.id.gridViewSaturday);
                itemsDay = processDeparture.createArrayListDepartureSaturday(items);
                break;
            }
            case SUNDAY: {
                gridView = (GridView) findViewById(R.id.gridViewSunday);
                itemsDay = processDeparture.createArrayListDepartureSunday(items);
                break;
            }
        }

        GridViewDepartureAdapter gridViewDepartureAdapter = new GridViewDepartureAdapter(this);

        gridViewDepartureAdapter.createArray(itemsDay);

        gridView.setAdapter(gridViewDepartureAdapter);

        VisualComponentsUtils.setDynamicHeightGridView(gridView);
    }

    @Override
    public void showMessageToast(String message) {
        controller.showMessage(message); Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
