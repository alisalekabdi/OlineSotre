package com.example.pascal_pc.baghali.model.category;

import com.google.gson.annotations.SerializedName;

public class ProductCatergory {
    private String id;
    @SerializedName("slug")
    private String mName;

    public ProductCatergory(String id, String name) {
        this.id = id;
        mName = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return mName;
    }
}
