package com.example.clothesvillage.remote.request;

public class StylePhotoListRequest {
    private int style_no;


    public StylePhotoListRequest(int style_no) {
        this.style_no = style_no;
    }

    @Override
    public String toString() {
        return "StyleDetailRequest{" +
                "style_no=" + style_no +
                '}';
    }
}
