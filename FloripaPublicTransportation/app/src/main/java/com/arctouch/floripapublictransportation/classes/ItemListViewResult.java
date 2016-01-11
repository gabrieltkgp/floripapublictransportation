package com.arctouch.floripapublictransportation.classes;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class ItemListViewResult {

    private String text;
    private int id;

    public ItemListViewResult(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
