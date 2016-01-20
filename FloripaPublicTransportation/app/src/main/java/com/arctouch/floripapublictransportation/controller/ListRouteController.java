package com.arctouch.floripapublictransportation.controller;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.arctouch.floripapublictransportation.activities.DetailsRouteActivity;
import com.arctouch.floripapublictransportation.components.FindRoutesRest;
import com.arctouch.floripapublictransportation.components.RestConfiguration;
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

import java.io.IOException;

/**
 * Created by GabrielPacheco on 20/01/2016.
 */
public class ListRouteController {

    private Context context;

    public ListRouteController(Context context) {
        this.context = context;
    }

    public void executeTestNetwork(String query){
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            RestConfiguration restConfiguration = new RestConfiguration(context);

            try {
                restConfiguration.readProperties();
            } catch (IOException e) {
                //showMessageToast("Properties not found.");
                return;
            }

            FindRoutesRest findRoutesRest = new FindRoutesRest((AsyncResponse) context, restConfiguration.getUser(), restConfiguration.getPassword(), query);

            findRoutesRest.execute(restConfiguration.getUrlFindRoutes());
        } else {
            //showMessageToast("No network connection available.");
        }
    }
}
