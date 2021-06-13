package com.example.clothesvillage.remote.request;

public class StyleSelectRequest {
    private int style_no;
    private String user_no;
    private String goodCheck;


    public StyleSelectRequest() {
    }

    public StyleSelectRequest(int style_no, String user_no, String goodCheck) {
        this.style_no = style_no;
        this.user_no = user_no;
        this.goodCheck = goodCheck;
    }

    public int getStyle_no() {
        return style_no;
    }

    public String getUser_no() {
        return user_no;
    }

    public String getGoodCheck() {
        return goodCheck;
    }

    @Override
    public String toString() {
        return "styleSelectRequest{" +
                "style_no='" + style_no + '\'' +
                ", user_no='" + user_no + '\'' +
                ", goodCheck='" + goodCheck + '\'' +
                '}';
    }
}
