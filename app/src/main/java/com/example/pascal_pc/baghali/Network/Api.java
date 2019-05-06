package com.example.pascal_pc.baghali.Network;

import com.example.pascal_pc.baghali.model.customer.Customer;
import com.example.pascal_pc.baghali.model.customer.OrderJsonBody;
import com.example.pascal_pc.baghali.model.product.Product;
import com.example.pascal_pc.baghali.model.category.ProductCatergory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Call<Product> getProduct(@Path("id") Integer productId);

    @GET("products/categories/?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<ProductCatergory>> getProductCategory();

    @GET("products?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<Product>> getProductWithCategory(@Query("category") String productId);

    @GET("products?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
    Call<List<Product>> getProductWithSearch(@Query("search") String searchString);

    @POST("customers?" +
            "consumer_key=ck_b45a5d66023bec9aec70b2d6d088e55bc11c0210" +
            "&consumer_secret=cs_92e419a6da8c4f6b7a6f60a5020b667ce49854ef")
            @FormUrlEncoded
    Call<Customer> createCustomer(@Field("first_name") String first_name,
                                  @Field("last_name") String last_name,
                                  @Field("username") String username,
                                  @Field("email") String email);

    @POST("orders?" +
            "consumer_key=ck_00fdf4e3f65c5275d802b412db586ba2cac6835f" +
            "&consumer_secret=cs_d2571d995db502ea4b04bfae270b92ac447eb8ba")
    Call<Customer> sendOrder(@Body OrderJsonBody orderJsonBody);

}
