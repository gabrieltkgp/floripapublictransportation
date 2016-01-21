package com.arctouch.floripapublictransportation.controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.arctouch.floripapublictransportation.components.FindDeparturesRest;
import com.arctouch.floripapublictransportation.components.FindRoutesRest;
import com.arctouch.floripapublictransportation.components.FindStopsRest;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;
import com.arctouch.floripapublictransportation.tools.RestConfiguration;

import java.io.IOException;

/**
 * Created by GabrielPacheco on 21/01/2016.
 */
public class DetailsRouteController extends BaseController{

    public DetailsRouteController(Context context) {
        super(context);
    }

    public void executeStopsRestConnection(String query){

        if (!isConnected()) {
            showMessage("No network connection available.");
            return;
        }

        RestConfiguration restConfiguration = readRestConfiguration();

        if (restConfiguration == null){
            showMessage("Properties not found.");
            return;
        }

        FindStopsRest findStopsRest = new FindStopsRest((AsyncResponse) getContext(), restConfiguration.getUser(), restConfiguration.getPassword(), query);

        findStopsRest.execute(restConfiguration.getUrlFindStops());
    }

    public void executeDeparturesRestConnection(String query){

        if (!isConnected()) {
            showMessage("No network connection available.");
            return;
        }

        RestConfiguration restConfiguration = readRestConfiguration();

        if (restConfiguration == null){
            showMessage("Properties not found.");
            return;
        }

        FindDeparturesRest findDeparturesRest = new FindDeparturesRest((AsyncResponse) getContext(), restConfiguration.getUser(), restConfiguration.getPassword(), query);

        findDeparturesRest.execute(restConfiguration.getUrlFindDepartures());
    }
}
