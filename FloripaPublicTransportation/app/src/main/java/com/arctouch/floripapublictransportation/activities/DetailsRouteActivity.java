package com.arctouch.floripapublictransportation.activities;

import android.content.Intent;
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
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.general.DepartureDay;
import com.arctouch.floripapublictransportation.general.RestType;
import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;
import com.arctouch.floripapublictransportation.tools.ProcessDeparture;
import com.arctouch.floripapublictransportation.tools.VisualComponentsUtils;

import java.util.ArrayList;

public class DetailsRouteActivity extends AppCompatActivity implements AsyncResponse {
    private Route route;

    private TextView textViewRoute;
    private ListView listViewStop;


    private DetailsRouteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        createObjects();

        initializeVisualComponents();

        defineRouteInformation();

        executeRestConnection();
    }

    private void initializeVisualComponents() {
        textViewRoute = (TextView) findViewById(R.id.textViewRoute);

        listViewStop = (ListView) findViewById(R.id.listViewStops);
    }

    private void defineRouteInformation(){
        Intent it = getIntent();

        route = (Route)it.getSerializableExtra("route");

        textViewRoute.setText(route.getLongName());
    }

    private void createObjects() {
        controller = new DetailsRouteController(this);
    }

    private void executeRestConnection(){
        controller.executeStopsRestConnection(route.getId().toString());
        controller.executeDeparturesRestConnection(route.getId().toString());
    }

    public void onButtonBackClick(View v) {
        setResult(1, new Intent(this, ListRouteActivity.class));
        finish();
    }

    private GridView getGridView(DepartureDay departureDay){
        GridView gridView = null;

        switch (departureDay) {
            case WEEKDAY: {
                gridView = (GridView) findViewById(R.id.gridViewWeekday);
                break;
            }
            case SATURDAY: {
                gridView = (GridView) findViewById(R.id.gridViewSaturday);
                break;
            }
            case SUNDAY: {
                gridView = (GridView) findViewById(R.id.gridViewSunday);
                break;
            }
        }

        return gridView;
    }

    private ArrayList<Departure> getArrayListDeparture(ArrayList items, DepartureDay departureDay){

        ArrayList<Departure> itemsDay = null;
        ProcessDeparture processDeparture = new ProcessDeparture();

        switch (departureDay) {
            case WEEKDAY: {
                itemsDay = processDeparture.createArrayListDepartureWeekDay(items);
                break;
            }
            case SATURDAY: {
                itemsDay = processDeparture.createArrayListDepartureSaturday(items);
                break;
            }
            case SUNDAY: {
                itemsDay = processDeparture.createArrayListDepartureSunday(items);
                break;
            }
        }

        return itemsDay;
    }

    private void configureGridViewDay(ArrayList items, DepartureDay departureDay) {
        GridView gridView = getGridView(departureDay);

        ArrayList<Departure> itemsDay = getArrayListDeparture(items, departureDay);

        GridViewDepartureAdapter gridViewDepartureAdapter = new GridViewDepartureAdapter(this);

        gridViewDepartureAdapter.createArray(itemsDay);

        gridView.setAdapter(gridViewDepartureAdapter);

        VisualComponentsUtils.setDynamicHeightGridView(gridView);
    }

    private void configureListView(ArrayList items) {
        ListViewStopsAdapter listViewStopsAdapter;

        listViewStopsAdapter = new ListViewStopsAdapter(this);

        listViewStopsAdapter.setItems(items);

        listViewStop.setAdapter(listViewStopsAdapter);

        VisualComponentsUtils.setDynamicHeightListView(listViewStop);
    }

    @Override
    public void processFinish(ArrayList items, RestType restType) {

        if (restType.equals(RestType.STOP)) {
            configureListView(items);
            return;
        }

        if (restType.equals(RestType.DEPARTURE)) {
            configureGridViewDay(items, DepartureDay.WEEKDAY);
            configureGridViewDay(items, DepartureDay.SATURDAY);
            configureGridViewDay(items, DepartureDay.SUNDAY);
        }
    }

    @Override
    public void showMessage(String message) {
        controller.showMessage(message);
    }
}
