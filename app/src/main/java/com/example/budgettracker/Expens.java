package com.example.budgettracker;

import java.io.Serializable;

public class Expens implements Serializable {

    int id;
    String name;
    String category;
    String date;
    Double amount;
    String latitude;
    String longitude;
    String pathReceipt;

    public Expens(int id, String name, String category, String date, Double amount, String latitude, String longitude,String pathReceipt) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.date = date;
        this.amount = amount;
        this.latitude = latitude;
        this.longitude = longitude;
        this.pathReceipt= pathReceipt;
    }

    public String getPathReceipt() {
        return pathReceipt;
    }

    public void setPathReceipt(String pathReceipt) {
        this.pathReceipt = pathReceipt;
    }

    public Expens() {

    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
