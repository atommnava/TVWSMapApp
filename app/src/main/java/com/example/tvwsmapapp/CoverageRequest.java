package com.example.tvwsmapapp;

public class CoverageRequest {
    private double lat;
    private double lon;

    public CoverageRequest(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() { return lat; }
    public double getLon() { return lon; }
}