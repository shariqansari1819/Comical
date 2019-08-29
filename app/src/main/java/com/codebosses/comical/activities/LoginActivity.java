package com.codebosses.comical.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    //    Android fields....
    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        loginBinding.setClickHandler(new LoginClickHandler());

    }

    public class LoginClickHandler {

        public void onLoginClick(View view) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

        public void onFacebookClick(View view) {

        }

        public void onGoogleClick(View view) {

        }

    }
}