package com.example.pascal_pc.baghali.controller.productInfo;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.pascal_pc.baghali.R;

public class ProductInfoActivity extends AppCompatActivity {


    private static final String EXTRA_PRODUCT_ID ="product_id";
    private int mProductId;

    public static Intent newIntent(Context context, int productid){
        Intent intent=new Intent(context,ProductInfoActivity.class);
        intent.putExtra(EXTRA_PRODUCT_ID,productid);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_info_main);
        mProductId=getIntent().getIntExtra(EXTRA_PRODUCT_ID,0);

        FragmentManager fragmentManager=getSupportFragmentManager();
        if(fragmentManager.findFragmentById(R.id.fragment_info_container)==null){
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_info_container,ProductInfFragment.newInstance(mProductId))
                    .commit();
        }
    }
}
