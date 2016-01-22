package com.arctouch.floripapublictransportation.tools;

import android.support.test.runner.AndroidJUnit4;

import com.arctouch.floripapublictransportation.entities.Departure;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by GabrielPacheco on 21/01/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ProcessDepartureTest {



    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {

    }

    private ArrayList<Departure> createArrayList(){
        ArrayList<Departure> items = new ArrayList<>();

        for(int i = 1;i <= 5; i++){
            Departure departure = new Departure(i, "WEEKDAY", "06:00");
            items.add(departure);
        }

        for(int i = 6;i <= 10; i++){
            Departure departure = new Departure(i, "SATURDAY", "07:00");
            items.add(departure);
        }

        for(int i = 11;i <= 15; i++){
            Departure departure = new Departure(i, "SUNDAY", "08:00");
            items.add(departure);
        }

        return items;
    }

    @Test
    public void testCreateArrayListDepartureWeekDayWith5Positions() {
        ArrayList<Departure> items = createArrayList();

        ProcessDeparture processDeparture = new ProcessDeparture();

        ArrayList<Departure> itemsWeek = processDeparture.createArrayListDepartureWeekDay(items);

        Assert.assertEquals(itemsWeek.size(), 5);
    }

    @Test
    public void testCreateArrayListDepartureSaturdayWith5Positions() {
        ArrayList<Departure> items = createArrayList();

        ProcessDeparture processDeparture = new ProcessDeparture();

        ArrayList<Departure> itemsWeek = processDeparture.createArrayListDepartureSaturday(items);

        Assert.assertEquals(itemsWeek.size(), 5);
    }

    @Test
    public void testCreateArrayListDepartureSundayWith5Positions(){
        ArrayList<Departure> items = createArrayList();
        ProcessDeparture processDeparture = new ProcessDeparture();

        ArrayList<Departure> itemsWeek = processDeparture.createArrayListDepartureSunday(items);

        Assert.assertEquals(itemsWeek.size(), 5);
    }
}