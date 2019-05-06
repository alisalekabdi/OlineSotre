package com.example.pascal_pc.baghali.controller.cart;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pascal_pc.baghali.R;

public class CartActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,CartActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.card_fragment_container)==null){
            fragmentManager.beginTransaction()
                    .add(R.id.card_fragment_container,CartFragment.newInstance())
                    .commit();
        }
    }
}
