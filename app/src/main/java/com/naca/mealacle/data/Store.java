package com.naca.mealacle.data;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import java.io.Serializable;

public class Store implements Serializable {
    String name;
    String address;
    String product;
    String cost;
    String time;
    String productID = "0";
    String addTime = "0";
    String adderName = "0";
    String contract = "0";
    String size = "가로 0cm / 세로 0cm / 높이 0cm";

    int imageID;
    int mapImageID;

    public Store(String name, String address, String product, String cost, String time, int imageID){
        this.name = name;
        this.address = address;
        this.product = product;
        this.cost = cost;
        this.time = time;
        this.imageID = imageID;
        this.mapImageID = imageID;
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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAdderName() {
        return adderName;
    }

    public void setAdderName(String adderName) {
        this.adderName = adderName;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getMapImageID() {
        return mapImageID;
    }

    public void setMapImageID(int mapImageID) {
        this.mapImageID = mapImageID;
    }
}
