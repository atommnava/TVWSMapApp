package com.example.tvwsmapapp;

import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationClient;
    private CoverageRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new CoverageRepository();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        getLocation();
    }

    private void getLocation() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {

                        double lat = location.getLatitude();
                        double lon = location.getLongitude();

                        LatLng user = new LatLng(lat, lon);

                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 12));

                        // 🔥 Llamada al backend
                        fetchCoverage(lat, lon);
                    }
                });
    }

    private void fetchCoverage(double lat, double lon) {

        repository.getCoverage(lat, lon).enqueue(new Callback<CoverageResponse>() {
            @Override
            public void onResponse(Call<CoverageResponse> call,
                                   Response<CoverageResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    drawPolygons(response.body());
                }
            }

            @Override
            public void onFailure(Call<CoverageResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // 🎨 DIBUJAR TVWS
    private void drawPolygons(CoverageResponse data) {

        for (java.util.List<CoverageResponse.Point> polygon : data.tvws) {

            PolygonOptions polygonOptions = new PolygonOptions();

            for (CoverageResponse.Point p : polygon) {
                polygonOptions.add(new LatLng(p.lat, p.lon));
            }

            polygonOptions.strokeWidth(4);
            polygonOptions.strokeColor(0xFF00FF00); // Verde
            polygonOptions.fillColor(0x5500FF00);   // Transparente

            mMap.addPolygon(polygonOptions);
        }
    }
}