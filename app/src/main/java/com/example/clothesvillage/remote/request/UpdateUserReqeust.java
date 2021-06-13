package com.example.clothesvillage.remote.request;

public class UpdateUserReqeust {
    private String user_email;
    private String user_pwd;
    private String user_name;
    private String user_gender;
    private String user_birth;
    private String user_height;
    private String user_weight;
    private String user_profile;

    public UpdateUserReqeust() {
    }

    public UpdateUserReqeust(String user_email, String user_pwd, String user_name, String user_gender, String user_birth, String user_height, String user_weight, String user_profile) {
        this.user_email = user_email;
        this.user_pwd = user_pwd;
        this.user_name = user_name;
        this.user_gender = user_gender;
        this.user_birth = user_birth;
        this.user_height = user_height;
        this.user_weight = user_weight;
        this.user_profile = user_profile;
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

    public String getUser_profile() {
        return user_profile;
    }
}
