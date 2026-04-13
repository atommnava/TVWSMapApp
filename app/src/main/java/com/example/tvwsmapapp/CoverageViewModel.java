package com.example.tvwsmapapp;

import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoverageViewModel extends ViewModel {

    private CoverageRepository repository;

    public CoverageViewModel() {
        repository = new CoverageRepository();
    }

    public void fetchCoverage(double lat, double lon) {

        repository.getCoverage(lat, lon).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    System.out.println("Respuesta: " + response.body());
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                System.out.println("Error: " + t.getMessage());
            }
        });
    }
}
