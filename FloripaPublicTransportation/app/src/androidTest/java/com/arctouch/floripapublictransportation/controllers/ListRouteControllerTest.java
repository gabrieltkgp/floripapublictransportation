package com.arctouch.floripapublictransportation.controllers;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.arctouch.floripapublictransportation.activities.ListRouteActivity;
import com.arctouch.floripapublictransportation.entities.Route;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by GabrielPacheco on 22/01/2016.
 */
@RunWith(AndroidJUnit4.class)
public class ListRouteControllerTest {

    @Rule
    public ActivityTestRule<ListRouteActivity> mActivityRule = new ActivityTestRule<>(ListRouteActivity.class);

    @Test
    public void testCreateIntentDetails() throws Exception {

        ListRouteController listRouteController = new ListRouteController(mActivityRule.getActivity().getBaseContext());

        Route route = new Route(1, "shortName", "longName", "", 1);

        Intent intent = listRouteController.createIntentDetails(route);

        Route route2 = (Route) intent.getSerializableExtra("route");

        Assert.assertEquals(route.getId(), route2.getId());

    }
}