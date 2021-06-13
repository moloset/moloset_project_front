package com.example.clothesvillage.remote.request;

public class ReplyInsertRequest {
    private int style_no;
    private int user_no;
    private String reply_content;

    public ReplyInsertRequest(int style_no, int user_no, String reply_content) {
        this.style_no = style_no;
        this.user_no = user_no;
        this.reply_content = reply_content;
    }

    @Override
    public String toString() {
        return "ReplyInsertRequest{" +
                "style_no=" + style_no +
                ", user_no=" + user_no +
                ", reply_content=" + reply_content +
                '}';
    }
}
