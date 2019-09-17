package com.codebosses.comical.pojo;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codebosses.comical.R;

public class Comic implements Parcelable {

    private String category;
    private String image;
    private String name;
    private String comicId;


    public Comic() {
    }

    public Comic(String category, String image, String name, String comicId) {
        this.category = category;
        this.image = image;
        this.name = name;
        this.comicId = comicId;
    }

    protected Comic(Parcel in) {
        category = in.readString();
        image = in.readString();
        name = in.readString();
        comicId = in.readString();
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(category);
        dest.writeString(image);
        dest.writeString(name);
        dest.writeString(comicId);
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
