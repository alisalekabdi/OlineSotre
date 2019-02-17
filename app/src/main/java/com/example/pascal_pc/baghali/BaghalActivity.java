package com.example.pascal_pc.baghali;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.pascal_pc.baghali.Network.Api;
import com.example.pascal_pc.baghali.Network.RetrofitClientInstance;
import com.example.pascal_pc.baghali.model.Prodcts;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BaghalActivity extends AppCompatActivity {

    // TODO: 2/17/2019 use progress bar or progress dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baghal);

        RetrofitClientInstance.getRetrofitInstance()
                .create(Api.class)
                .getProducts()
                .enqueue(new Callback<List<Prodcts>>() {
                    @Override
                    public void onResponse(Call<List<Prodcts>> call, Response<List<Prodcts>> response) {
                        if (response.isSuccessful()){
                            List<Prodcts> prodctsList=response.body();
                        }

                    }

                    @Override
                    public void onFailure(Call<List<Prodcts>> call, Throwable t) {
                        Toast.makeText(BaghalActivity.this, "jjJJJ", Toast.LENGTH_LONG).show();
                        // TODO: 2/17/2019 show snackBar message for problem with request

                    }
                });
    }
}
