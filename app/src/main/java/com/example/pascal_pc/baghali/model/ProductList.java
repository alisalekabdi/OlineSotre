package com.example.pascal_pc.baghali.model;


import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductList {
    private List<Product> mRecentProducts;
    private List<Product> mBestSellerProducts;
    private List<Product> mPopularProducts;
    private static ProductList ourInstance;

    private ProductList() {
        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getProducts()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            mRecentProducts = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                    }
                });
        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getPopularList()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            mPopularProducts = response.body();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                    }
                });
        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getBestSellerList()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            mBestSellerProducts = response.body();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                    }
                });
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
}
