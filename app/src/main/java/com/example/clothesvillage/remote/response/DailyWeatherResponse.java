package com.example.clothesvillage.remote.response;

public class DailyWeatherResponse {
    private String day;
    private String maxTemp;
    private String minTemp;
    private String icon;


    public DailyWeatherResponse() {
    }

    public String getDay() {
        return day;
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
        return "DailyWeatherResponse{" +
                "day='" + day + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
