package com.example.clothesvillage.remote.response;

public class HourlyWeatherResponse {
    private String time;
    private String temp;
    private String icon;


    public HourlyWeatherResponse() {
    }

    public String getTime() {
        return time;
    }

    public String getTemp() {
        return temp;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "HourlyWeatherResponse{" +
                "time='" + time + '\'' +
                ", temp='" + temp + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
