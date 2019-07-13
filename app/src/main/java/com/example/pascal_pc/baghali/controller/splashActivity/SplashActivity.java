package com.example.pascal_pc.baghali.controller.splashActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.pascal_pc.baghali.model.Root;
import com.example.pascal_pc.baghali.model.product.Product;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private LottieAnimationView mAnimationView;
    private ProductList productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        productList = ProductList.getOurInstance();
        /**
         * here we get popular list in AsynckTask in Background thread
         */
        new PopularProductsAsynckTask(this).execute();
        mAnimationView = findViewById(R.id.entrance_animation);
        mAnimationView.playAnimation();

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                finishLoading();
//            }
//        }, 10000);

        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getProducts()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            productList.setRecentProducts(response.body());
                            finishLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                    }
                });
        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getBestSellerList()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        if (response.isSuccessful()) {
                            productList.setBestSellerProducts(response.body());finishLoading();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                    }
                });


    }

    private void finishLoading() {
        if (productList.getmBestSellerProducts().size() > 0 && productList.getmPopularProducts().size() > 0
                && productList.getmRecentProducts().size() > 0) {
            mAnimationView.pauseAnimation();
            Intent intent = HomeActivity.newIntent(SplashActivity.this);
            startActivity(intent);
            SplashActivity.this.finish();
        }
    }

    private class PopularProductsAsynckTask extends AsyncTask<Void, Void, List<Product>> {
        public PopularProductsAsynckTask(Activity activity) {
        }

        @Override
        protected List<Product> doInBackground(Void... voids) {
            Response<List<Product>> response = null;

            try {
                response = RetrofitClientInstance.getRetrofitInstance().create(Api.class).
                        getPopularList().execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response.body();
        }

        @Override
        protected void onPostExecute(List<Product> products) {
            super.onPostExecute(products);
            productList.setPopularProducts(products);
            finishLoading();
            Log.e("alisalek", "onPostExecute: " + products.size());
        }
    }

}
