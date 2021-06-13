package com.example.clothesvillage.remote.request;

public class ClothesUpdateRequest {
    private int clothes_no;
    private String clothes_category;
    private String clothes_name;
    private String clothes_memo;
    private String photo;
    private String owner_no;

    public ClothesUpdateRequest() {
    }

    public ClothesUpdateRequest(int clothes_no, String clothes_category, String clothes_name, String clothes_memo, String photo, String owner_no) {
        this.clothes_no = clothes_no;
        this.clothes_category = clothes_category;
        this.clothes_name = clothes_name;
        this.clothes_memo = clothes_memo;
        this.photo = photo;
        this.owner_no = owner_no;
    }

    public int getClothes_no() {
        return clothes_no;
    }

    public void setClothes_no(int clothes_no) {
        this.clothes_no = clothes_no;
    }

    public String getClothes_category() {
        return clothes_category;
    }

    public void setClothes_category(String clothes_category) {
        this.clothes_category = clothes_category;
    }

    public String getClothes_name() {
        return clothes_name;
    }

    public void setClothes_name(String clothes_name) {
        this.clothes_name = clothes_name;
    }

    public String getClothes_memo() {
        return clothes_memo;
    }

    public void setClothes_memo(String clothes_memo) {
        this.clothes_memo = clothes_memo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getOwner_no() {
        return owner_no;
    }

    public void setOwner_no(String owner_no) {
        this.owner_no = owner_no;
    }

    @Override
    public String toString() {
        return "ClothesUpdateRequest{" +
                "clothes_no=" + clothes_no +
                ", clothes_category='" + clothes_category + '\'' +
                ", clothes_name='" + clothes_name + '\'' +
                ", clothes_memo='" + clothes_memo + '\'' +
                ", photo='" + photo + '\'' +
                ", owner_no='" + owner_no + '\'' +
                '}';
    }
}
