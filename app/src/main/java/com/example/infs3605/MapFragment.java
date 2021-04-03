package com.example.infs3605;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(final GoogleMap googleMap) {
                LatLng sydney = new LatLng(-33.79086685180664, 151.13381958007812);
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng sydney) {
                        sydney = new LatLng(-33.79086685180664, 151.13381958007812);
                        MarkerOptions markerOptions= new MarkerOptions();
                        markerOptions.position(sydney);
                        markerOptions.title(sydney.latitude + " : " + sydney.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10));
                        googleMap.addMarker(new MarkerOptions().position(sydney).title("Tristan's Red Cross Hospital"));
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                    }
                });
            }
        });
        return view;
    }
}

