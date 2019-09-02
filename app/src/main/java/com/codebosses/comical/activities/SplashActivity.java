package com.codebosses.comical.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.codebosses.comical.R;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.utils.PrefUtils;

public class SplashActivity extends AppCompatActivity {

    private Handler handler;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if ((Boolean) PrefUtils.getFromPrefs(context, EndpointKeys.IS_USER_FIRST_TIME, true)) {
                    Intent intent = new Intent(context, IntroActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(null);
        }
    }
}