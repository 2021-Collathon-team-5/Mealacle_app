package com.naca.mealacle.data;

import android.view.View;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;

import com.naca.mealacle.R;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dbID = "";
    private String name = "0"; // 상품 이름
    private long price = 0; // 상품 가격
    private String price_string;
    private String productID = "0"; // 상품 번호
    private String manufacture = "0"; // 제조사
    private String origin = "0"; // 원산지
    private List<HashMap<String, Object>> options;

    private int imageID = R.drawable.ic_launcher_background; // 상품 이미지id << 방식 변경 예정
    private int detailImageID = R.drawable.ic_launcher_background; // 상품 설명 이미지 id << 방식 변경 예정

    private String images;
    private String description;

    public Food() {

    }

    public Food(String dbID, String name, long price, String images,
                String description, List<HashMap<String, Object>> options, String origin){
        setDbID(dbID);
        setName(name);
        setPrice(price);
        setImageID(imageID);
        setDetailImageID(imageID);
        setImages(images);
        setDescription(description);
        setOptions(options);
        setOrigin(origin);
    }

    public Food(String name, int price, int imageID){
        setName(name);
        setPrice(price);
        setImageID(imageID);
        setDetailImageID(imageID);
    }

    public String getDbID() {
        return dbID;
    }

    public void setDbID(String dbID) {
        char[] idarr = dbID.toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<5;i++){
            sb.append((int) idarr[i]);
        }
        this.dbID = sb.toString().substring(0, 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        setPrice_string(price);
        this.price = price;
    }

    public String getPrice_string() {
        return price_string;
    }

    public void setPrice_string(long price) {
        DecimalFormat format = new DecimalFormat("###,###");
        this.price_string = format.format(price) + "원";
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<HashMap<String, Object>> getOptions() {
        return options;
    }

    public void setOptions(List<HashMap<String, Object>> options) {
        this.options = options;
    }
}
