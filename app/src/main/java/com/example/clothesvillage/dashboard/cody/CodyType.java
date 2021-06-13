package com.example.clothesvillage.dashboard.cody;

public class CodyType {
    private String name;
    private String categoryType;
    private boolean isSelected;

    public CodyType(String name, String categoryType, boolean isSelected) {
        this.name = name;
        this.categoryType = categoryType;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "CodyType{" +
                "name='" + name + '\'' +
                ", categoryType='" + categoryType + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }
}
