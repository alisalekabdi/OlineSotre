package com.example.pascal_pc.baghali.model;

import com.example.pascal_pc.baghali.model.product.Product;

import java.util.List;

public class Root {

    private List<Product> mProdcts;

    public Root(List<Product> prodcts) {
        mProdcts = prodcts;
    }

    public List<Product> getProdcts() {
        return mProdcts;
    }
}
