package com.example.pascal_pc.baghali.Network;

import com.example.pascal_pc.baghali.model.product.Product;
import com.example.pascal_pc.baghali.model.category.ProductCatergory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @GET("products?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210&" +
            "consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<Product>> getProducts();

    // you can use retrofit to post get delete and put

    @GET("products?consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef" +
            "&orderby=popularity")
    Call<List<Product>> getBestSellerList();

    @GET("products?consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef" +
            "&orderby=rating")
    Call<List<Product>> getPopularList();
    @GET("products/{id}/" +
            "?consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<Product> getProduct(@Path("id")Integer productId);

    @GET("products/categories/?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<ProductCatergory>> getProductCategory();

    @GET("products?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<Product>> getProductWithCategory(@Query("category")String productId);

    @GET("products?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<Product>> getProductWithSearch(@Query("search")String searchString);

}
