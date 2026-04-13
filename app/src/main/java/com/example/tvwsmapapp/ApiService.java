package com.example.tvwsmapapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/")
    Call<CoverageResponse> getCoverage(@Body CoverageRequest request);
}