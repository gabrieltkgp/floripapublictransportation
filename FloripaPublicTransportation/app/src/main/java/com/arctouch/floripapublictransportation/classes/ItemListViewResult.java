package com.arctouch.floripapublictransportation.classes;

/**
 * Created by GabrielPacheco on 05/01/2016.
 */
public class ItemListViewResult {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String text;

    public ItemListViewResult(){}

    public ItemListViewResult(String text)
    {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
