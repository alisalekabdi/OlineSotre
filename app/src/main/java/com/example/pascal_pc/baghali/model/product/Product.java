package com.example.pascal_pc.baghali.model.product;

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
    private List<Attributes> attributes;

    public Product(List<Image> images, String name, String price, int id, String description, String createdDate,
                   String average_rating, String rating_count, String total_sales, List<Attributes> attributes) {
        mImages = images;
        this.name = name;
        this.price = price;
        this.id = id;
        this.description = description;
        this.createdDate = createdDate;
        this.average_rating = average_rating;
        this.rating_count = rating_count;
        this.total_sales = total_sales;
        this.attributes = attributes;
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

    public List<Attributes> getAttributes() {
        return attributes;
    }

    public void setImages(List<Image> images) {
        mImages = images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }

    public void setTotal_sales(String total_sales) {
        this.total_sales = total_sales;
    }

    public void setAttributes(List<Attributes> attributes) {
        this.attributes = attributes;
    }
}
