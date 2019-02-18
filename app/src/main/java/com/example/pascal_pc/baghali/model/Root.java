package com.example.pascal_pc.baghali.model;

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
