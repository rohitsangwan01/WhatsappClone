package com.example.whatsappclone;

public class ChatModel {
    String sender;
    String reciever;
    String message;
    int messageType;

    public ChatModel(String sender, String reciever, String message, int messageType) {
        this.sender = sender;
        this.reciever = reciever;
        this.message = message;
       // this.messageType = messageType;
    }

    public ChatModel() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciever() {
        return reciever;
    }

    public void setReciever(String reciever) {
        this.reciever = reciever;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public int getMessageType() {
//        return messageType;
//    }
//
//    public void setMessageType(int messageType) {
//        this.messageType = messageType;
//    }
}
