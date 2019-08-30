package com.codebosses.comical.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.codebosses.comical.R;

public class Comics implements Parcelable {

    private int image;
    private String title;
    private String edition;
    private String backDropPath;
    private double rating;
    private String publisher;
    private String firstApearance;
    private String description;
    private String author;
    private String pdfUrl;

    public Comics(int image, String title, String edition, String backDropPath, double rating, String publisher, String firstApearance, String description, String author, String pdfUrl) {
        this.image = image;
        this.title = title;
        this.edition = edition;
        this.backDropPath = backDropPath;
        this.rating = rating;
        this.publisher = publisher;
        this.firstApearance = firstApearance;
        this.description = description;
        this.author = author;
        this.pdfUrl = pdfUrl;
    }

    protected Comics(Parcel in) {
        image = in.readInt();
        title = in.readString();
        edition = in.readString();
        backDropPath = in.readString();
        rating = in.readDouble();
        publisher = in.readString();
        firstApearance = in.readString();
        description = in.readString();
        author = in.readString();
        pdfUrl = in.readString();
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getBackDropPath() {
        return backDropPath;
    }

    public void setBackDropPath(String backDropPath) {
        this.backDropPath = backDropPath;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getFirstApearance() {
        return firstApearance;
    }

    public void setFirstApearance(String firstApearance) {
        this.firstApearance = firstApearance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @BindingAdapter({"android:image"})
    public static void setImageUrl(ImageView imageView, int imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .placeholder(R.drawable.comic_placeholder)
                .into(imageView);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(image);
        dest.writeString(title);
        dest.writeString(edition);
        dest.writeString(backDropPath);
        dest.writeDouble(rating);
        dest.writeString(publisher);
        dest.writeString(firstApearance);
        dest.writeString(description);
        dest.writeString(author);
        dest.writeString(pdfUrl);
    }
}
