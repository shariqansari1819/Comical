package com.codebosses.comical.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.codebosses.comical.R;
import com.codebosses.comical.adapters.IntroPagerAdapter;
import com.codebosses.comical.databinding.ActivityIntroBinding;
import com.codebosses.comical.pojo.IntroPagerModel;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //    Android fields....
    private ActivityIntroBinding introBinding;

    //    Adapter fields....
    private IntroPagerAdapter introPagerAdapter;
    private List<IntroPagerModel> introPagerModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro);

        createDataSet();
        introPagerAdapter = new IntroPagerAdapter(this, introPagerModelList);
        introBinding.viewPagerIntro.setAdapter(introPagerAdapter);
        introBinding.viewPagerIntro.setOffscreenPageLimit(1);
        introBinding.viewPagerIntro.addOnPageChangeListener(this);
        changeImageSelection(introBinding.imageViewCircleOneIntro, introBinding.imageViewCircleTwoIntro);

        introBinding.setClickHandler(new IntroClickHandler());

    }

    private void createDataSet() {
        introPagerModelList.add(new IntroPagerModel(getResources().getString(R.string.intro_one_heading), getResources().getString(R.string.intro_one_description), R.drawable.icon_intro_one));
        introPagerModelList.add(new IntroPagerModel(getResources().getString(R.string.intro_two_heading), getResources().getString(R.string.intro_two_description), R.drawable.icon_intro_two));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                changeImageSelection(introBinding.imageViewCircleOneIntro, introBinding.imageViewCircleTwoIntro);
                break;
            case 1:
                changeImageSelection(introBinding.imageViewCircleTwoIntro, introBinding.imageViewCircleOneIntro);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void changeImageSelection(ImageView imageViewSelected, ImageView imageViewDeselected) {
        imageViewSelected.setSelected(true);
        imageViewDeselected.setSelected(false);
    }


    public class IntroClickHandler {

        public void onSkipClick(View view) {
            startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        }

    }
}
