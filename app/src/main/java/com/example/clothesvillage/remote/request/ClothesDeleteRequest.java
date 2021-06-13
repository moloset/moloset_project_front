package com.example.clothesvillage.remote.request;

public class ClothesDeleteRequest {
    private int owner_no;
    private int clothes_no;

    public ClothesDeleteRequest(int owner_no, int clothes_no) {
        this.owner_no = owner_no;
        this.clothes_no = clothes_no;
    }
}
