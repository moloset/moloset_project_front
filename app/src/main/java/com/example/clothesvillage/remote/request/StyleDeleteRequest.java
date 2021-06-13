package com.example.clothesvillage.remote.request;

public class StyleDeleteRequest {
    private int style_no;

    public StyleDeleteRequest(int style_no) {
        this.style_no = style_no;
    }

    @Override
    public String toString() {
        return "StyleDeleteRequest{" +
                "style_no=" + style_no +
                '}';
    }
}
