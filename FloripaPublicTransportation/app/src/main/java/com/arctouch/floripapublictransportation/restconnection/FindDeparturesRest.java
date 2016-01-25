package com.arctouch.floripapublictransportation.restconnection;

import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.general.RestType;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class FindDeparturesRest extends RestConnectionImpl {

    public FindDeparturesRest(AsyncResponse delegate, String user, String password, String query) {
        super(delegate, user, password, query);
    }

    @Override
    public ArrayList parseJson(String jsonResult) {
        ArrayList<Departure> items = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(jsonResult);
            JSONArray rows = json.getJSONArray("rows");

            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);

                Departure item = new Departure(obj.getInt("id"), obj.getString("calendar"), obj.getString("time"));

                items.add(item);
            }
        } catch (JSONException e) {
            getDelegate().showMessage("Error to parse json.");
        }

        return items;
    }

    @Override
    public String getJsonParams() {
        return "{ \"params\": { \"routeId\": " + getQuery() + " } }";
    }

    @Override
    public void processFinish(ArrayList items) {
        getDelegate().processFinish(items, RestType.DEPARTURE);
    }

}
