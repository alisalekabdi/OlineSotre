package com.example.pascal_pc.baghali.controller.registerCustomer;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pascal_pc.baghali.R;

public class RegisterCustActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        return new Intent(context,RegisterCustActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_cust);

        FragmentManager fragmentManager=getSupportFragmentManager();

        if(fragmentManager.findFragmentById(R.id.register_fragment_container)==null){
            fragmentManager.beginTransaction()
                    .add(R.id.register_fragment_container,RegisterCustFragment.newInstance())
                    .commit();
        }
    }
}
