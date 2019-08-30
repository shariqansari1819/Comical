package com.codebosses.comical.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.codebosses.comical.R;
import com.codebosses.comical.adapters.ComicDetailsPagerAdapter;
import com.codebosses.comical.databinding.ActivityComicDetailBinding;

public class ComicDetailActivity extends AppCompatActivity {

    //    Android fields....
    ActivityComicDetailBinding comicDetailBinding;

    //    Adapter fields....
    ComicDetailsPagerAdapter comicDetailsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_detail);

        comicDetailsPagerAdapter = new ComicDetailsPagerAdapter(this, getSupportFragmentManager(), 0);
        comicDetailBinding.viewPagerComicDetail.setAdapter(comicDetailsPagerAdapter);
        comicDetailBinding.viewPagerComicDetail.setOffscreenPageLimit(2);
        comicDetailBinding.tabLayoutComicDetail.setupWithViewPager(comicDetailBinding.viewPagerComicDetail);
    }

}
