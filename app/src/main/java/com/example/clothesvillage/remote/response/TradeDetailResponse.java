package com.example.clothesvillage.remote.response;

import java.io.Serializable;

public class TradeDetailResponse implements Serializable {
    private int trade_no;
    private String trade_name;
    private String trade_content;
    private String trade_price;
    private String trade_case;
    private String trade_area;
    private String trade_area_name;
    private String photo;
    private String user_no;
    private String user_name;


    public TradeDetailResponse() {
    }

    public int getTrade_no() {
        return trade_no;
    }

    public String getTrade_name() {
        return trade_name;
    }

    public String getTrade_content() {
        return trade_content;
    }

    public String getTrade_price() {
        return trade_price;
    }

    public String getTrade_case() {
        return trade_case;
    }

    public String getTrade_area() {
        return trade_area;
    }

    public String getTrade_area_name() {
        return trade_area_name;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUser_no() {
        return user_no;
    }

    public String getUser_name() {
        return user_name;
    }



    @Override
    public String toString() {
        return "TradeDetailResponse{" +
                "trade_no=" + trade_no +
                ", trade_name='" + trade_name + '\'' +
                ", trade_content='" + trade_content + '\'' +
                ", trade_price='" + trade_price + '\'' +
                ", trade_case='" + trade_case + '\'' +
                ", trade_area='" + trade_area + '\'' +
                ", trade_area_name='" + trade_area_name + '\'' +
                ", photo='" + photo + '\'' +
                ", user_no='" + user_no + '\'' +
                ", user_name='" + user_name + '\'' +
                '}';
    }
}
