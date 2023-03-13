package com.example.shortvideod.design;

public class UserPostVideo {

    String bio;
    String img;

    public UserPostVideo(String bio, String img) {
        this.bio = bio;
        this.img = img;
    }

    public UserPostVideo() {
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
