package com.example.shortvideod.design;

public class ChatUser {
    String username;
    String time;
    String message;
    String img;
    boolean isonline;

    public ChatUser() {
    }

    public ChatUser(String username, String time, String message, String img, boolean isonline) {
        this.username = username;
        this.time = time;
        this.message = message;
        this.img = img;
        this.isonline = isonline;

    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean isIsonline() {
        return isonline;
    }

    public void setIsonline(boolean isonline) {
        this.isonline = isonline;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
