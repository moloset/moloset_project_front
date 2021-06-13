package com.example.clothesvillage.remote.request;

public class WeatherRequest {
    private String lat;
    private String lon;

    public WeatherRequest(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return "WeatherRequest{" +
                "lat='" + lat + '\'' +
                ", lon='" + lon + '\'' +
                '}';
    }
}
