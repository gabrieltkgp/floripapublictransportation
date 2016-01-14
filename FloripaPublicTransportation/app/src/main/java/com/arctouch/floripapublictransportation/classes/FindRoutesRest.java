package com.arctouch.floripapublictransportation.classes;

import android.content.Context;
import android.graphics.Color;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 12/01/2016.
 */
public class FindRoutesRest extends RestConnection {
    private ListView listView;
    private ListViewResultAdapter listViewResultAdapter;
    private ArrayList<RouteItemListView> items;

    public FindRoutesRest(Context context, String user, String password, String textSearch, ListView listView, BaseAdapter listViewResultAdapter) {
        super(context, user, password, textSearch);
        this.listView = listView;
        this.listViewResultAdapter = (ListViewResultAdapter) listViewResultAdapter;
    }

    @Override
    protected ArrayList parseJson(String jsonResult) {
        try {
            JSONObject json = new JSONObject(jsonResult);
            JSONArray rows = json.getJSONArray("rows");

            items = new ArrayList<RouteItemListView>();

            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);

                RouteItemListView item = new RouteItemListView(obj.getInt("id"), obj.getString("longName"));

                items.add(item);
            }
        } catch (JSONException e) {
            Toast.makeText(getContext(), "Error to parse json.", Toast.LENGTH_LONG).show();
        }
        return items;
    }

    @Override
    protected void executeAfterParseJson(ArrayList items){
        listViewResultAdapter.setItems(items);
        listViewResultAdapter.setMInflater(getContext());

        listView.setAdapter(this.listViewResultAdapter);

        listView.setCacheColorHint(Color.TRANSPARENT);
    }

    @Override
    protected String getJsonParams(){
        return "{ \"params\": { \"stopName\": \"%" + getTextSearch() + "%\" } }";
    }
}