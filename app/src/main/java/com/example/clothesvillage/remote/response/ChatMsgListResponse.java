package com.example.clothesvillage.remote.response;

public class ChatMsgListResponse {
    private int user_no;
    private String content;
    private String send_dt;

    public int getUser_no() {
        return user_no;
    }

    public String getContent() {
        return content;
    }

    public String getSend_dt() {
        return send_dt;
    }

    @Override
    public String toString() {
        return "ChatMsgListResponse{" +
                "user_no=" + user_no +
                ", content='" + content + '\'' +
                ", send_dt='" + send_dt + '\'' +
                '}';
    }
}
