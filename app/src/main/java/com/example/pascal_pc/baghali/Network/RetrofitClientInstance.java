package com.example.pascal_pc.baghali.Network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {

    private static final String BASE_URL="https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    private static Retrofit retrofitInstance;

    public static Retrofit getRetrofitInstance(){

        if(retrofitInstance==null){
            retrofitInstance=new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofitInstance;
    }


}