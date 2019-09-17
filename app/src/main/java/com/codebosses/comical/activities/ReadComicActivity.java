package com.codebosses.comical.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.codebosses.comical.R;
import com.codebosses.comical.adapters.ImagesPagerAdapter;
import com.codebosses.comical.databinding.ActivityReadComicBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.ArrayList;

public class ReadComicActivity extends AppCompatActivity {

    //    Android fields....
    private ActivityReadComicBinding readComicBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readComicBinding = DataBindingUtil.setContentView(this, R.layout.activity_read_comic);

        if (getIntent() != null) {
            ArrayList<String> comicUrl = getIntent().getStringArrayListExtra(EndpointKeys.COMIC_URL);
            if (comicUrl != null) {
                ImagesPagerAdapter imagesPagerAdapter = new ImagesPagerAdapter(this,comicUrl);
                readComicBinding.viewPager.setAdapter(imagesPagerAdapter);
                BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();

// Enable / Disable scaling while flipping. If true, then next page will scale in (zoom in). By default, its true.
                bookFlipPageTransformer.setEnableScale(true);

// The amount of scale the page will zoom. By default, its 5 percent.
                bookFlipPageTransformer.setScaleAmountPercent(10f);

// Assign the page transformer to the ViewPager.
                readComicBinding.viewPager.setPageTransformer(true, bookFlipPageTransformer);
            }
        }

    }
}
