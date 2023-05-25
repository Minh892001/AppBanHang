package com.example.appbanhang.model;

import java.io.Serializable;

public class Item implements Serializable {
    private int id;
    private String name, destinationFrom, destinationTo, price, date;
    private int total;

    public Item() {
    }

    public Item(int id, String name, String destinationFrom, String destinationTo, String price, String date, int total) {
        this.id = id;
        this.name = name;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.price = price;
        this.date = date;
        this.total = total;
    }

    public Item(String name, String destinationFrom, String destinationTo, String price, String date) {
        this.name = name;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.price = price;
        this.date = date;
    }

    public Item(int id, String name, String destinationFrom, String destinationTo, String price, String date) {
        this.id = id;
        this.name = name;
        this.destinationFrom = destinationFrom;
        this.destinationTo = destinationTo;
        this.price = price;
        this.date = date;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public String getDestinationFrom() {
        return destinationFrom;
    }

    public void setDestinationFrom(String destinationFrom) {
        this.destinationFrom = destinationFrom;
    }

    public String getDestinationTo() {
        return destinationTo;
    }

    public void setDestinationTo(String destinationTo) {
        this.destinationTo = destinationTo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
