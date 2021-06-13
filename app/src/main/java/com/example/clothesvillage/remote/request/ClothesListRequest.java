package com.example.clothesvillage.remote.request;

public class ClothesListRequest {
    private int owner_no;
    private String clothes_category;

    public ClothesListRequest(int owner_no, String clothes_category) {
        this.owner_no = owner_no;
        this.clothes_category = clothes_category;
    }

    public int getOwner_no() {
        return owner_no;
    }

    public String getClothes_category() {
        return clothes_category;
    }

    @Override
    public String toString() {
        return "ClothesListRequest{" +
                "owner_no=" + owner_no +
                ", clothes_category='" + clothes_category + '\'' +
                '}';
    }
}
