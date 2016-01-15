package com.arctouch.floripapublictransportation.entity;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class Route {

    private int id;
    private String shortName;
    private String longName;
    private String lastModifiedDate;
    private int agencyId;

    public Route(int id, String shortName, String longName, String lastModifiedDate, int agencyId) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.lastModifiedDate = lastModifiedDate;
        this.agencyId = agencyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }
}
