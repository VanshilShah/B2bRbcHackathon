package com.vanshil.rbchacks;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vanshil.rbchacks.common.BaseActivity;
import com.vanshil.rbchacks.common.NonSwipeableViewPager;
import com.vanshil.rbchacks.dummy.DummyContent;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, BusinessItemFragment.OnListFragmentInteractionListener {

    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng latlng;
    Marker myLocation;

    @BindView(R.id.view_pager)
    NonSwipeableViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        TAG = "MainActivity";
        map = null;
        mapFragment = new SupportMapFragment();
        mapFragment.getMapAsync(this);
        final Activity context = this;
        locationListener = new LocationManager.Listener() {
            @Override
            public void onLocationChanged(Location location) {
                latlng = new LatLng(location.getLatitude(), location.getLongitude());
                initializeIfReady();
            }
        };
        initializeViewPager();
    }
    private void initializeViewPager(){
        final Fragment[] fragments = {mapFragment, new BusinessItemFragment()};

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return 2;
            }

            // Returns the page title for the top indicator
            @Override
            public CharSequence getPageTitle(int position) {
                return position==0?"Map":"List";
            }

        });

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

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
