package com.vanshil.rbchacks.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.vanshil.rbchacks.LocationManager;
import com.vanshil.rbchacks.controllers.FirebaseManager;

/**
 * Created by vanshilshah on 24/09/16.
 */
public class BaseActivity extends AppCompatActivity {
    public static String TAG = "BaseActivity";

    protected LocationManager.Listener locationListener;
    LocationManager locationManager;

    protected FirebaseManager.Listener firebaseListener;
    protected FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = locationManager.getInstance(this);
        firebaseManager = firebaseManager.getInstance();
    }

    @Override
    public void onResume(){
        super.onResume();
        locationManager.onResume();
        if(locationListener != null){
            locationManager.register(locationListener);
        }
        if(firebaseListener != null){
            firebaseManager.register(firebaseListener);
        }


    }

    @Override
    public void onPause(){
        LocationManager.getInstance(this).onPause();
        if(locationListener != null){
            locationManager.unregister(locationListener);
        }
        if(firebaseListener != null){
            firebaseManager.unregister(firebaseListener);
        }
        super.onPause();
    }
}
