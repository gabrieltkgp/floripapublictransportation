package com.arctouch.floripapublictransportation.entities;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class Stop {
    private int id;
    private String name;
    private int sequence;
    private int routeId;

    public Stop(int id, String name, int sequence, int routeId) {
        this.id = id;
        this.name = name;
        this.sequence = sequence;
        this.routeId = routeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
