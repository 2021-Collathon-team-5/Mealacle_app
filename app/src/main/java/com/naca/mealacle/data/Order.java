package com.naca.mealacle.data;

public class Order {
    String foodID;
    String userID;
    String riderID;
    String sellerID;
    int count;
    int option;
    int profile_idx;
    boolean isAssign;
    boolean isReady;
    boolean isComplete;

    public Order(){

    }

    public Order(String foodID, String userID, String riderID, String sellerID, int count, int option,
                 int profile_idx, boolean isAssign, boolean isReady, boolean isComplete) {
        this.foodID = foodID;
        this.userID = userID;
        this.riderID = riderID;
        this.sellerID = sellerID;
        this.count = count;
        this.option = option;
        this.profile_idx = profile_idx;
        this.isAssign = isAssign;
        this.isReady = isReady;
        this.isComplete = isComplete;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getRiderID() {
        return riderID;
    }

    public void setRiderID(String riderID) {
        this.riderID = riderID;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
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

    public int getProfile_idx() {
        return profile_idx;
    }

    public void setProfile_idx(int profile_idx) {
        this.profile_idx = profile_idx;
    }

    public boolean isAssign() {
        return isAssign;
    }

    public void setAssign(boolean assign) {
        isAssign = assign;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
