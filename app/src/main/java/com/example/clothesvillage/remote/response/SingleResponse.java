package com.example.clothesvillage.remote.response;

public class SingleResponse {
    private String result;

    public SingleResponse() {
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "SingleResponse{" +
                "result='" + result + '\'' +
                '}';
    }
}
