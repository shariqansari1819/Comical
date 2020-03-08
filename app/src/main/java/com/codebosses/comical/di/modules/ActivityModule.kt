package com.codebosses.comical.di.modules

import com.codebosses.comical.ui.detail.ChapterDetailActivity
import com.codebosses.comical.ui.detail.ComicReadActivity
import com.codebosses.comical.ui.intro.IntroActivity
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.ui.registration.login.LoginActivity
import com.codebosses.comical.ui.registration.signup.SignUpActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * All your Activities participating in DI would be listed here.
 */
@Module(includes = [FragmentModule::class]) // Including Fragment Module Available For Activities
abstract class ActivityModule {

    /**
     * Marking Activities to be available to contributes for Android Injector
     */
    @ContributesAndroidInjector
    abstract fun contributeIntroActivity(): IntroActivity

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    abstract fun contributeSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector
    abstract fun contributeChapterDetailActivity(): ChapterDetailActivity

    @ContributesAndroidInjector
    abstract fun contributeReadComicActivity(): ComicReadActivity

//    @ContributesAndroidInjector
//    abstract fun contributeNewsArticlesActivity(): NewsArticlesActivity
//
//    @ContributesAndroidInjector
//    abstract fun contributeCountryListingActivity(): CountryListingActivity
}
