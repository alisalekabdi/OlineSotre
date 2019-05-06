package com.example.pascal_pc.baghali.controller.splashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.controller.home.HomeActivity;
import com.example.pascal_pc.baghali.R;
import com.example.pascal_pc.baghali.controller.home.ProductListViewFragment;
import com.example.pascal_pc.baghali.model.ProductList;
import com.example.pascal_pc.baghali.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView mAnimationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ProductList productList = ProductList.getOurInstance();
        mAnimationView = findViewById(R.id.entrance_animation);
        mAnimationView.playAnimation();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimationView.pauseAnimation();
                Intent intent = HomeActivity.newIntent(SplashActivity.this);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, 5000);

    }

}
