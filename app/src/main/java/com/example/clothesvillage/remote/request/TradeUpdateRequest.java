package com.example.clothesvillage.remote.request;

public class TradeUpdateRequest {
    private int trade_no;
    private String trade_case;

    public TradeUpdateRequest(int trade_no, String trade_case) {
        this.trade_no = trade_no;
        this.trade_case = trade_case;
    }

    @Override
    public String toString() {
        return "TradeUpdateRequest{" +
                "trade_no=" + trade_no +
                ", trade_case='" + trade_case + '\'' +
                '}';
    }
}
