package com.naca.mealacle.data;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Store implements Serializable {
    private String name; // 가게 이름
    private String address; // 가게 주소
    private String product; // 배달해야하는 상품 명
    private String cost = "2000원 (개당)"; // 배달 단가
    private String time = "오후 9:00"; // 배달 시간
    private String productID = "0"; // 상품번호
    private String addTime = "0"; // 등록 시간
    private String adderName = "0"; // 업체 사장 이름
    private String contract = "01000000000"; // 업체 사장 번호
    private String size = "가로 30cm / 세로 20cm / 높이 5cm"; // 상품 크기
    private Food food;
    private int count = 0;
    private String orderID;

    private List<Store> riderList = new LinkedList<>();

    private String image;
    private String mapImage;

    public Store(){

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

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMapImage() {
        return mapImage;
    }

    public void setMapImage(String mapImage) {
        this.mapImage = mapImage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Store> getRiderList() {
        return riderList;
    }

    public void setRiderList(List<Store> riderList) {
        this.riderList = riderList;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }
}
