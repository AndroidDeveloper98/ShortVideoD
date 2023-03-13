package com.example.shortvideod.workers;

import android.os.Parcel;
import android.os.Parcelable;

public class Draft implements Parcelable {
    public static final Parcelable.Creator<Draft> CREATOR = new Parcelable.Creator<Draft>() {
        public Draft createFromParcel(Parcel in) {
            return new Draft(in);
        }

        public Draft[] newArray(int size) {
            return new Draft[size];
        }
    };
    public String description;
    public boolean hasComments;

    /* renamed from: id */
    public int f10id;
    public String location;
    public String preview;
    public int privacy;
    public String screenshot;
    public String songId;
    public String video;

    public Draft() {
    }

    protected Draft(Parcel in) {
        this.f10id = in.readInt();
        this.video = in.readString();
        this.screenshot = in.readString();
        this.preview = in.readString();
        this.description = in.readString();
        this.hasComments = in.readByte() != 0;
        this.location = in.readString();
        this.privacy = in.readInt();
        this.songId = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.f10id);
        dest.writeString(this.video);
        dest.writeString(this.screenshot);
        dest.writeString(this.preview);
        dest.writeString(this.description);
        dest.writeByte(this.hasComments ? (byte) 1 : 0);
        dest.writeString(this.location);
        dest.writeInt(this.privacy);
        dest.writeString(this.songId);
    }
}
