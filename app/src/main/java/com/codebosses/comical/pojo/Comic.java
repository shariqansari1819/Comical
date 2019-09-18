package com.codebosses.comical.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codebosses.comical.R;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Comic implements Parcelable {

    private String category;
    private String comicGenre;
    private String comicId;
    private String comicName;
    private String comicPosterPath;
    private String comicBannerPath;
    private double rating;
    private String releaseDate;
    private String summary;
    private String tags;
    @ServerTimestamp
    Date timestamp;


    public Comic() {
    }

    public Comic(String category, String comicId, String comicGenre, String comicName, String comicPosterPath,String comicBannerPath, double rating, String releaseDate, String summary, String tags) {
        this.category = category;
        this.comicId = comicId;
        this.comicGenre = comicGenre;
        this.comicName = comicName;
        this.comicPosterPath = comicPosterPath;
        this.comicBannerPath = comicBannerPath;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.summary = summary;
        this.tags = tags;
    }

    protected Comic(Parcel in) {
        category = in.readString();
        comicGenre = in.readString();
        comicId = in.readString();
        comicName = in.readString();
        comicPosterPath = in.readString();
        comicBannerPath = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
        summary = in.readString();
        tags = in.readString();
    }

    public static final Creator<Comic> CREATOR = new Creator<Comic>() {
        @Override
        public Comic createFromParcel(Parcel in) {
            return new Comic(in);
        }

        @Override
        public Comic[] newArray(int size) {
            return new Comic[size];
        }
    };

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComicGenre() {
        return comicGenre;
    }

    public void setComicGenre(String comicGenre) {
        this.comicGenre = comicGenre;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
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

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(comicGenre);
        dest.writeString(comicId);
        dest.writeString(comicName);
        dest.writeString(comicPosterPath);
        dest.writeString(comicBannerPath);
        dest.writeDouble(rating);
        dest.writeString(releaseDate);
        dest.writeString(summary);
        dest.writeString(tags);
    }

    @BindingAdapter({"android:image"})
    public static void setImage(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.comic_placeholder))
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
    }
}
