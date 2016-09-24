package com.vanshil.rbchacks;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vanshil.rbchacks.common.BaseActivity;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    MapFragment mapFragment;
    GoogleMap map;
    LatLng latlng;
    Marker myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TAG = "MainActivity";
        map = null;
        mapFragment = new MapFragment();
        mapFragment.getMapAsync(this);
        final Activity context = this;
        locationListener = new LocationManager.Listener() {
            @Override
            public void onLocationChanged(Location location) {
                latlng = new LatLng(location.getLatitude(), location.getLongitude());
                initializeIfReady();
            }
        };
    }

    private void initializeIfReady(){
        if(map != null && latlng != null){
            myLocation = map.addMarker(new MarkerOptions().position(latlng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_icon_yellow_small_border)));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setMapToolbarEnabled(false);
        map.setOnMarkerClickListener(this);
        initializeIfReady();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if(marker.getSnippet() != null){
            //SelectedActivity.start(this, businesses.get(Integer.parseInt(marker.getSnippet())));
        }
        return false;
    }

}
