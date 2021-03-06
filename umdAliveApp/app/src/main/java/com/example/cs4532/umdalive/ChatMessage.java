package com.example.cs4532.umdalive;

import android.widget.ImageView;

import java.util.Date;

public class ChatMessage {
    private String messageText;
    private String messageUser;
    private long messageTime;
    private String messageImage;

    public ChatMessage(String messageText, String messageUser, String  messageImage) {

        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageImage = messageImage;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage(){
    }

    public ChatMessage(String toString, String name, String profileUrl, int count) {
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

    public String getMessageImage() {
        return messageImage;
    }

    public void setMessageImage(String messageImage) {
        this.messageImage = messageImage;
    }

}