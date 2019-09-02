package com.codebosses.comical.application;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.google.firebase.FirebaseApp;

public class ComicalApplication extends Application {

    private static ComicalApplication comicalApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        comicalApplication = this;
        FirebaseApp.initializeApp(comicalApplication);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(comicalApplication);
    }

    public static ComicalApplication getInstance() {
        return comicalApplication;
    }

}
