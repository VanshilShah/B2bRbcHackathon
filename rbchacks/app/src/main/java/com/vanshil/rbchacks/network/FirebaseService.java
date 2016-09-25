package com.vanshil.rbchacks.network;

import com.vanshil.rbchacks.models.Store;
import com.vanshil.rbchacks.models.Product;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by vanshilshah on 24/09/16.
 */
public interface FirebaseService {

    @GET("/stores/{storeID}/{productID}")
    Call<Product> getProduct();

    @GET("/stores/{storeID}")
    Call<Store> getStore();
}
