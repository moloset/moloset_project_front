package com.example.clothesvillage.remote.response;

public class TradeListResponse {
    private int trade_no;
    private String trade_name;
    private String trade_content;
    private String trade_price;
    private String trade_case;
    private String photo;


    public TradeListResponse() {
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

    public String getPhoto() {
        return photo;
    }

    @Override
    public String toString() {
        return "TradeListResponse{" +
                "trade_no=" + trade_no +
                ", trade_name='" + trade_name + '\'' +
                ", trade_content='" + trade_content + '\'' +
                ", trade_price='" + trade_price + '\'' +
                ", trade_case='" + trade_case + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
