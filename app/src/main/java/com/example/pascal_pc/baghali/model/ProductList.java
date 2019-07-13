package com.example.pascal_pc.baghali.model;

import com.example.pascal_pc.baghali.model.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductList {
    private List<Product> mRecentProducts=new ArrayList<>();
    private List<Product> mBestSellerProducts=new ArrayList<>();
    private List<Product> mPopularProducts=new ArrayList<>();
    private static ProductList ourInstance;

    private ProductList() {
    }

    public List<Product> getmRecentProducts() {
        return mRecentProducts;
    }

    public List<Product> getmBestSellerProducts() {
        return mBestSellerProducts;
    }

    public List<Product> getmPopularProducts() {
        return mPopularProducts;
    }

    public static ProductList getOurInstance() {

        if (ourInstance == null) {
            ourInstance = new ProductList();
        }
        return ourInstance;
    }

    public void setRecentProducts(List<Product> recentProducts) {
        mRecentProducts = recentProducts;
    }

    public void setBestSellerProducts(List<Product> bestSellerProducts) {
        mBestSellerProducts = bestSellerProducts;
    }

    public void setPopularProducts(List<Product> popularProducts) {
        mPopularProducts = popularProducts;
    }
}
