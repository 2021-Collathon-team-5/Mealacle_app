package com.naca.mealacle.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Serializable;
import java.text.DecimalFormat;

public class CartProduct implements Serializable {
    private Food food;
    private int count;
    private int option;

    public CartProduct(Food food, int option, int count){
        this.food = food;
        this.option = option;
        this.count = count;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getTotal(){
        DecimalFormat format = new DecimalFormat("###,###");
        long price = (food.getPrice() + Integer.parseInt(String.valueOf(food.getOptions().get(getOption()).get("price"))) ) * count;
        return format.format(price) + "원";
    }

    public long getTotalLong(){
        return (food.getPrice() + Integer.parseInt(String.valueOf(food.getOptions().get(getOption()).get("price"))) ) * count;
    }
}
