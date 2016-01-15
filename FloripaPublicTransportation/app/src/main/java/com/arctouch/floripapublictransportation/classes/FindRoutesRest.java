package com.arctouch.floripapublictransportation.classes;

import com.arctouch.floripapublictransportation.entity.Route;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 12/01/2016.
 */
public class FindRoutesRest extends RestConnection {

    public FindRoutesRest(AsyncResponse delegate, String user, String password, String query) {
        super(delegate, user, password, query);
    }

    @Override
    protected ArrayList parseJson(String jsonResult) {

        ArrayList<Route> items = new ArrayList();

        try {
            JSONObject json = new JSONObject(jsonResult);
            JSONArray rows = json.getJSONArray("rows");

            for (int i = 0; i < rows.length(); i++) {
                JSONObject obj = rows.getJSONObject(i);

                Route item = new Route(obj.getInt("id"), obj.getString("shortName"), obj.getString("longName"), obj.getString("lastModifiedDate"), obj.getInt("agencyId"));

                items.add(item);
            }
        } catch (JSONException e) {
            getDelegate().showMessageToast("Error to parse json.");
        }

        return items;
    }

    @Override
    protected String getJsonParams(){
        return "{ \"params\": { \"stopName\": \"%" + getQuery() + "%\" } }";
    }
}