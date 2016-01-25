package com.arctouch.floripapublictransportation.interfaces;

import com.arctouch.floripapublictransportation.general.RestType;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 15/01/2016.
 * interface created to receive messages from RestConnectionImpl classes
 */
public interface AsyncResponse {
    void processFinish(ArrayList items, RestType restType);
    void showMessage(String message);
}
