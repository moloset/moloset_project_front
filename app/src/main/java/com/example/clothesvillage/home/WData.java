package com.example.clothesvillage.home;

public class WData {
    //news data를 위한 class
    private String picture; //id : textView_title
    private String content; //id : textView_content (url)
    private String temperature;
    private String high_low_temp;

    public WData(){

    }

    public WData(String picture, String content, String temperature, String high_low_temp){
        this.picture = picture;
        this.content = content;
        this.temperature = temperature;
        this.high_low_temp = high_low_temp;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHigh_low_temp() {
        return high_low_temp;
    }

    public void setHigh_Low_temp(String high_low_temp) {
        this.high_low_temp = high_low_temp;
    }
}
