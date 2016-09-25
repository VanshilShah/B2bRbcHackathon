package com.vanshil.rbchacks.controllers;

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

    public void getStore(int id){
        loadStore(id);
    }

    public void loadStore(int id){
        Callback<Store> storeCallback = new Callback<Store>() {
            @Override
            public void onResponse(Call<Store> call, Response<Store> businessResponse) {

                notifyStoreLoaded(businessResponse.body());
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
    private void notifyStoreLoaded(Store businesses){
        for(Listener listener: listeners){
            listener.notifyStoreLoaded(businesses);
        }
    }
    public interface Listener{
        void notifyStoreLoaded(Store store);
    }
}
