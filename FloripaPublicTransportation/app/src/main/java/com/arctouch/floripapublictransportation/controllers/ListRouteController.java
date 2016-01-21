package com.arctouch.floripapublictransportation.controllers;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.activities.DetailsRouteActivity;
import com.arctouch.floripapublictransportation.components.FindRoutesRest;
import com.arctouch.floripapublictransportation.tools.RestConfiguration;
import com.arctouch.floripapublictransportation.general.Constants;
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import java.io.IOException;

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
        Integer routeId = route.getId();
        it.putExtra("routeId", routeId.toString());
        it.putExtra("routeName", route.getLongName());

        return it;
    }
}
