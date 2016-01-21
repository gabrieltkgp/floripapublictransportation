package com.arctouch.floripapublictransportation.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.adapters.ListViewRoutesAdapter;
import com.arctouch.floripapublictransportation.controllers.ListRouteController;
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.general.RestType;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import java.util.ArrayList;

public class ListRouteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AsyncResponse {

    private ListView listView;
    private ListViewRoutesAdapter listViewRoutesAdapter;
    private EditText editText;
    private ListRouteController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initializeVisualComponents();

        createObjects();
    }

    private void createObjects(){
        controller = new ListRouteController(this);
        listViewRoutesAdapter = new ListViewRoutesAdapter();
    }

    private void initializeVisualComponents(){
        listView = (ListView) findViewById(R.id.listViewResult);
        listView.setOnItemClickListener(this);

        editText = (EditText) findViewById(R.id.editTextSearch);
    }

    private void showDetailsRouteActivity(int position) {
        Route route = (Route) listViewRoutesAdapter.getItem(position);

        Intent it = controller.createIntentDetails(route);

        startActivityForResult(it, 1);
    }

    private void defineListViewAdapter(ArrayList items){
        listViewRoutesAdapter.setItems(items);
        listViewRoutesAdapter.setMInflater(this);

        listView.setAdapter(listViewRoutesAdapter);
    }

    public void onButtonClick(View v){
        controller.executeRestConnection(editText.getText().toString());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDetailsRouteActivity(position);
    }

    @Override
    public void processFinish(ArrayList items, RestType restType) {
        defineListViewAdapter(items);
    }

    @Override
    public void showMessageToast(String message) {
        controller.showMessage(message);
    }
}
