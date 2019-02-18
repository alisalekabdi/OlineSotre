package com.example.pascal_pc.baghali;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class BaghalActivity extends AppCompatActivity {
    public static final int REQ_ID_NEWEST_PRODUCT = 1;
    public static final int REQ_ID_POPULAR_PRODUCT = 2;
    public static final int REQ_ID_MOST_SELL_PRODUCT = 3;

    // TODO: 2/17/2019 use progress bar or progress dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.findFragmentById(R.id.newest_products_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.newest_products_container,
                            ProductListViewFragment.newInstance(REQ_ID_NEWEST_PRODUCT))
                    .commit();
        }
        if (fragmentManager.findFragmentById(R.id.popular_products_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.popular_products_container,
                            ProductListViewFragment.newInstance(REQ_ID_POPULAR_PRODUCT))
                    .commit();
        }
        if (fragmentManager.findFragmentById(R.id.most_sell_products_container) == null) {
            fragmentManager.beginTransaction()
                    .replace(R.id.most_sell_products_container,
                            ProductListViewFragment.newInstance(REQ_ID_MOST_SELL_PRODUCT))
                    .commit();
        }

    }
}
