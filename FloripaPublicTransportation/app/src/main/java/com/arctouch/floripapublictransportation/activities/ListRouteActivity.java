package com.arctouch.floripapublictransportation.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapters.ListViewRoutesAdapter;
import com.arctouch.floripapublictransportation.controllers.ListRouteController;
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.general.Constants;
import com.arctouch.floripapublictransportation.general.RestType;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import java.util.ArrayList;

public class ListRouteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnKeyListener, AsyncResponse {

    private ListView listView;
    private ListViewRoutesAdapter listViewRoutesAdapter;
    private EditText editText;
    private ListRouteController controller;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initializeVisualComponents();

        createObjects();
    }

    private void createObjects() {
        controller = new ListRouteController(this);
        listViewRoutesAdapter = new ListViewRoutesAdapter(this);
    }

    private void initializeVisualComponents() {
        listView = (ListView) findViewById(R.id.listViewRoute);
        listView.setOnItemClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.progressBarList);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        editText = (EditText) findViewById(R.id.editTextSearch);
        editText.setOnKeyListener(this);
    }

    private void showDetailsRouteActivity(int position) {
        Route route = (Route) listViewRoutesAdapter.getItem(position);

        Intent it = controller.createIntentDetails(route);

        startActivityForResult(it, Constants.DETAILS_ROUTE_ACTIVITY);
    }

    private void defineListViewAdapter(ArrayList items) {
        listViewRoutesAdapter.setItems(items);

        listView.setAdapter(listViewRoutesAdapter);
    }

    private boolean isStreetFilled() {
        if (!editText.getText().toString().equals("")) {
            return true;
        }

        showMessage("Enter a street name");
        return false;
    }

    private void executeRestConnection() {
        if (!isStreetFilled()) {
            return;
        }

        progressBar.setVisibility(ProgressBar.VISIBLE);
        controller.executeRestConnection(editText.getText().toString());
    }

    private void receiveInformationFromMapsActivity(int requestCode, int resultCode, Intent data){
        if (requestCode != Constants.MAPS_ACTIVITY) {
            return;
        }
        // Make sure the request was successful
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        String street = data.getStringExtra("street");

        editText.setText(street);

        executeRestConnection();
    }

    private boolean pressKeyEnter(int keyCode, KeyEvent event) {
        if (event.getAction() != KeyEvent.ACTION_DOWN) {
            return false;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                executeRestConnection();
                return true;
            default:
                break;
        }

        return false;
    }

    public void onButtonShowMapsClick(View v) {
        Intent it = new Intent(this, MapsActivity.class);

        startActivityForResult(it, Constants.MAPS_ACTIVITY);
    }

    public void onButtonClick(View v) {
        executeRestConnection();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDetailsRouteActivity(position);
    }

    @Override
    public void processFinish(ArrayList items, RestType restType) {
        defineListViewAdapter(items);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        controller.showMessage(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        receiveInformationFromMapsActivity(requestCode, resultCode, data);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        return pressKeyEnter(keyCode, event);
    }
}
