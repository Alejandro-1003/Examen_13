package com.example.vj20231;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapviwActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapView mapView;
    double latitud;
    double longitud;
    String marcador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapviw);
        Intent intent = getIntent();
        latitud= intent.getDoubleExtra("lat",0);
        longitud= intent.getDoubleExtra("lon",0);
        marcador= intent.getStringExtra("nombre");

        mapView= findViewById(R.id.mapvista);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        // Create an LatLng object with our data
        LatLng location = new LatLng(
               latitud,longitud
        );

        // Add marker at the location
        googleMap.addMarker(new MarkerOptions()
                .position(location).title(marcador));

        // Move the camera to the location and zoom in
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                location, 15F
        ));
    }

    @Override
    public void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        this.mapView.onLowMemory();
    }
}