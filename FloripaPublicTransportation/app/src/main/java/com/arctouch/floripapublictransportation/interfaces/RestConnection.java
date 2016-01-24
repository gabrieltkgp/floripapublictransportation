package com.arctouch.floripapublictransportation.interfaces;

import java.util.ArrayList;

/**
 * Created by GabrielPacheco on 24/01/2016.
 */
public interface RestConnection {
    String getJsonParams();
    ArrayList parseJson(String jsonResult);
    void processFinish(ArrayList items);
}
