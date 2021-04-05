package com.example.infs3605;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Focus map on your own location
            // Enabling location services within the emulator would have taken too much time so,
            // for hte time being, I recommend just entering your current address into this website
            // https://www.gps-coordinates.net/ and entering the exact numbers into the brackets
            // in the LatLng(v, v1) code below. Latitude in v: and Longitude in v1:
            // this method is tested and it works well.

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-33.79086685180664, 151.13381958007812);
        mMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Tristan's Red Cross Hospital")
                .snippet("Available - Tuna, Blankets & Crackers"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        // Add a 2nd marker in Sydney and move the camera
        LatLng sydneyTwo = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions()
                .position(sydneyTwo)
                .title("Sydney Red Cross Hospital")
                .snippet("Tuna cans available"));
        // [START_EXCLUDE silent]
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }
}