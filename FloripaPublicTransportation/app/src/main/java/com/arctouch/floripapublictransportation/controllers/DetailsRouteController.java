package com.arctouch.floripapublictransportation.controllers;

import android.content.Context;

import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.general.DepartureDay;
import com.arctouch.floripapublictransportation.interfaces.AsyncResponse;
import com.arctouch.floripapublictransportation.restconnection.FindDeparturesRest;
import com.arctouch.floripapublictransportation.restconnection.FindStopsRest;
import com.arctouch.floripapublictransportation.tools.ProcessDeparture;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 21/01/2016.
 */
public class DetailsRouteController extends BaseController {

    public DetailsRouteController(Context context) {
        super(context);
    }

    public void executeStopsRestConnection(String query) {

        if (!validationRestConnection()) {
            return;
        }

        FindStopsRest findStopsRest = new FindStopsRest((AsyncResponse) getContext(), getRestConfiguration().getUser(), getRestConfiguration().getPassword(), query);

        findStopsRest.execute(getRestConfiguration().getUrlFindStops());
    }

    public void executeDeparturesRestConnection(String query) {

        if (!validationRestConnection()) {
            return;
        }

        FindDeparturesRest findDeparturesRest = new FindDeparturesRest((AsyncResponse) getContext(), getRestConfiguration().getUser(), getRestConfiguration().getPassword(), query);

        findDeparturesRest.execute(getRestConfiguration().getUrlFindDepartures());
    }

    public ArrayList<Departure> getArrayListDeparture(ArrayList<Departure> items, DepartureDay departureDay) {

        ArrayList<Departure> itemsDay = null;
        ProcessDeparture processDeparture = new ProcessDeparture();

        switch (departureDay) {
            case WEEKDAY: {
                itemsDay = processDeparture.createArrayListDepartureWeekDay(items);
                break;
            }
            case SATURDAY: {
                itemsDay = processDeparture.createArrayListDepartureSaturday(items);
                break;
            }
            case SUNDAY: {
                itemsDay = processDeparture.createArrayListDepartureSunday(items);
                break;
            }
        }

        return itemsDay;
    }
}
