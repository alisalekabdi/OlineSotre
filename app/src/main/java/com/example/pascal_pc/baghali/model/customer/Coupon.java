package com.example.pascal_pc.baghali.model.customer;

import com.google.gson.annotations.SerializedName;

public class Coupon {

    @SerializedName("id")
    private int _Id;
    private String code ;
    private String amount;

    public Coupon(String code) {
        this.code = code;

    }

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
