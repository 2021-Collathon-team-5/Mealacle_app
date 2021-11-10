package com.naca.mealacle.data;

public class Delivery {
    Order order;
    User user;
    String orderID;
    String productName;

    public Delivery(Order order, User user, String orderID, String productName) {
        this.order = order;
        this.user = user;
        this.orderID = orderID;
        this.productName = productName;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
