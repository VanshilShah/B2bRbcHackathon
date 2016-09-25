package com.vanshil.rbchacks.controllers;

import com.vanshil.rbchacks.models.Product;
import com.vanshil.rbchacks.models.Store;
import com.vanshil.rbchacks.network.FirebaseService;
import com.vanshil.rbchacks.network.LoggingInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vanshilshah on 24/09/16.
 */
public class FirebaseManager {
    private static FirebaseManager instance;

    private List<Listener> listeners;

    FirebaseService firebaseService;
    private List<Store> store;

    public static FirebaseManager getInstance(){
        if(instance == null){
            instance = new FirebaseManager();
        }
        return instance;
    }

    private FirebaseManager(){
        listeners = new ArrayList<>();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).build();

        firebaseService = new Retrofit.Builder()
                .baseUrl("https://rbchacks.firebaseio.com/rbchacks/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(FirebaseService.class);


    }

    public void getProducts(String search){
        //oString[] keywords = search.split(" ");
        final List<Integer> productsFound = new ArrayList<>();
        Callback<List<Integer>> keywordCallback = new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                if(response.body()!=null){
                    productsFound.addAll(response.body());
                }
                Call productCall = firebaseService.getProducts();
                productCall.enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        List<Product> result = response.body();
                        List<Product> toReturn = new ArrayList<Product>();
                        for(Integer productIndex : productsFound){
                            toReturn.add(result.get(productIndex));
                        }
                        listeners.get(0).notifyProductsFound(toReturn);

                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });

            }

            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {

            }
        };
        Call call = firebaseService.getProductsByKeywords(search);
        call.enqueue(keywordCallback);

    }

    public void getStore(int id){
        loadStore(id);
    }

    public void loadStore(int id){
        Callback<Store> storeCallback = new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> businessResponse) {

                notifyStoresLoaded(businessResponse.body());
            }

            @Override
            public void onFailure(Call<Store> call, Throwable t) {

            }
        };
        Call<Store> storeCall = firebaseService.getStore(id);
        storeCall.enqueue(storeCallback);
    }



    public void unregister(Listener listener){
        if(listeners.indexOf(listener) != -1){
            listeners.remove(listener);
        }
    }
    public void register(Listener listener){
        if(listeners.indexOf(listener) == -1){
            listeners.add(listener);
        }
    }
    private void notifyStoresLoaded(Store businesses){
        for(Listener listener: listeners){
            listener.notifyStoreLoaded(businesses);
        }
    }
    public interface Listener{
        void notifyStoreLoaded(Store store);
        void notifyProductsFound(List<Product> products);
    }
}
