package com.example.shortvideod.design;

public class MentionImg {
    String name;
    String username;
    String image;

    public MentionImg(String name, String username, String image) {
        this.name = name;
        this.username = username;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
