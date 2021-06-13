package com.example.clothesvillage.remote.response;

public class ChatRoomResponse {
    private int chat_no;

    public ChatRoomResponse() {
    }

    public int getChat_no() {
        return chat_no;
    }

    @Override
    public String toString() {
        return "ChatRoomResponse{" +
                "chat_no=" + chat_no +
                '}';
    }
}
