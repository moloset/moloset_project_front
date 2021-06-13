package com.example.clothesvillage.remote.request;

public class StyleInsertRequest {
    private String style_name;
    private String style_content;
    private String hash_tag;
    private String clothes_no;


    public StyleInsertRequest() {
    }

    public StyleInsertRequest(String style_name, String style_content, String hash_tag, String clothes_no) {
        this.style_name = style_name;
        this.style_content = style_content;
        this.hash_tag = hash_tag;
        this.clothes_no = clothes_no;
    }

    public String getStyle_name() {
        return style_name;
    }

    public void setStyle_name(String style_name) {
        this.style_name = style_name;
    }

    public String getStyle_content() {
        return style_content;
    }

    public void setStyle_content(String style_content) {
        this.style_content = style_content;
    }

    public String getHash_tag() {
        return hash_tag;
    }

    public void setHash_tag(String hash_tag) {
        this.hash_tag = hash_tag;
    }

    public String getClothes_no() {
        return clothes_no;
    }

    public void setClothes_no(String clothes_no) {
        this.clothes_no = clothes_no;
    }

    @Override
    public String toString() {
        return "StyleInsertRequest{" +
                "style_name='" + style_name + '\'' +
                ", style_content='" + style_content + '\'' +
                ", hash_tag='" + hash_tag + '\'' +
                ", clothes_no='" + clothes_no + '\'' +
                '}';
    }
}
