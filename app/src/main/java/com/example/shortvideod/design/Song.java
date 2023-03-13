package com.example.shortvideod.design;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Song implements Parcelable {


    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
    @Nullable
    String artist;
    String audio;
    @Nullable
    public String details;
    @Nullable
    public String albumImage;
    String id;
    String title;
    int duration;

    public Song() {
    }


    public Song(String id, String title, @Nullable String artist, @Nullable String album, String audio, int duration, String details) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.albumImage = album;
        this.audio = audio;

        this.duration = duration;
        this.details = details;


    }

    @Nullable
    public String getArtist() {
        return artist;
    }

    public void setArtist(@Nullable String artist) {
        this.artist = artist;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    @Nullable
    public String getDetails() {
        return details;
    }

    public void setDetails(@Nullable String details) {
        this.details = details;
    }

    @Nullable
    public String getAlbumImage() {
        return albumImage;
    }

    public void setAlbumImage(@Nullable String albumImage) {
        this.albumImage = albumImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    protected Song(Parcel in) {
        id = in.readString();
        title = in.readString();
        artist = in.readString();
        albumImage = in.readString();
        audio = in.readString();
        duration = in.readInt();
        details = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(albumImage);
        dest.writeString(audio);
        dest.writeInt(duration);
        dest.writeString(details);
    }
}
