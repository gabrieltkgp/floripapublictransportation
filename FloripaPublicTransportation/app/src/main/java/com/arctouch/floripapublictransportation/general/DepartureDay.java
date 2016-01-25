package com.arctouch.floripapublictransportation.general;

/**
 * Created by GabrielPacheco on 21/01/2016.
 * enum created to identify departures.
 */
public enum DepartureDay {
    WEEKDAY("WEEKDAY"),
    SATURDAY("SATURDAY"),
    SUNDAY("SUNDAY");

    private String stringValue;

    DepartureDay(String toString) {
        stringValue = toString;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
