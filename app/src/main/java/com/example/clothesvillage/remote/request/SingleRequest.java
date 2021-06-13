package com.example.clothesvillage.remote.request;

public class SingleRequest {
    private String user_email;

    public SingleRequest(String email) {
        this.user_email = email;
    }

    public String getEmail() {
        return user_email;
    }
}
