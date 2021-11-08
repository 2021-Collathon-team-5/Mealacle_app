package com.naca.mealacle.data;

public class Delivery {
    private String address;
    private String product;

    public Delivery(String address, String product){
        this.address = address;
        this.product = product;
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
}
