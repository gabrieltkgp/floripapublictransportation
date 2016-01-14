package com.arctouch.floripapublictransportation.classes;

/**
 * Created by GabrielPacheco on 14/01/2016.
 */
public class DepartureSubItemExpandableListView {
    private int departureId;
    private String departureCalendar;
    private String departureTime;

    public DepartureSubItemExpandableListView() {
    }

    public int getDepartureId() {
        return departureId;
    }

    public void setDepartureId(int departureId) {
        this.departureId = departureId;
    }

    public String getDepartureCalendar() {
        return departureCalendar;
    }

    public void setDepartureCalendar(String departureCalendar) {
        this.departureCalendar = departureCalendar;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
