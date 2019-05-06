package com.example.pascal_pc.baghali.controller.finalizeOrder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pascal_pc.baghali.R;


public class OrderActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, OrderActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.order_fragment_container)==null){
            fragmentManager.beginTransaction()
                    .replace(R.id.order_fragment_container,OrderFragment.newInstance())
                    .commit();
        }
    }
}
