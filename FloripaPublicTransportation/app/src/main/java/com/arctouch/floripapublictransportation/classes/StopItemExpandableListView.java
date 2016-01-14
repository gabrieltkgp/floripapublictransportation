package com.arctouch.floripapublictransportation.classes;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class StopItemExpandableListView {
    private int stopId;
    private String stopName;
    private int stopSequence;
    private int routeId;

    public StopItemExpandableListView() {
    }

    public int getStopId() {
        return stopId;
    }

    public void setStopId(int stopId) {
        this.stopId = stopId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public int getStopSequence() {
        return stopSequence;
    }

    public void setStopSequence(int stopSequence) {
        this.stopSequence = stopSequence;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
