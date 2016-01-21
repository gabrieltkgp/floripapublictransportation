package com.arctouch.floripapublictransportation.interfaces;

import com.arctouch.floripapublictransportation.general.RestType;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 15/01/2016.
 */
public interface AsyncResponse {
    void processFinish(ArrayList items, RestType restType);
    void showMessageToast(String message);
}
