package com.arctouch.floripapublictransportation.tools;

import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.arctouch.floripapublictransportation.entities.Departure;
import com.arctouch.floripapublictransportation.general.DepartureDay;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 20/01/2016.
 */
public class ProcessDeparture {

    private int getInitialPosition(ArrayList<Departure> items, DepartureDay departureDay){
        int initialPosition = -1;
        Departure departure;
        for (int i = 0; i < items.size(); i++) {
            departure = items.get(i);
            if (departure.getCalendar().equals(departureDay.toString())) {
                initialPosition = i;
                break;
            }
        }

        return initialPosition;
    }

    private int getFinalPosition(ArrayList<Departure> items, DepartureDay departureDay, int initialPosition){
        if (initialPosition < 0){
            return -1;
        }

        int finalPosition = initialPosition;

        Departure departure;

        while (finalPosition < items.size()) {
            departure = items.get(finalPosition);

            if (!departure.getCalendar().equals(departureDay.toString())) {
                break;
            }

            finalPosition++;
        }

        finalPosition--;

        return finalPosition;
    }


    private ArrayList<Departure> getArrayListByDay(ArrayList<Departure> items, int initialPosition, int finalPosition){

        ArrayList<Departure> itemsByDay = new ArrayList<>();

        if (initialPosition < 0){
            return itemsByDay;
        }

        Departure departure;

        int arraySize = finalPosition - initialPosition + 1;

        for (int i = 0; i < arraySize; i++) {
            departure = items.get(initialPosition + i);
            itemsByDay.add(departure);
        }

        return itemsByDay;
    }

    private ArrayList<Departure> splitArrayListDepartureByDay(ArrayList<Departure> items, DepartureDay departureDay) {

        int initialPosition = getInitialPosition(items, departureDay);

        int finalPosition = getFinalPosition(items, departureDay, initialPosition);

        return getArrayListByDay(items, initialPosition, finalPosition);
    }

    public ArrayList<Departure> createArrayListDepartureWeekDay(ArrayList<Departure> items){
        return splitArrayListDepartureByDay(items, DepartureDay.WEEKDAY);
    }

    public ArrayList<Departure> createArrayListDepartureSaturday(ArrayList<Departure> items){
        return splitArrayListDepartureByDay(items, DepartureDay.SATURDAY);
    }

    public ArrayList<Departure> createArrayListDepartureSunday(ArrayList<Departure> items){
        return splitArrayListDepartureByDay(items, DepartureDay.SUNDAY);
    }
}
