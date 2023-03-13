package com.example.shortvideod.design;

public class Reels {
    User user;
    String caption;
    String video;
    String email;
    int likes;
    int comments;

    boolean islike;

    public boolean isIslike() {
        return islike;
    }

    public void setIslike(boolean islike) {
        this.islike = islike;
    }

    public Reels() {
    }


    public Reels(User user, String caption, String video, String email, int likes, int comments) {
        this.user = user;
        this.caption = caption;
        this.video = video;
        this.email = email;
        this.likes = likes;
        this.comments = comments;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }


}
