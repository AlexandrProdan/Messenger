package com.example.messenger;

public class Message {
    private String messageText;
    private String senderId;
    private String receiverId;

    public Message() {
    }

    public Message(String messageText, String senderId, String receiverId) {
        this.messageText = messageText;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }
}


