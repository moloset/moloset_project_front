package com.example.clothesvillage.remote.request;

public class TradeDetailRequest {
    private int trade_no;

    public TradeDetailRequest() {
    }

    public TradeDetailRequest(int trade_no) {
        this.trade_no = trade_no;
    }

    @Override
    public String toString() {
        return "TradeDetailRequest{" +
                "trade_no=" + trade_no +
                '}';
    }
}
