package com.example.clothesvillage.model;

public class ChatItem {
    private String content;
    private int viewType;

    public ChatItem(String content, int viewType) {
        this.content = content;
        this.viewType = viewType;
    }

    public String getContent() {
        return content;
    }

    public int getViewType() {
        return viewType;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "content='" + content + '\'' +
                ", viewType=" + viewType +
                '}';
    }
}
