package com.vanshil.rbchacks;

import android.app.Activity;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import com.vanshil.rbchacks.controllers.FirebaseManager;
import com.vanshil.rbchacks.dummy.DummyContent;
import com.vanshil.rbchacks.models.Product;
import com.vanshil.rbchacks.models.Store;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, BusinessItemFragment.OnListFragmentInteractionListener,  NavigationDrawerFragment.NavigationDrawerCallbacks, MyProfile.MyProfileFragmentInteractionListener, MapListFragment.OnFragmentInteractionListener{

    SupportMapFragment mapFragment;
    GoogleMap map;
    LatLng latlng;
    Marker myLocation;
    public static List<Product> products = new ArrayList<>();
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)  getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp( R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main3);
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
        firebaseManager.getStore(0);
        firebaseListener = new FirebaseManager.Listener() {
            @Override
            public void notifyStoreLoaded(Store store) {
                Log.d(TAG, store.getName());
            }

            @Override
            public void notifyProductsFound(List<Product> products) {
                for(Product product : products){
                    LatLng latLng = new LatLng(product.getLatitude(), product.getLongitude());
                    myLocation = map.addMarker(new MarkerOptions().position(latlng).title(product.getName()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_icon_blue_small_border)));

                }
            }
        };
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        final Fragment[] fragments = {MapListFragment.newInstance(0), MyProfile.newInstance()};
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragments[position])
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
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

    @Override
    public void onMyProfileFragmentInteraction(Uri uri) {

    }

    @Override
    public void onMapListFragmentInteraction(Uri uri) {

    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MapListFragment extends Fragment {

        NonSwipeableViewPager viewPager;
        EditText searchEdit;
        public static MapListFragment newInstance(int sectionNumber) {
            MapListFragment fragment = new MapListFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public MapListFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maplist, container, false);
            viewPager = (NonSwipeableViewPager)rootView.findViewById(R.id.view_pager);
            searchEdit = (EditText)rootView.findViewById(R.id.search);
            searchEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //FirebaseManager.getInstance().getProducts();
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    FirebaseManager.getInstance().getProducts(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });
            initializeViewPager();
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
        }
        public void initializeViewPager(){
            final Fragment[] fragments = {((MainActivity)getActivity()).mapFragment, new BusinessItemFragment()};
            //NonSwipeableViewPager viewPager = new NonSwipeableViewPager(getActivity());
            viewPager.setAdapter(new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragments[0];
                }

                @Override
                public int getCount() {
                    return 1;
                }

                @Override
                public CharSequence getPageTitle(int position) {
                    return position==0?"Map":"List";
                }

            });
            FirebaseManager.getInstance().register(new FirebaseManager.Listener() {
                @Override
                public void notifyStoreLoaded(Store store) {

                }

                @Override
                public void notifyProductsFound(List<Product> products) {

                }
            });

        }

    }

}
