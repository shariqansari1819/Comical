package com.codebosses.comical.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Chapter implements Parcelable {

    private String chapterId;
    private String comicId;
    private String name;
    private String image;
    private ArrayList<String> images;

    public Chapter(String chapterId, String comicId, String name,String image, ArrayList<String> images) {
        this.chapterId = chapterId;
        this.comicId = comicId;
        this.name = name;
        this.image = image;
        this.images = images;
    }

    public Chapter() {
    }

    protected Chapter(Parcel in) {
        chapterId = in.readString();
        comicId = in.readString();
        name = in.readString();
        image = in.readString();
        images = in.createStringArrayList();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chapterId);
        dest.writeString(comicId);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeStringList(images);
    }
}
