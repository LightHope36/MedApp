package com.example.authorization;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Message implements Serializable {

    private String messageText;
    private String messageUser;
    private String messageTime;
    private Long messageId;
    private Bitmap image;
    private int messageType;
    private int messageVoicetime;
    private String Imagefile;

    public Message(String messageText, String messageUser, String messageTime) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.messageTime = messageTime;
    }

    public Message(){

    }

    public void setImagefile(String imagefile) {
        Imagefile = imagefile;
    }

    public String getImagefile() {
        return Imagefile;
    }

    public int getMessageVoicetime() {
        return messageVoicetime;
    }

    public void setMessageVoicetime(int messageVoicetime) {
        this.messageVoicetime = messageVoicetime;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public int getMessageType() {
        return messageType;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public Long getMessageId() {
        return messageId;
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

    public String  getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}