package com.example.clothesvillage;

public class User {
    private String user_name;
    private int user_birth_year;
    private int user_birth_month;
    private int user_birth_day;
    private String user_gender;

    public User(){

    }

    public User(String user_name, int user_birth_year, int user_birth_month, int user_birth_day, String user_gender){
        this.user_name = user_name;
        this.user_birth_year = user_birth_year;
        this.user_birth_month = user_birth_month;
        this.user_birth_day = user_birth_day;
        this.user_gender = user_gender;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public int getUser_birth_year() {
        return user_birth_year;
    }

    public void setUser_birth_year(int user_birth_year) {
        this.user_birth_year = user_birth_year;
    }

    public int getUser_birth_month() {
        return user_birth_month;
    }

    public void setUser_birth_month(int user_birth_month) {
        this.user_birth_month = user_birth_month;
    }

    public int getUser_birth_day() {
        return user_birth_day;
    }

    public void setUser_birth_day(int user_birth_day) {
        this.user_birth_day = user_birth_day;
    }
}
