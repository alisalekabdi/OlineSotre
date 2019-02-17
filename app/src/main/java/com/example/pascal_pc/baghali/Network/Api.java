package com.example.pascal_pc.baghali.Network;

import com.example.pascal_pc.baghali.model.Prodcts;
import com.example.pascal_pc.baghali.model.Root;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("products?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210&" +
            "consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<Prodcts>> getProducts();

    // TODO: 2/17/2019 get specific product's feature by its id by annotation @GET
// you can use retrofit to post get delete and put
    @GET("products?consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef" +
            "&orderby=popularity")
    Call<List<Prodcts>> getBestSellerList();

    @GET("products?consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef" +
            "&orderby=rating")
    Call<List<Prodcts>> getPopularList();
    @GET("https://woocommerce.maktabsharif.ir/wp-json/wc/v3/products/{id}/" +
            "?consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<Prodcts> getProduct(@Path("id")Integer productId);

}
