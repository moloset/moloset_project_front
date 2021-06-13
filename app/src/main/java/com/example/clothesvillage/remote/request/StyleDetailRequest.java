package com.example.clothesvillage.remote.request;

public class StyleDetailRequest {
    private int style_no;
    private int user_no;


    public StyleDetailRequest(int style_no, int user_no) {
        this.style_no = style_no;
        this.user_no = user_no;
    }

    @Override
    public String toString() {
        return "StyleDetailRequest{" +
                "style_no=" + style_no +
                ", user_no=" + user_no +
                '}';
    }
}
