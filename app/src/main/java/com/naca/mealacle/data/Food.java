package com.naca.mealacle.data;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import java.io.Serializable;

public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name = "0";
    private String price = "0";
    private String productID = "0";
    private String manufacture = "0";
    private String model = "0";
    private String brand = "0";
    private String origin = "0";


    private int imageID;
    private int detailImageID;

    public Food(String name, int price, int imageID){
        setName(name);
        setPrice(price);
        setImageID(imageID);
        setDetailImageID(imageID);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(int price) {
        StringBuilder sb = new StringBuilder();
        sb.append(price /1000);
        sb.append(",");
        if(price %1000 == 0){
            sb.append("000원");
        } else {
            sb.append(price %1000);
            sb.append("원");
        }
        this.price = sb.toString();
    }

    public void setCost(String cost) {
        this.price = cost;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getDetailImageID() {
        return detailImageID;
    }

    public void setDetailImageID(int detailImageID) {
        this.detailImageID = detailImageID;
    }
}
