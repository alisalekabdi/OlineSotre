package com.example.pascal_pc.baghali.model.product;


import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("src")
    private String mPath;

    public String getPath() {
        return mPath;
    }

    public Image(String path) {

        mPath = path;
    }
}
