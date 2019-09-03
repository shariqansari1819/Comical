package com.codebosses.comical.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.ActivityReadComicBinding;
import com.codebosses.comical.endpoints.EndpointKeys;

public class ReadComicActivity extends AppCompatActivity {

    //    Android fields....
    private ActivityReadComicBinding readComicBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readComicBinding = DataBindingUtil.setContentView(this, R.layout.activity_read_comic);

        if (getIntent() != null) {
            String comicUrl = getIntent().getStringExtra(EndpointKeys.COMIC_URL);
            if (comicUrl != null) {
//                readComicBinding.pdfViewer.fromUri(Uri.parse(comicUrl));
            }
        }

    }
}
