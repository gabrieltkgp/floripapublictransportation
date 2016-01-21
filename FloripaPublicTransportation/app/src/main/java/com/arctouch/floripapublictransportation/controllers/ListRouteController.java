package com.arctouch.floripapublictransportation.controllers;

import android.content.Context;
import android.content.Intent;

import com.arctouch.floripapublictransportation.activities.DetailsRouteActivity;
import com.arctouch.floripapublictransportation.components.FindRoutesRest;
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.general.RestType;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;
import com.arctouch.floripapublictransportation.tools.RestConfiguration;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 20/01/2016.
 */
public class ListRouteController extends BaseController{

    public ListRouteController(Context context) {
        super(context);
    }

    public void executeRestConnection(String query){

        if (!isConnected()) {
            showMessage("No network connection available.");
            return;
        }

        RestConfiguration restConfiguration = readRestConfiguration();

        if (restConfiguration == null){
            showMessage("Properties not found.");
            return;
        }

        FindRoutesRest findRoutesRest = new FindRoutesRest((AsyncResponse) getContext(), restConfiguration.getUser(), restConfiguration.getPassword(), query);

        findRoutesRest.execute(restConfiguration.getUrlFindRoutes());
    }

    public Intent createIntentDetails(Route route){
        Intent it = new Intent(getContext(), DetailsRouteActivity.class);

        it.putExtra("route", route);

        return it;
    }


}
