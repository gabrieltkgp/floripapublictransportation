package com.arctouch.floripapublictransportation.controllers;

import android.content.Context;
import android.content.Intent;

import com.arctouch.floripapublictransportation.activities.DetailsRouteActivity;
import com.arctouch.floripapublictransportation.restconnection.FindRoutesRest;
import com.arctouch.floripapublictransportation.entities.Route;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;

/**
 * Created by GabrielPacheco on 20/01/2016.
 */
public class ListRouteController extends BaseController{

    /**
     *
     * @param context
     */
    public ListRouteController(Context context) {
        super(context);
    }

    public void executeRestConnection(String query){
        if (!validationRestConnection()){
            return;
        }

        FindRoutesRest findRoutesRest = new FindRoutesRest((AsyncResponse) getContext(), getRestConfiguration().getUser(), getRestConfiguration().getPassword(), query);

        findRoutesRest.execute(getRestConfiguration().getUrlFindRoutes());
    }

    public Intent createIntentDetails(Route route){
        Intent it = new Intent(getContext(), DetailsRouteActivity.class);

        it.putExtra("route", route);

        return it;
    }


}
