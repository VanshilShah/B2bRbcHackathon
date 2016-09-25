package com.vanshil.rbchacks.controllers;

import com.google.android.gms.maps.model.LatLng;
import com.vanshil.rbchacks.models.Store;
import com.vanshil.rbchacks.network.FirebaseService;
import com.vanshil.rbchacks.network.LoggingInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by vanshilshah on 24/09/16.
 */
public class FirebaseManager {
    private static FirebaseManager instance;

    private List<Listener> listeners;

    FirebaseService firebaseService;
    private List<Store> businesses;

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
                .baseUrl("https://rbchacks.firebaseio.com/rbchacks")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(FirebaseService.class);


    }

    public List<Store> getBusinesses(){
        if(businesses == null){
            businesses = new ArrayList<>();
            loadBusinesses();
        }
        return businesses;
    }

    public void loadBusinesses(){
        /*Callback<BusinessResponse> businessCallback = new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> businessResponse) {
                for(BusinessResponse.BusinessResult businessResult: businessResponse.body().getResult()){
                    businessResult.getLatLng();
                }
                notifyBusinessLoaded(businessResponse.body().getResult());
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {

            }
        };
        Call<BusinessResponse> zeusCall = zeusService.getBusinesses();
        zeusCall.enqueue(businessCallback);*/
    }

    public void postLocation(String logID, LatLng latlng){

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
    private void notifyStoresLoaded(List<Store> businesses){
        for(Listener listener: listeners){
            listener.notifyBusinessLoaded(businesses);
        }
    }
    public interface Listener{
        void notifyBusinessLoaded(List<Store> businesses);
    }
}
