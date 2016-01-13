package com.arctouch.floripapublictransportation.classes;

import android.content.Context;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.R;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by GabrielPacheco on 13/01/2016.
 */
public class RestConfiguration {

    private String user;
    private String password;
    private String urlFindRoutes;
    private String urlFindStops;
    private String urlFindDepartures;

    public RestConfiguration(Context context) {
        readProperties(context);
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getUrlFindRoutes() {
        return urlFindRoutes;
    }

    public String getUrlFindDepartures() {
        return urlFindDepartures;
    }

    public String getUrlFindStops() {
        return urlFindStops;
    }

    public InputStream readRaw(Context context, int rawResId) {
        return context.getResources().openRawResource(rawResId);
    }

    protected void readProperties(Context context) {
        Properties pp = new Properties();

        try {
            pp.load(readRaw(context, R.raw.configuration));

            this.user = pp.getProperty("user").toString();
            this.password = pp.getProperty("password").toString();
            this.urlFindRoutes = pp.getProperty("urlFindRoutes").toString();
            this.urlFindStops = pp.getProperty("urlFindStops").toString();
            this.urlFindDepartures = pp.getProperty("urlFindDepartures").toString();

        } catch (Exception e) {
            Toast.makeText(context, "Properties not found.", Toast.LENGTH_LONG).show();
        }
    }


}
