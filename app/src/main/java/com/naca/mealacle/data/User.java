package com.naca.mealacle.data;

import com.naca.mealacle.R;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class User implements Serializable {
    private String univ;
    private String name;
    private String address;
    private String email;
    private String phone;
    private List<String> orderList;
    private List<String> riderID;
    private int imageID = R.drawable.user_default;

    public User(){

    }

    public User(String univ, String name, String address, String email, String phone) {
        this.univ = univ;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        orderList = new LinkedList<>();
        riderID = new LinkedList<>();
    }

    public String getUniv() {
        return univ;
    }

    public void setUniv(String univ) {
        this.univ = univ;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getImageID() {
        return R.drawable.user_default;
    }

    public List<String> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<String> orderList) {
        this.orderList = orderList;
    }

    public List<String> getRiderID() {
        return riderID;
    }

    public void setRiderID(List<String> riderID) {
        this.riderID = riderID;
    }
}
