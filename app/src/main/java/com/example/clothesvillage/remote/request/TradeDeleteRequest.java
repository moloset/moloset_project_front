package com.example.clothesvillage.remote.request;

public class TradeDeleteRequest {
    private int trade_no;

    public TradeDeleteRequest() {
    }

    public TradeDeleteRequest(int trade_no) {
        this.trade_no = trade_no;
    }

    @Override
    public String toString() {
        return "TradeDetailRequest{" +
                "trade_no=" + trade_no +
                '}';
    }
}
