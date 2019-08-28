package com.codebosses.comical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.codebosses.comical.R;
import com.codebosses.comical.pojo.IntroPagerModel;

import java.util.ArrayList;
import java.util.List;

public class IntroPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<IntroPagerModel> introPagerModelList = new ArrayList<>();

    public IntroPagerAdapter(Context context, List<IntroPagerModel> introPagerModelList) {
        this.context = context;
        this.introPagerModelList = introPagerModelList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return introPagerModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.pager_item_intro, container, false);
        ImageView imageView = view.findViewById(R.id.imageViewPagerItemIntro);
        TextView textViewHeading = view.findViewById(R.id.textViewHeadingPagerItemIntro);
        TextView textViewDescription = view.findViewById(R.id.textViewDescriptionPagerItemIntro);

        Glide.with(context)
                .load(introPagerModelList.get(position).getImage())
                .into(imageView);
        textViewHeading.setText(introPagerModelList.get(position).getTitle());
        textViewDescription.setText(introPagerModelList.get(position).getDescription());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
