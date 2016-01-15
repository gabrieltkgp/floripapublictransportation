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
import android.widget.ListView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;
import com.arctouch.floripapublictransportation.classes.FindRoutesRest;
import com.arctouch.floripapublictransportation.classes.RestConfiguration;
import com.arctouch.floripapublictransportation.entity.Route;
import com.arctouch.floripapublictransportation.adapter.ListViewRoutesAdapter;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import java.io.IOException;
import java.util.ArrayList;

public class ListRouteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AsyncResponse {

    private ListView listView;
    private ListViewRoutesAdapter listViewRoutesAdapter;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        initializeComponents();
    }

    private void initializeComponents(){
        listView = (ListView) findViewById(R.id.listViewResult);
        listView.setOnItemClickListener(this);
        listView.setCacheColorHint(Color.TRANSPARENT);

        editText = (EditText) findViewById(R.id.editTextSearch);

        listViewRoutesAdapter = new ListViewRoutesAdapter();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Route item = (Route) this.listViewRoutesAdapter.getItem(position);
        Toast.makeText(this, "Click: " + item.getId() + " = " + item.getLongName(), Toast.LENGTH_SHORT).show();

        callDetailsRouteActivity(item);
    }

    private void callDetailsRouteActivity(Route item) {
        Intent it = new Intent(this, DetailsRouteActivity.class);
        Integer routeId = item.getId();
        it.putExtra("routeId", routeId.toString());
        it.putExtra("routeName", item.getLongName());

        startActivityForResult(it, 1);
    }

    public void onButtonClick(View v){
        executeTestNetwork();
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

            FindRoutesRest findRoutesRest = new FindRoutesRest(this, restConfiguration.getUser(), restConfiguration.getPassword(), editText.getText().toString());

            findRoutesRest.execute(restConfiguration.getUrlFindRoutes());
        } else {
            showMessageToast("No network connection available.");
        }
    }

    @Override
    public void processFinish(ArrayList items) {
        listViewRoutesAdapter.setItems(items);
        listViewRoutesAdapter.setMInflater(this);

        listView.setAdapter(listViewRoutesAdapter);
    }

    @Override
    public void showMessageToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
