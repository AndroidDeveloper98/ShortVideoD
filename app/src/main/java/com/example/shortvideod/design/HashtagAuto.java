package com.example.shortvideod.design;

public class HashtagAuto {
    String hashtag;
    int count;


    public HashtagAuto(String hashtag, int clip) {
        this.hashtag = hashtag;
        this.count = clip;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
