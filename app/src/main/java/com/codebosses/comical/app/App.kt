package com.codebosses.comical.app

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.di.base.AppInjector
import com.codebosses.comical.utils.LogUtil
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        PrefUtils.init(this)
        AppInjector.init(this)
        LogUtil.init(true)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingAndroidInjector
    }
}