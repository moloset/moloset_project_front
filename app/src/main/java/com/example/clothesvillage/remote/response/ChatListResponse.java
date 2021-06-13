package com.example.clothesvillage.remote.response;

public class ChatListResponse {
    private String photo;
    private String user_name;
    private String trade_case;
    private String last_msg;
    private int chat_no;

    public String getPhoto() {
        return photo;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getTrade_case() {
        return trade_case;
    }

    public String getLast_msg() {
        return last_msg;
    }

    public int getChat_no() {
        return chat_no;
    }

    @Override
    public String toString() {
        return "ChatListResponse{" +
                "photo='" + photo + '\'' +
                ", user_name='" + user_name + '\'' +
                ", trade_case='" + trade_case + '\'' +
                ", last_msg='" + last_msg + '\'' +
                ", chat_no=" + chat_no +
                '}';
    }
}
