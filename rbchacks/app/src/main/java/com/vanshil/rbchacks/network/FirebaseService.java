package com.vanshil.rbchacks.network;

import com.vanshil.rbchacks.models.Product;
import com.vanshil.rbchacks.models.Store;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by vanshilshah on 24/09/16.
 */
public interface FirebaseService {

    @GET("/products.json")
    Call<List<Product>> getProducts();

    @GET("/stores/{storeID}.json")
    Call<Store> getStore(@Path("storeID") int storeID);

    @GET("/keywords/{name}.json")
    Call<List<Integer>> getProductsByKeywords(@Path("name") String name);

    @POST("/keywords/{name}.json")
    Call<Integer> addKeyword(@Path("name") String name, @Body String path);

    @POST("/stores/{storeID}.json")
    Call<Store> getStore(@Path("storeID") int storeID, @Body Store store);

    @POST("/products.json")
    Call<Product> postProduct(@Body Product product);

    @POST("/queries.json")
    Call<List<Integer>> postQuery(@Body Integer count);
}
