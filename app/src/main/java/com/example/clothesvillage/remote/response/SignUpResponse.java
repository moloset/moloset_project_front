package com.example.clothesvillage.remote.response;

public class SignUpResponse {
    private String user_no;

    public SignUpResponse() {
    }

    public String getUser_no() {
        return user_no;
    }

    @Override
    public String toString() {
        return "SignUpResponse{" +
                "user_no='" + user_no + '\'' +
                '}';
    }
}
