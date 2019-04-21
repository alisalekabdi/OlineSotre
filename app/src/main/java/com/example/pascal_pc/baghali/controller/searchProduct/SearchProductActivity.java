package com.example.pascal_pc.baghali.controller.searchProduct;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.pascal_pc.baghali.R;

public class SearchProductActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,SearchProductActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        FragmentManager fragmentManager=getSupportFragmentManager();

        if(fragmentManager.findFragmentById(R.id.product_search_fragment_container)==null){
            fragmentManager.beginTransaction()
                    .replace(R.id.product_search_fragment_container,SearchProductsFragment.newInstance())
                    .commit();
        }

    }

}
