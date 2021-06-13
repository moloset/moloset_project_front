package com.example.clothesvillage.remote.request;

public class LoginRequest {
    private String user_email;
    private String user_pwd;

    public LoginRequest(String user_email, String user_pwd) {
        this.user_email = user_email;
        this.user_pwd = user_pwd;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_pwd() {
        return user_pwd;
    }
}
