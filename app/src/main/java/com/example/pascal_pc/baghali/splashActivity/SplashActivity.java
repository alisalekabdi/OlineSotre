package com.example.pascal_pc.baghali.splashActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.pascal_pc.baghali.BaghalActivity;
import com.example.pascal_pc.baghali.R;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView mAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAnimationView=findViewById(R.id.entrance_animation);
        mAnimationView.playAnimation();

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mAnimationView.pauseAnimation();
                Intent intent=BaghalActivity.newIntent(SplashActivity.this);
                startActivity(intent);
            }
        },3000);
    }

}
