package com.example.clothesvillage.remote.response;

public class StyleListResponse {
    private String user_profile;
    private String user_name;
    private int style_no;
    private String style_name;
    private String style_content;
    private String photo;
    private String goodCheck;


    public StyleListResponse() {
    }

    public String getUser_name() {
        return user_name;
    }

    public int getStyle_no() {
        return style_no;
    }

    public String getStyle_name() {
        return style_name;
    }

    public String getStyle_content() {
        return style_content;
    }

    public String getPhoto() {
        return photo;
    }

    public String getGoodCheck() {
        return goodCheck;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setGoodCheck(String goodCheck) {
        this.goodCheck = goodCheck;
    }

    @Override
    public String toString() {
        return "StyleListResponse{" +
                "user_profile='" + user_profile + '\'' +
                ", user_name='" + user_name + '\'' +
                ", style_no=" + style_no +
                ", style_name='" + style_name + '\'' +
                ", style_content='" + style_content + '\'' +
                ", photo='" + photo + '\'' +
                ", goodCheck='" + goodCheck + '\'' +
                '}';
    }
}
