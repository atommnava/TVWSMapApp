package com.example.tvwsmapapp;

import java.util.List;

public class CoverageResponse {
    public List<List<Point>> tvws;

    public static class Point {
        public double lat;
        public double lon;
    }
}
