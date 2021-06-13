package com.example.clothesvillage.remote.response;

public class ReplyListResponse {
    private int reply_no;
    private String user_profile;
    private int user_no;
    private String user_name;
    private String reply_content;

    public int getReply_no() {
        return reply_no;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public int getUser_no() {
        return user_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getReply_content() {
        return reply_content;
    }

    @Override
    public String toString() {
        return "ReplyListResponse{" +
                "reply_no=" + reply_no +
                ", user_profile='" + user_profile + '\'' +
                ", user_no=" + user_no +
                ", user_name='" + user_name + '\'' +
                ", reply_content='" + reply_content + '\'' +
                '}';
    }
}
