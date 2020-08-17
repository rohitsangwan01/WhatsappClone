package com.example.whatsappclone;

import java.util.Date;

public class MessageModel {

    public String message;
    public int messageType;
    public Date messageTime = new Date();
    public MessageModel(String message, int messageType) {
        this.message = message;
        this.messageType = messageType;
    }
}