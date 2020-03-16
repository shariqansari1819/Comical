package com.codebosses.comical.di.modules

import com.codebosses.comical.ui.detail.FragmentAbout
import com.codebosses.comical.ui.main.profile.FragmentProfile
import com.codebosses.comical.ui.main.comic.FragmentComics
import com.codebosses.comical.ui.main.search.FragmentSearch
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    /**
     * Injecting Fragments
     */
//    @ContributesAndroidInjector
//    internal abstract fun contributeCountryListFragment(): CountryListFragment

    @ContributesAndroidInjector
    internal abstract fun contributeChapterFragment(): FragmentComics

    @ContributesAndroidInjector
    internal abstract fun contributeProfileFragment(): FragmentProfile

    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): FragmentSearch
}