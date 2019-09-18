package com.codebosses.comical.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Chapter implements Parcelable {

    private String chapterId;
    private String comicId;
    private String name;
    private String posterPath;
    private ArrayList<String> images;

    public Chapter() {
    }

    public Chapter(String chapterId, String comicId, String name, String posterPath, ArrayList<String> images) {
        this.chapterId = chapterId;
        this.comicId = comicId;
        this.name = name;
        this.posterPath = posterPath;
        this.images = images;
    }

    protected Chapter(Parcel in) {
        chapterId = in.readString();
        comicId = in.readString();
        name = in.readString();
        posterPath = in.readString();
        images = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(chapterId);
        dest.writeString(comicId);
        dest.writeString(name);
        dest.writeString(posterPath);
        dest.writeStringList(images);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }
}
