package com.naca.mealacle.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartProduct extends ViewModel {
    private Food food;
    private int selectedOption;
    private MutableLiveData<Integer> count = new MutableLiveData<>();
    private MutableLiveData<Integer> totalPrice = new MutableLiveData<>();

    public CartProduct(Food food, int option, int count){
        this.food = food;
        this.selectedOption = option;
        this.count.setValue(count);
    }

    public MutableLiveData<Integer> getCount() {
        if (count == null){
            count = new MutableLiveData<>();
        }
        return count;
    }

    public void setCount(Integer count){
        this.count.setValue(count);
    }

    public MutableLiveData<Integer> getTotalPrice() {
        if (totalPrice == null){
            totalPrice = new MutableLiveData<>();
        }
        return totalPrice;
    }

    public void setTotalPrice(Integer price) {
        this.totalPrice.setValue(price);
    }
}
