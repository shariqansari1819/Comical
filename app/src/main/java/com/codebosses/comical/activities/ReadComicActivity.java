package com.codebosses.comical.activities;

import android.os.Bundle;
import android.view.MenuItem;

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

//        Setting custom action bar....

        if (getIntent() != null) {
            ArrayList<String> comicUrl = getIntent().getStringArrayListExtra(EndpointKeys.COMIC_URL);
            String name = getIntent().getStringExtra(EndpointKeys.COMIC_NAME);
            setSupportActionBar(readComicBinding.toolbarReadComic);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(name);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if (comicUrl != null) {
                ImagesPagerAdapter imagesPagerAdapter = new ImagesPagerAdapter(this, comicUrl);
                readComicBinding.viewPager.setAdapter(imagesPagerAdapter);
                BookFlipPageTransformer bookFlipPageTransformer = new BookFlipPageTransformer();
                bookFlipPageTransformer.setEnableScale(true);
                bookFlipPageTransformer.setScaleAmountPercent(10f);
                readComicBinding.viewPager.setPageTransformer(true, bookFlipPageTransformer);
                readComicBinding.viewPager.setOffscreenPageLimit(comicUrl.size() - 1);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
