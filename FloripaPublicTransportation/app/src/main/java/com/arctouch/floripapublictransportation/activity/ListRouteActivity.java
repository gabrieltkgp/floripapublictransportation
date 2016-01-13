package com.arctouch.floripapublictransportation.activity;


import android.content.Context;
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
import com.arctouch.floripapublictransportation.classes.FindRoutNetworkRest;
import com.arctouch.floripapublictransportation.classes.RestConfiguration;
import com.arctouch.floripapublictransportation.classes.ItemListViewResult;
import com.arctouch.floripapublictransportation.classes.ListViewResultAdapter;

import java.util.ArrayList;

public class ListRouteActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ListViewResultAdapter listViewResultAdapter;
    private ArrayList<ItemListViewResult> items;
    private EditText editText;

    private RestConfiguration restConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        restConfiguration = new RestConfiguration(this);

        initializeComponents();
    }

    private void initializeComponents(){
        listView = (ListView) findViewById(R.id.listViewResult);
        listView.setOnItemClickListener(this);
        listView.setCacheColorHint(Color.TRANSPARENT);

        editText = (EditText) findViewById(R.id.editTextSearch);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemListViewResult item = (ItemListViewResult) listViewResultAdapter.getItem(position);
        Toast.makeText(this, "Click: " + item.getId() + " = " + item.getText(), Toast.LENGTH_LONG).show();
    }

    public void onButtonClick(View v){
        executeTestNetwork();
    }

    public void executeTestNetwork(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            FindRoutNetworkRest findRoutNetworkRest = new FindRoutNetworkRest(this, restConfiguration.getUser(), restConfiguration.getPassword(),
                    this.listView, this.listViewResultAdapter, editText.getText().toString());
            findRoutNetworkRest.execute(restConfiguration.getUrlFindRoutes());
        } else {
            Toast.makeText(this, "No network connection available.", Toast.LENGTH_LONG).show();
        }
    }


}
