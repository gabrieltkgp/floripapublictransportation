package com.arctouch.floripapublictransportation.tools;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.mock.MockContext;

import com.arctouch.floripapublictransportation.activities.ListRouteActivity;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by GabrielPacheco on 22/01/2016.
 */
@RunWith(AndroidJUnit4.class)
public class RestConfigurationTest {

    @Test(expected = NullPointerException.class)
    public void testCreateRestConfigurationContextNull() throws IOException {
        RestConfiguration restConfiguration = new RestConfiguration(null);
    }

    @Rule
    public ActivityTestRule<ListRouteActivity> listRouteActivity = new ActivityTestRule<>(ListRouteActivity.class);

    @Test
     public void testGetUser() throws IOException {
        Context context = listRouteActivity.getActivity().getBaseContext();
        RestConfiguration restConfiguration = new RestConfiguration(context);

        restConfiguration.readProperties();

        Assert.assertEquals(restConfiguration.getUser(), "WKD4N7YMA1uiM8V");
    }

    @Test
    public void testGetPassword() throws IOException {
        Context context = listRouteActivity.getActivity().getBaseContext();
        RestConfiguration restConfiguration = new RestConfiguration(context);

        restConfiguration.readProperties();

        Assert.assertEquals(restConfiguration.getPassword(), "DtdTtzMLQlA0hk2C1Yi5pLyVIlAQ68");
    }

    @Test
    public void testGetUrlFindRoutes() throws IOException {
        Context context = listRouteActivity.getActivity().getBaseContext();
        RestConfiguration restConfiguration = new RestConfiguration(context);

        restConfiguration.readProperties();

        Assert.assertEquals(restConfiguration.getUrlFindRoutes(), "https://api.appglu.com/v1/queries/findRoutesByStopName/run");
    }

    @Test
    public void testGetUrlFindStops() throws IOException {
        Context context = listRouteActivity.getActivity().getBaseContext();
        RestConfiguration restConfiguration = new RestConfiguration(context);

        restConfiguration.readProperties();

        Assert.assertEquals(restConfiguration.getUrlFindStops(), "https://api.appglu.com/v1/queries/findStopsByRouteId/run");
    }

    @Test
    public void testGetUrlFindDepartures() throws IOException {
        Context context = listRouteActivity.getActivity().getBaseContext();
        RestConfiguration restConfiguration = new RestConfiguration(context);

        restConfiguration.readProperties();

        Assert.assertEquals(restConfiguration.getUrlFindDepartures(), "https://api.appglu.com/v1/queries/findDeparturesByRouteId/run");
    }
}