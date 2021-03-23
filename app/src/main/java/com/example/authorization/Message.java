package com.example.authorization;

import java.util.Date;

public class Message {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private String authorAvatar;

    public Message(String messageText, String messageUser) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.authorAvatar = authorAvatar;

        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public Message(){

    }

    public String getAuthorAvatar() {
        return authorAvatar;
    }

    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar;
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
}