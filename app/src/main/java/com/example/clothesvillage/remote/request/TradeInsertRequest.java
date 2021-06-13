package com.example.clothesvillage.remote.request;

public class TradeInsertRequest {
    private String clothes_no;
    private String clothes_category;
    private String trade_name;
    private String trade_content;
    private String trade_price;
    private String trade_case;
    private String trade_area_name;
    private String trade_area;


    public TradeInsertRequest() {
    }

    public TradeInsertRequest(String clothes_no, String clothes_category, String trade_name, String trade_content, String trade_price, String trade_case, String trade_area_name, String trade_area) {
        this.clothes_no = clothes_no;
        this.clothes_category = clothes_category;
        this.trade_name = trade_name;
        this.trade_content = trade_content;
        this.trade_price = trade_price;
        this.trade_case = trade_case;
        this.trade_area_name = trade_area_name;
        this.trade_area = trade_area;
    }

    public String getClothes_no() {
        return clothes_no;
    }

    public String getClothes_category() {
        return clothes_category;
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

    public String getTrade_area_name() {
        return trade_area_name;
    }

    public String getTrade_area() {
        return trade_area;
    }

    @Override
    public String toString() {
        return "TradeInsertRequest{" +
                "clothes_no='" + clothes_no + '\'' +
                ", clothes_category='" + clothes_category + '\'' +
                ", trade_name='" + trade_name + '\'' +
                ", trade_content='" + trade_content + '\'' +
                ", trade_price='" + trade_price + '\'' +
                ", trade_case='" + trade_case + '\'' +
                ", trade_area_name='" + trade_area_name + '\'' +
                ", trade_area='" + trade_area + '\'' +
                '}';
    }
}
