package com.example.richardwilliam_mobile;

import android.os.Parcel;
import android.os.Parcelable;

public class Picture implements Parcelable {
    private String images;
    private String desc;

    public Picture(String images, String desc) {
        this.images = images;
        this.desc = desc;
    }

    protected Picture(Parcel in) {
        images = in.readString();
        desc = in.readString();
    }

    public Picture(){}

    public static final Creator<Picture> CREATOR = new Creator<Picture>() {
        @Override
        public Picture createFromParcel(Parcel in) {
            return new Picture(in);
        }

        @Override
        public Picture[] newArray(int size) {
            return new Picture[size];
        }
    };

    public String getImages() {
        return images;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(images);
        parcel.writeString(desc);
    }
}
