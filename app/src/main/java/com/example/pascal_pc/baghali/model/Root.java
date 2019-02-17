package com.example.pascal_pc.baghali.model;

import java.util.List;

public class Root {

    private List<Prodcts> mProdcts;

    public Root(List<Prodcts> prodcts) {
        mProdcts = prodcts;
    }

    public List<Prodcts> getProdcts() {
        return mProdcts;
    }
}
