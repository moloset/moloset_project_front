package com.example.clothesvillage.remote.request;

public class ClothesDetailRequest {
    private int owner_no;
    private int clothes_no;

    public ClothesDetailRequest(int owner_no, int clothes_no) {
        this.owner_no = owner_no;
        this.clothes_no = clothes_no;
    }
}
