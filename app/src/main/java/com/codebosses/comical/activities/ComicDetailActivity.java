package com.codebosses.comical.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codebosses.comical.R;
import com.codebosses.comical.adapters.ComicDetailsPagerAdapter;
import com.codebosses.comical.databinding.ActivityComicDetailBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.Comic;

public class ComicDetailActivity extends AppCompatActivity {

    //    Android fields....
    ActivityComicDetailBinding comicDetailBinding;

    //    Adapter fields....
    ComicDetailsPagerAdapter comicDetailsPagerAdapter;

    //    Instance fields....
    private Comic comicGroup;
    private Typeface typeface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        comicDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_comic_detail);

        typeface = Typeface.createFromAsset(getAssets(),"fonts/comic_bold.ttf");

        if (getIntent() != null) {
            comicGroup = getIntent().getParcelableExtra(EndpointKeys.COMIC);
            if (comicGroup != null) {
                comicDetailBinding.setComic(comicGroup);
            }
        }

        comicDetailsPagerAdapter = new ComicDetailsPagerAdapter(this, getSupportFragmentManager(), 0, comicGroup);
        comicDetailBinding.viewPagerComicDetail.setAdapter(comicDetailsPagerAdapter);
        comicDetailBinding.viewPagerComicDetail.setOffscreenPageLimit(2);
        comicDetailBinding.tabLayoutComicDetail.setupWithViewPager(comicDetailBinding.viewPagerComicDetail);

        changeTabsFont();
    }

    private void changeTabsFont() {
        ViewGroup vg = (ViewGroup) comicDetailBinding.tabLayoutComicDetail.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(typeface);
                }
            }
        }
    }

}