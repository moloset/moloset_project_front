package com.example.clothesvillage.remote.response;

public class CurrentWeatherResponse {
    private String temp;
    private String maxTemp;
    private String minTemp;
    private String icon;


    public CurrentWeatherResponse() {
    }

    public String getTemp() {
        return temp;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "CurrentWeatherResponse{" +
                "temp='" + temp + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
