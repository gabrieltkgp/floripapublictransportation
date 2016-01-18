package com.arctouch.floripapublictransportation.components;

import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class FindDeparturesRest extends RestConnection{

    public FindDeparturesRest(AsyncResponse delegate, String user, String password, String query) {
        super(delegate, user, password, query);
    }

    @Override
    protected ArrayList parseJson(String jsonResult) {
        ArrayList<Departure> items = new ArrayList();

        try {
            JSONObject json = new JSONObject(jsonResult);
            JSONArray rows = json.getJSONArray("rows");

            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);

                Departure item = new Departure(obj.getInt("id"), obj.getString("calendar"), obj.getString("time"));

                items.add(item);
            }
        } catch (JSONException e) {
            getDelegate().showMessageToast("Error to parse json.");
        }

        return items;
    }

    @Override
    protected String getJsonParams(){
        return "{ \"params\": { \"routeId\": " + getQuery() + " } }";
    }

    @Override
    protected void processFinish(ArrayList items){
        getDelegate().processFinish(items, "DEPARTURE");
    }
}
