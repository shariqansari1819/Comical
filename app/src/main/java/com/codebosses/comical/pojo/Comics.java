package com.codebosses.comical.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class Comics implements Parcelable {

    private String comicId;
    private String comicGroupId;
    private String comicName;
    private String comicPosterPath;
    private String comicBannerPath;
    private String comicUrl;

    public Comics() {
    }

    public Comics(String comicId, String comicGroupId, String comicName, String comicPosterPath, String comicBannerPath, String comicUrl) {
        this.comicId = comicId;
        this.comicGroupId = comicGroupId;
        this.comicName = comicName;
        this.comicPosterPath = comicPosterPath;
        this.comicBannerPath = comicBannerPath;
        this.comicUrl = comicUrl;
    }

    protected Comics(Parcel in) {
        comicId = in.readString();
        comicGroupId = in.readString();
        comicName = in.readString();
        comicPosterPath = in.readString();
        comicBannerPath = in.readString();
        comicUrl = in.readString();
    }

    public static final Creator<Comics> CREATOR = new Creator<Comics>() {
        @Override
        public Comics createFromParcel(Parcel in) {
            return new Comics(in);
        }

        @Override
        public Comics[] newArray(int size) {
            return new Comics[size];
        }
    };

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getComicGroupId() {
        return comicGroupId;
    }

    public void setComicGroupId(String comicGroupId) {
        this.comicGroupId = comicGroupId;
    }

    public String getComicName() {
        return comicName;
    }

    public void setComicName(String comicName) {
        this.comicName = comicName;
    }

    public String getComicPosterPath() {
        return comicPosterPath;
    }

    public void setComicPosterPath(String comicPosterPath) {
        this.comicPosterPath = comicPosterPath;
    }

    public String getComicBannerPath() {
        return comicBannerPath;
    }

    public void setComicBannerPath(String comicBannerPath) {
        this.comicBannerPath = comicBannerPath;
    }

    public String getComicUrl() {
        return comicUrl;
    }

    public void setComicUrl(String comicUrl) {
        this.comicUrl = comicUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(comicId);
        dest.writeString(comicGroupId);
        dest.writeString(comicName);
        dest.writeString(comicPosterPath);
        dest.writeString(comicBannerPath);
        dest.writeString(comicUrl);
    }
}
