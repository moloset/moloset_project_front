package com.example.clothesvillage.remote.response;

public class UserSelectListResponse {
    private int style_no;
    private String style_name;
    private String style_content;
    private String photo;
    private String user_profile;
    private String user_name;
    private String goodCheck;


    public UserSelectListResponse() {
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

    public String getUser_profile() {
        return user_profile;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getGoodCheck() {
        return goodCheck;
    }

    @Override
    public String toString() {
        return "UserSelectListResponse{" +
                "style_no=" + style_no +
                ", style_name='" + style_name + '\'' +
                ", style_content='" + style_content + '\'' +
                ", photo='" + photo + '\'' +
                ", user_profile='" + user_profile + '\'' +
                ", user_name='" + user_name + '\'' +
                ", goodCheck='" + goodCheck + '\'' +
                '}';
    }

}
