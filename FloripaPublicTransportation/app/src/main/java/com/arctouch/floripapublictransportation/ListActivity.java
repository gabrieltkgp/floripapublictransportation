package com.arctouch.floripapublictransportation;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.classes.ItemListViewResult;
import com.arctouch.floripapublictransportation.classes.ListViewResultAdapter;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ListViewResultAdapter listViewResultAdapter;
    private ArrayList<ItemListViewResult> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listViewResult);

        listView.setOnItemClickListener(this);

        createListView();
    }

    private void createListView() {
        items = new ArrayList<ItemListViewResult>();
        ItemListViewResult item1 = new ItemListViewResult("First line");
        ItemListViewResult item2 = new ItemListViewResult("Second line");
        ItemListViewResult item3 = new ItemListViewResult("Third line");

        items.add(item1);
        items.add(item2);
        items.add(item3);

        listViewResultAdapter = new ListViewResultAdapter(this, items);

        listView.setAdapter(listViewResultAdapter);

        listView.setCacheColorHint(Color.TRANSPARENT);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemListViewResult item = (ItemListViewResult) listViewResultAdapter.getItem(position);
        Toast.makeText(this, "Click: " + item.getText(), Toast.LENGTH_LONG).show();


    }
}
