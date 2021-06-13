package com.example.clothesvillage.remote.request;

public class StyleUserSelectRequest {
    private int user_no;

    public StyleUserSelectRequest(int user_no) {
        this.user_no = user_no;
    }

    @Override
    public String toString() {
        return "StyleUserSelectRequest{" +
                "user_no=" + user_no +
                '}';
    }
}
