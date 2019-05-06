package com.example.pascal_pc.baghali.model.customer;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Id;

public class Order {
    @Id
    @SerializedName("product_id")
    private  int _id;
    @SerializedName("quantity")
    private int Count;

    public Order(int _id, int count) {
        this._id = _id;
        Count = count;
    }
}
