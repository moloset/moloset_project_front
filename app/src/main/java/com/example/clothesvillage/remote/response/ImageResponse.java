package com.example.clothesvillage.remote.response;

public class ImageResponse {
    private String path;

    public ImageResponse() {
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "ImageResponse{" +
                "path='" + path + '\'' +
                '}';
    }
}
