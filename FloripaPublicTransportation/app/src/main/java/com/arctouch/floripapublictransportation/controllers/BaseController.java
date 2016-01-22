package com.arctouch.floripapublictransportation.controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.arctouch.floripapublictransportation.general.Constants;
import com.arctouch.floripapublictransportation.tools.RestConfiguration;

import java.io.IOException;

/**
 * Created by GabrielPacheco on 21/01/2016.
 */
public class BaseController {

    private Context context;
    private RestConfiguration restConfiguration;

    public BaseController(Context context) {
        this.context = context;
    }

    private boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        return ((networkInfo != null) && networkInfo.isConnected());
    }

    private boolean readRestConfiguration(){
        restConfiguration = new RestConfiguration(getContext());

        try {

            restConfiguration.readProperties();

            return true;

        } catch (IOException e) {
            showMessage("Properties not found.");
            return false;
        }
    }

    protected boolean validationRestConnection(){
        if (!isConnected()) {
            showMessage("No network connection available.");
            return false;
        }

        return readRestConfiguration();
    }

    public Context getContext() {
        return context;
    }

    public void showMessage(String message) {
        Log.i(Constants.TAG_LOG_INFO, message);

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public RestConfiguration getRestConfiguration() {
        return restConfiguration;
    }
}
