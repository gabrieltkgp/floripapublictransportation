package com.arctouch.floripapublictransportation.entity;

/**
 * Created by GabrielPacheco on 15/01/2016.
 */
public class Departure {
    private int id;
    private String calendar;
    private String time;

    public Departure(int id, String calendar, String time) {
        this.id = id;
        this.calendar = calendar;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
