package com.example.clothesvillage.remote.request;

public class ReplyListRequest {
    private int style_no;


    public ReplyListRequest(int style_no) {
        this.style_no = style_no;
    }

    @Override
    public String toString() {
        return "StyleDetailRequest{" +
                "style_no=" + style_no +
                '}';
    }
}
