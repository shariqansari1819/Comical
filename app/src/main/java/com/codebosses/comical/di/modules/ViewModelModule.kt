package com.codebosses.comical.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codebosses.comical.ui.intro.IntroViewModel
import com.codebosses.comical.di.base.ViewModelFactory
import com.codebosses.comical.di.base.ViewModelKey
import com.codebosses.comical.ui.detail.ComicReadViewModel
import com.codebosses.comical.ui.main.chapters.ChaptersViewModel
import com.codebosses.comical.ui.registration.login.LoginViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class ViewModelModule {

    /**
     * Binding NewsArticleViewModel using this key "NewsArticleViewModel::class"
     * So you can get NewsArticleViewModel using "NewsArticleViewModel::class" key from factory
     */
    /*@Binds
    @IntoMap
    @ViewModelKey(NewsArticleViewModel::class)
    abstract fun bindNewsArticleViewModel(newsArticleViewModel: NewsArticleViewModel): ViewModel

    *//**
     * Countries List View Model
     *//*
    @Binds
    @IntoMap
    @ViewModelKey(CountriesViewModel::class)
    abstract fun bindCountriesViewModel(countriesViewModel: CountriesViewModel): ViewModel*/

    @Binds
    @IntoMap
    @ViewModelKey(IntroViewModel::class)
    abstract fun bindIntroViewModel(introViewModel: IntroViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicReadViewModel::class)
    abstract fun bindComicReadViewModel(comicReadViewModel: ComicReadViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChaptersViewModel::class)
    abstract fun bindChaptersViewModel(chaptersViewModel: ChaptersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel


    /**
     * Binds ViewModels factory to provide ViewModels.
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}