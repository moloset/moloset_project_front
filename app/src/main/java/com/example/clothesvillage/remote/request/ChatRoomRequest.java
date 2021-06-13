package com.example.clothesvillage.remote.request;

public class ChatRoomRequest {
    private int trade_no;
    private int user_no1;
    private int user_no2;

    public ChatRoomRequest(int trade_no, int user_no1, int user_no2) {
        this.trade_no = trade_no;
        this.user_no1 = user_no1;
        this.user_no2 = user_no2;
    }
}
