package com.arctouch.floripapublictransportation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapters.GridViewDepartureAdapter;
import com.arctouch.floripapublictransportation.adapters.ListViewStopsAdapter;
import com.arctouch.floripapublictransportation.controllers.DetailsRouteController;
import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.entities.Stop;
import com.arctouch.floripapublictransportation.general.DepartureDay;
import com.arctouch.floripapublictransportation.general.RestType;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;
import com.arctouch.floripapublictransportation.tools.VisualComponentsUtils;

import java.util.ArrayList;

public class DetailsRouteActivity extends AppCompatActivity implements AsyncResponse {
    private Route route;

    private TextView textViewRoute;
    private ListView listViewStop;

    private DetailsRouteController controller;
    private ProgressBar progressBar;

    private void initializeVisualComponents() {
        textViewRoute = (TextView) findViewById(R.id.textViewRoute);

        listViewStop = (ListView) findViewById(R.id.listViewStops);

        defineRouteInformation();

        progressBar = (ProgressBar) findViewById(R.id.progressBarDetails);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    private void defineRouteInformation(){
        Intent it = getIntent();

        route = (Route) it.getSerializableExtra("route");

        textViewRoute.setText(route.getLongName());
    }

    private void createObjects() {
        controller = new DetailsRouteController(this);
    }

    private void executeRestConnection(){
        progressBar.setVisibility(ProgressBar.VISIBLE);
        controller.executeStopsRestConnection(route.getId().toString());
        controller.executeDeparturesRestConnection(route.getId().toString());
    }

    private GridView getGridViewDay(DepartureDay departureDay){
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

    private TextView getTextViewDay(DepartureDay departureDay){
        TextView textView = null;

        switch (departureDay) {
            case WEEKDAY: {
                textView = (TextView) findViewById(R.id.textViewWeekday);
                break;
            }
            case SATURDAY: {
                textView = (TextView) findViewById(R.id.textViewSaturday);
                break;
            }
            case SUNDAY: {
                textView = (TextView) findViewById(R.id.textViewSunday);
                break;
            }
        }

        return textView;
    }

    private boolean isArrayListEmpty(ArrayList items){
        return (items.size() == 0);
    }

    private void hideGridViewDeparture(DepartureDay departureDay){
        TextView textView = getTextViewDay(departureDay);
        textView.setVisibility(View.INVISIBLE);

        GridView gridView = getGridViewDay(departureDay);
        gridView.setVisibility(View.INVISIBLE);
    }

    private void hideDepartureComponents(){
        TextView textView = (TextView)findViewById(R.id.textViewDeparture);
        textView.setVisibility(View.INVISIBLE);

        hideGridViewDeparture(DepartureDay.WEEKDAY);
        hideGridViewDeparture(DepartureDay.SATURDAY);
        hideGridViewDeparture(DepartureDay.SUNDAY);
    }

    private void configureGridViewDay(ArrayList<Departure> items, DepartureDay departureDay) {
        ArrayList<Departure> itemsDay = controller.getArrayListDeparture(items, departureDay);

        if (isArrayListEmpty(itemsDay)){
            hideGridViewDeparture(departureDay);
            return;
        }
        GridViewDepartureAdapter gridViewDepartureAdapter = new GridViewDepartureAdapter(this, itemsDay);

        GridView gridView = getGridViewDay(departureDay);
        gridView.setAdapter(gridViewDepartureAdapter);

        VisualComponentsUtils.setDynamicHeightGridView(gridView);
    }

    private void configureGridView(ArrayList<Departure> items){
        if (isArrayListEmpty(items)){
            hideDepartureComponents();
            return;
        }

        configureGridViewDay(items, DepartureDay.WEEKDAY);
        configureGridViewDay(items, DepartureDay.SATURDAY);
        configureGridViewDay(items, DepartureDay.SUNDAY);
    }

    private void configureListView(ArrayList<Stop> items) {

        ListViewStopsAdapter listViewStopsAdapter = new ListViewStopsAdapter(this);

        listViewStopsAdapter.setItems(items);

        listViewStop.setAdapter(listViewStopsAdapter);

        VisualComponentsUtils.setDynamicHeightListView(listViewStop);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        createObjects();

        initializeVisualComponents();

        executeRestConnection();
    }

    public void onButtonBackClick(View v) {
        setResult(0, new Intent(this, ListRouteActivity.class));
        finish();
    }

    @Override
    public void processFinish(ArrayList items, RestType restType) {
        if (restType.equals(RestType.STOP)) {
            configureListView(items);
            return;
        }

        if (restType.equals(RestType.DEPARTURE)) {
            configureGridView(items);

            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }

    @Override
    public void showMessage(String message) {
        controller.showMessage(message);
    }
}
