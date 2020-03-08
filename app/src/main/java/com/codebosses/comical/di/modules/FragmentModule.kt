package com.codebosses.comical.di.modules

import com.codebosses.comical.ui.main.FragmentProfile
import com.codebosses.comical.ui.main.chapters.FragmentChapters
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
    internal abstract fun contributeChapterFragment(): FragmentChapters

    @ContributesAndroidInjector
    internal abstract fun contributeProfileFragment(): FragmentProfile

    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): FragmentSearch
}