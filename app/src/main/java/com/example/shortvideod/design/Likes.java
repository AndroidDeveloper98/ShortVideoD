package com.example.shortvideod.design;

public class Likes {
    String userimg;
    String date;
    String comment;

    public Likes() {

    }

    public Likes(String userimg, String date, String comment) {
        this.userimg = userimg;
        this.date = date;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
