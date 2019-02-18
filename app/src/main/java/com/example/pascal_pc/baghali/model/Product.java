package com.example.pascal_pc.baghali.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("images")
    private List<Image> mImages;
    private String name;
    private String price;
    private int id;
    private String description;
    @SerializedName("date_created")
    private String createdDate;
    private String average_rating;
    private String rating_count;
    private String total_sales;

    public Product(List<Image> images, String name, String price, int id,
                   String description, String createdDate, String average_rating,
                   String rating_count, String total_sales) {
        mImages = images;
        this.name = name;
        this.price = price;
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.average_rating = average_rating;
        this.rating_count = rating_count;
        this.total_sales = total_sales;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public String getTotal_sales() {
        return total_sales;
    }
}
