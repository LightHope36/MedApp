package com.example.authorization;

import java.io.Serializable;

public class Person implements Serializable {

    private String avatar;
    private String name;
    private String messageTime;
    private String dopinfo;
    private String number;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDopinfo() {
        return dopinfo;
    }

    public void setDopinfo(String dopinfo) {
        this.dopinfo = dopinfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
