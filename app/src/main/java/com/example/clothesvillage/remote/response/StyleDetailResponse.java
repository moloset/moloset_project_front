package com.example.clothesvillage.remote.response;

public class StyleDetailResponse {
    private int style_no;
    private String style_name;
    private String style_content;
    private String goodCheck;
    private int user_no;
    private String user_name;
    private String user_profile;
    private String[] hash_tag;

    public int getStyle_no() {
        return style_no;
    }

    public String getStyle_name() {
        return style_name;
    }

    public String getStyle_content() {
        return style_content;
    }

    public String getGoodCheck() {
        return goodCheck;
    }

    public int getUser_no() {
        return user_no;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public String[] getHash_tag() {
        return hash_tag;
    }

    @Override
    public String toString() {
        return "StyleDetailResponse{" +
                "style_no=" + style_no +
                ", style_name='" + style_name + '\'' +
                ", style_content='" + style_content + '\'' +
                ", goodCheck='" + goodCheck + '\'' +
                ", user_no=" + user_no +
                ", user_name='" + user_name + '\'' +
                ", user_profile='" + user_profile + '\'' +
                ", hash_tag='" + hash_tag + '\'' +
                '}';
    }
}
