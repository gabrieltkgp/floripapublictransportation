package com.arctouch.floripapublictransportation.tools;

import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.arctouch.floripapublictransportation.entities.Departure;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 20/01/2016.
 */
public class ProcessDeparture {

    private ArrayList<Departure> splitArrayListDepartureByDay(ArrayList<Departure> items, String calendar) {
        int initialPosition = -1;

        for (Departure departure : items) {
            initialPosition++;
            if (departure.getCalendar().equals(calendar)) {
                break;
            }
        }

        int finalPosition = initialPosition;

        Departure departure;

        while (finalPosition < items.size()) {
            departure = items.get(finalPosition);

            if (!departure.getCalendar().equals(calendar)) {
                break;
            }

            finalPosition++;
        }

        finalPosition--;

        int arraySize = finalPosition - initialPosition + 1;

        ArrayList<Departure> itemsByDay = new ArrayList<>();

        for (int i = 0; i < arraySize; i++) {
            departure = items.get(initialPosition + i);
            itemsByDay.add(departure);
        }

        return itemsByDay;
    }

    public ArrayList<Departure> createArrayListDepartureWeekDay(ArrayList<Departure> items){
        return splitArrayListDepartureByDay(items, "WEEKDAY");
    }

    public ArrayList<Departure> createArrayListDepartureSaturday(ArrayList<Departure> items){
        return splitArrayListDepartureByDay(items, "SATURDAY");
    }

    public ArrayList<Departure> createArrayListDepartureSunday(ArrayList<Departure> items){
        return splitArrayListDepartureByDay(items, "SUNDAY");
    }
}
