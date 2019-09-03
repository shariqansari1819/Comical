package com.codebosses.comical.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codebosses.comical.R;

public class ComicGroup implements Parcelable {

    private String groupName;
    private String groupId;
    private String groupPosterPath;
    private String groupDescription;
    private double rating;
    private String backDropPath;
    private String writer;
    private String publisher;
    private String releaseDate;

    public ComicGroup() {
    }

    public ComicGroup(String groupName, String groupId, String groupPosterPath, String groupDescription, double rating, String backDropPath, String writer, String publisher, String releaseDate) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupPosterPath = groupPosterPath;
        this.groupDescription = groupDescription;
        this.rating = rating;
        this.backDropPath = backDropPath;
        this.writer = writer;
        this.publisher = publisher;
        this.releaseDate = releaseDate;
    }

    protected ComicGroup(Parcel in) {
        groupName = in.readString();
        groupId = in.readString();
        groupPosterPath = in.readString();
        groupDescription = in.readString();
        rating = in.readDouble();
        backDropPath = in.readString();
        writer = in.readString();
        publisher = in.readString();
        releaseDate = in.readString();
    }

    public static final Creator<ComicGroup> CREATOR = new Creator<ComicGroup>() {
        @Override
        public ComicGroup createFromParcel(Parcel in) {
            return new ComicGroup(in);
        }

        @Override
        public ComicGroup[] newArray(int size) {
            return new ComicGroup[size];
        }
    };

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupPosterPath() {
        return groupPosterPath;
    }

    public void setGroupPosterPath(String groupPosterPath) {
        this.groupPosterPath = groupPosterPath;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @BindingAdapter({"android:image"})
    public static void setImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().centerCrop())
                .apply(new RequestOptions().placeholder(R.drawable.comic_placeholder))
                .into(imageView);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupName);
        dest.writeString(groupId);
        dest.writeString(groupPosterPath);
        dest.writeString(groupDescription);
        dest.writeDouble(rating);
        dest.writeString(backDropPath);
        dest.writeString(writer);
        dest.writeString(publisher);
        dest.writeString(releaseDate);
    }
}
