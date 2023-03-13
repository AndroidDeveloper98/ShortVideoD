package com.example.shortvideod.design;

public class GiftList {
    String senderImg;
    String comment;
    int giftImg;

    public GiftList(String senderImg, String comment, int giftImg) {
        this.senderImg = senderImg;
        this.comment = comment;
        this.giftImg = giftImg;
    }

    public String getSenderImg() {
        return senderImg;
    }

    public void setSenderImg(String senderImg) {
        this.senderImg = senderImg;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGiftImg() {
        return giftImg;
    }

    public void setGiftImg(int giftImg) {
        this.giftImg = giftImg;
    }
}
