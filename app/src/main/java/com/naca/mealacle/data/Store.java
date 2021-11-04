package com.naca.mealacle.data;

import java.io.Serializable;

public class Store implements Serializable {
    String name;
    String address;
    String product;
    String cost;
    String time;

    public Store(String name, String address, String product, String cost, String time){
        this.name = name;
        this.address = address;
        this.product = product;
        this.cost = cost;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
