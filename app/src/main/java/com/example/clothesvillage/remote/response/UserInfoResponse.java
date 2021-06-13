package com.example.clothesvillage.remote.response;

public class UserInfoResponse {
    private String user_no;
    private String user_email;
    private String user_pwd;
    private String user_name;
    private String user_gender;
    private String user_birth;
    private String user_height;
    private String user_weight;
    private String user_location;
    private String user_profile;

    public UserInfoResponse() {
    }

    public String getUser_no() {
        return user_no;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public String getUser_height() {
        return user_height;
    }

    public String getUser_weight() {
        return user_weight;
    }

    public String getUser_location() {
        return user_location;
    }

    public String getUser_profile() {
        return user_profile;
    }

    @Override
    public String toString() {
        return "UserInfoResponse{" +
                "user_no='" + user_no + '\'' +
                ", user_email='" + user_email + '\'' +
                ", user_pwd='" + user_pwd + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_gender='" + user_gender + '\'' +
                ", user_birth='" + user_birth + '\'' +
                ", user_height='" + user_height + '\'' +
                ", user_weight='" + user_weight + '\'' +
                ", user_location='" + user_location + '\'' +
                ", user_profile='" + user_profile + '\'' +
                '}';
    }
}
