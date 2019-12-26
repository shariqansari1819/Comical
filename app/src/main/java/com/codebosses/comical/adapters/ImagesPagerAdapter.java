package com.codebosses.comical.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.codebosses.comical.R;

import java.util.ArrayList;

public class ImagesPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<String> images = new ArrayList<>();

    public ImagesPagerAdapter(Context context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.row_image, container, false);

        ImageView imageView = view.findViewById(R.id.imageViewReadyComic);
        CircularProgressBar circularProgressBar = view.findViewById(R.id.circularProgressReadComic);
        circularProgressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(images.get(position))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        circularProgressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        circularProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}