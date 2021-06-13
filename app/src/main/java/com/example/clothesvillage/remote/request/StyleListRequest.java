package com.example.clothesvillage.remote.request;

public class StyleListRequest {
    private int user_no;
    private String user_gender;
    private String search_div;
    private String search_text;


    public StyleListRequest(int user_no, String user_gender, String search_div, String search_text) {
        this.user_no = user_no;
        this.user_gender = user_gender;
        this.search_div = search_div;
        this.search_text = search_text;
    }

    public int getUser_no() {
        return user_no;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getSearch_div() {
        return search_div;
    }

    public String getSearch_text() {
        return search_text;
    }

    @Override
    public String toString() {
        return "StyleListRequest{" +
                "user_no=" + user_no +
                ", user_gender='" + user_gender + '\'' +
                ", search_div='" + search_div + '\'' +
                '}';
    }
}
