package com.example.clothesvillage.remote.request;

public class ChatMessageRequest {
    private int chat_no;
    private int user_no;
    private String content;

    public ChatMessageRequest(int chat_no, int user_no, String content) {
        this.chat_no = chat_no;
        this.user_no = user_no;
        this.content = content;
    }

    public int getChat_no() {
        return chat_no;
    }

    public int getUser_no() {
        return user_no;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ChatMessageRequest{" +
                "chat_no=" + chat_no +
                ", user_no=" + user_no +
                ", content='" + content + '\'' +
                '}';
    }
}
