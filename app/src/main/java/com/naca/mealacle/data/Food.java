package com.naca.mealacle.data;

import java.io.Serializable;

public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private int cost;

    public Food(String name, int cost){
        setName(name);
        setCost(cost);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
