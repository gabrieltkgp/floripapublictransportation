package com.arctouch.floripapublictransportation.interfaces;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 15/01/2016.
 */
public interface AsyncResponse {
    void processFinish(ArrayList items);
    void showMessageToast(String message);
}
