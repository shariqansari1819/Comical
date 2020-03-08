package com.codebosses.comical.di.modules

import android.content.Context
import android.content.res.Resources
import com.codebosses.comical.BuildConfig
import com.codebosses.comical.app.App
import com.codebosses.comical.repository.api.network.LiveDataCallAdapterFactoryForRetrofit
import com.codebosses.comical.repository.api.ApiServices
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module(includes = [PrefrencesModule::class, ActivityModule::class, ViewModelModule::class])
class AppModule {

    /**
     * Static variables to hold base url's etc.
     */
    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
    }


    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    fun provideNewsService(okHttpClient: OkHttpClient): ApiServices {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactoryForRetrofit())
            .client(okHttpClient)
            .build()
            .create(ApiServices::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val APPLICATION_JSON_CHARSET_UTF_8 = "application/json; charset=utf-8"

        val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val response = chain.proceed(chain.request())
            // Do anything with response here
            response.header("Content-Type", APPLICATION_JSON_CHARSET_UTF_8)
            response.header("Accept", APPLICATION_JSON_CHARSET_UTF_8)
            response
        }.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
        return httpClient.build()
    }


    /**
     * Provides app AppDatabase
     */
    /*@Singleton
    @Provides
    fun provideDb(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "news-db").build()
    }


    *//**
     * Provides NewsArticlesDao an object to access NewsArticles table from Database
     *//*
    @Singleton
    @Provides
    fun provideUserDao(db: AppDatabase): NewsArticlesDao {
        return db.newsArticlesDao()
    }

    *//**
     * Provides CountriesDao an object to access Countries table from Database
     *//*
    @Singleton
    @Provides
    fun provideCountriesDao(db: AppDatabase): CountriesDao {
        return db.countriesDao()
    }*/


    /**
     * Application application level context.
     */
    @Singleton
    @Provides
    fun provideContext(application: App): Context {
        return application.applicationContext
    }


    /**
     * Application resource provider, so that we can get the Drawable, Color, String etc at runtime
     */
    @Provides
    @Singleton
    fun providesResources(application: App): Resources = application.resources
}
