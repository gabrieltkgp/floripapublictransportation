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

    public BaseController(Context context) {
        this.context = context;
    }

    protected boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (!((networkInfo != null) && networkInfo.isConnected())){
            return false;
        }

        return true;
    }

    protected RestConfiguration readRestConfiguration(){
        RestConfiguration restConfiguration = new RestConfiguration(getContext());

        try {

            restConfiguration.readProperties();

        } catch (IOException e) {
            return null;
        }

        return restConfiguration;
    }

    public Context getContext() {
        return context;
    }

    public void showMessage(String message) {
        Log.i(Constants.TAG_LOG_INFO, message);

        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
