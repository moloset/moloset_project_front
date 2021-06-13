package com.example.clothesvillage.remote.response;

public class ClothesDetailResponse {
    private int clothes_no;
    private String clothes_category;
    private String clothes_name;
    private String clothes_memo;
    private String photo;
    private int owner_no;

    public ClothesDetailResponse() {
    }

    public int getClothes_no() {
        return clothes_no;
    }

    public String getClothes_category() {
        return clothes_category;
    }

    public String getClothes_name() {
        return clothes_name;
    }

    public String getClothes_memo() {
        return clothes_memo;
    }

    public String getPhoto() {
        return photo;
    }

    public int getOwner_no() {
        return owner_no;
    }
}
