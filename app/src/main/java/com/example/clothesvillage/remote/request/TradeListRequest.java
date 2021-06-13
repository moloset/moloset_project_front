package com.example.clothesvillage.remote.request;

public class TradeListRequest {
    private String clothes_category;
    private String height;
    private String h_check;
    private String weight;
    private String w_check;
    private String user_no;



    public TradeListRequest() {
    }

    public TradeListRequest(String clothes_category, String height, String h_check, String weight, String w_check, String user_no) {
        this.clothes_category = clothes_category;
        this.height = height;
        this.h_check = h_check;
        this.weight = weight;
        this.w_check = w_check;
        this.user_no = user_no;
    }

    @Override
    public String toString() {
        return "TradeListRequest{" +
                "clothes_category='" + clothes_category + '\'' +
                ", height='" + height + '\'' +
                ", h_check='" + h_check + '\'' +
                ", weight='" + weight + '\'' +
                ", w_check='" + w_check + '\'' +
                ", user_no='" + user_no + '\'' +
                '}';
    }
}
