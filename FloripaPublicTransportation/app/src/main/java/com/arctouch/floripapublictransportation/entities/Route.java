package com.arctouch.floripapublictransportation.entities;

import java.io.Serializable;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class Route implements Serializable{

    private Integer id;
    private String shortName;
    private String longName;
    private String lastModifiedDate;
    private Integer agencyId;

    public Route(Integer id, String shortName, String longName, String lastModifiedDate, Integer agencyId) {
        this.id = id;
        this.shortName = shortName;
        this.longName = longName;
        this.lastModifiedDate = lastModifiedDate;
        this.agencyId = agencyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }
}
