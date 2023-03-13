package com.example.shortvideod.design;

public class Mentions {
    String userimg;
    String date;
    String comment;
    String mentionuser;

    public Mentions() {

    }

    public Mentions(String userimg, String mentionuser, String date, String comment) {
        this.mentionuser = mentionuser;
        this.userimg = userimg;
        this.date = date;
        this.comment = comment;
    }

    public String getMentionuser() {
        return mentionuser;
    }

    public void setMentionuser(String mentionuser) {
        this.mentionuser = mentionuser;
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
