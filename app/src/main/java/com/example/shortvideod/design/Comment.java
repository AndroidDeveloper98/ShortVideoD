package com.example.shortvideod.design;

public class Comment {
    String username;
    String userimg;
    String date;
    String comm;

    public Comment() {

    }

    public Comment(String username, String userimg, String date, String comment) {
        this.username = username;
        this.userimg = userimg;
        this.date = date;
        this.comm = comment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimg() {
        return userimg;
    }

    public void setUserimg(String userimg) {
        this.userimg = userimg;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm;
    }
}
