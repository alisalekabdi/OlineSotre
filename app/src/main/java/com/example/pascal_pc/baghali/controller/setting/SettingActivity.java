package com.example.pascal_pc.baghali.controller.setting;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.cart.CartActivity;

public class SettingActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context,SettingActivity.class);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        FragmentManager fragmentManager=getSupportFragmentManager();

        if(fragmentManager.findFragmentById(R.id.settings_frg_container)==null){
            fragmentManager.beginTransaction()
                    .add(R.id.settings_frg_container,SettingFragment.newInstance())
                    .commit();
        }
    }
}
