package com.arctouch.floripapublictransportation.entities;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class Stop {
    private Integer id;
    private String name;
    private Integer sequence;
    private Integer routeId;

    public Stop(Integer id, String name, Integer sequence, Integer routeId) {
        this.id = id;
        this.name = name;
        this.sequence = sequence;
        this.routeId = routeId;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getSequence() {
        return sequence;
    }

    public Integer getRouteId() {
        return routeId;
    }

}
