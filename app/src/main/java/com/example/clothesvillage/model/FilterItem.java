package com.example.clothesvillage.model;

import java.io.Serializable;

public class FilterItem implements Serializable {
    private boolean category_1 = true;
    private boolean category_2 = true;
    private boolean category_3 = true;
    private boolean category_4 = true;
    private boolean category_5 = true;
    private boolean category_6 = true;
    private String height = "";
    private boolean heightEnable= true;
    private String weight = "";
    private boolean weightEnable= true;

    public FilterItem() {
    }

    public void setCategory_1(boolean category_1) {
        this.category_1 = category_1;
    }

    public void setCategory_2(boolean category_2) {
        this.category_2 = category_2;
    }

    public void setCategory_3(boolean category_3) {
        this.category_3 = category_3;
    }

    public void setCategory_4(boolean category_4) {
        this.category_4 = category_4;
    }

    public void setCategory_5(boolean category_5) {
        this.category_5 = category_5;
    }

    public void setCategory_6(boolean category_6) {
        this.category_6 = category_6;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setHeightEnable(boolean heightEnable) {
        this.heightEnable = heightEnable;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setWeightEnable(boolean weightEnable) {
        this.weightEnable = weightEnable;
    }

    public boolean isCategory_1() {
        return category_1;
    }

    public boolean isCategory_2() {
        return category_2;
    }

    public boolean isCategory_3() {
        return category_3;
    }

    public boolean isCategory_4() {
        return category_4;
    }

    public boolean isCategory_5() {
        return category_5;
    }

    public boolean isCategory_6() {
        return category_6;
    }

    public String getHeight() {
        return height;
    }

    public boolean isHeightEnable() {
        return heightEnable;
    }

    public String getWeight() {
        return weight;
    }

    public boolean isWeightEnable() {
        return weightEnable;
    }

    @Override
    public String toString() {
        return "FilterItem{" +
                "category_1=" + category_1 +
                ", category_2=" + category_2 +
                ", category_3=" + category_3 +
                ", category_4=" + category_4 +
                ", category_5=" + category_5 +
                ", category_6=" + category_6 +
                ", height='" + height + '\'' +
                ", heightEnable=" + heightEnable +
                ", weight='" + weight + '\'' +
                ", weightEnable=" + weightEnable +
                '}';
    }
}
