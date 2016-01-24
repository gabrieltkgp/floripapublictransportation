package com.arctouch.floripapublictransportation.entities;

/**
 * Created by GabrielPacheco on 15/01/2016.
 */
public class Departure {
    private Integer id;
    private String calendar;
    private String time;

    public Departure(Integer id, String calendar, String time) {
        this.id = id;
        this.calendar = calendar;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalendar() {
        return calendar;
    }


    public String getTime() {
        return time;
    }

}
