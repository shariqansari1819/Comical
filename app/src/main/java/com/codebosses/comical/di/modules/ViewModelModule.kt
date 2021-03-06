package com.codebosses.comical.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.codebosses.comical.ui.intro.IntroViewModel
import com.codebosses.comical.di.base.ViewModelFactory
import com.codebosses.comical.di.base.ViewModelKey
import com.codebosses.comical.ui.detail.ChapterReadViewModel
import com.codebosses.comical.ui.main.comic.ComicsViewModel
import com.codebosses.comical.ui.main.profile.ProfileViewModel
import com.codebosses.comical.ui.main.search.SearchViewModel
import com.codebosses.comical.ui.registration.login.LoginViewModel
import com.codebosses.comical.ui.registration.signup.SignUpViewModel
import com.codebosses.comical.ui.setting.ChangePasswordViewModel
import com.codebosses.comical.ui.setting.EditProfileViewModel
import com.codebosses.comical.ui.setting.SettingViewModel
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
    @ViewModelKey(ChapterReadViewModel::class)
    abstract fun bindComicReadViewModel(comicReadViewModel: ChapterReadViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ComicsViewModel::class)
    abstract fun bindChaptersViewModel(chaptersViewModel: ComicsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(profileViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingViewModel::class)
    abstract fun bindSettingViewModel(settingViewModel: SettingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel::class)
    abstract fun bindEditProfileViewModel(editProfileViewModel: EditProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChangePasswordViewModel::class)
    abstract fun bindChangePasswordViewModel(changePasswordViewModel: ChangePasswordViewModel): ViewModel

    /**
     * Binds ViewModels factory to provide ViewModels.
     */
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
