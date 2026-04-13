package com.example.tvwsmapapp;

import retrofit2.Call;

public class CoverageRepository {

    private ApiService apiService;

    public CoverageRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public Call<Object> getCoverage(double lat, double lon) {
        CoverageRequest request = new CoverageRequest(lat, lon);
        return apiService.getCoverage(request);
    }
}
