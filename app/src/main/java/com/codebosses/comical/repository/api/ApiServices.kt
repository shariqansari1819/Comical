package com.codebosses.comical.repository.api

import androidx.lifecycle.LiveData
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.repository.model.chapterreadreading.ChapterReadReading
import com.codebosses.comical.repository.model.comicdetail.Chapter
import com.codebosses.comical.repository.model.comicdetail.ComicDetail
import com.codebosses.comical.repository.model.comics.Comic
import com.codebosses.comical.repository.model.search.Search
import com.codebosses.comical.repository.model.user.User
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 * Api services to communicate with server
 *
 */
interface ApiServices {

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("getComics")
    fun getComics(@Field("user_id") userId: Int): LiveData<Resource<Comic>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("getChepterDetail")
    fun getChapterDetail(@Field("chapter_id") chapterId: Int,
                         @Field("user_id") userId: Int): LiveData<Resource<ChapterDetail>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("addRead")
    fun addReadChapter(@Field("comic_id") comicId: Int,
                       @Field("chapter_id") chapterId: Int,
                       @Field("user_id") userId: Int,
                       @Field("is_read") isRead: Int): LiveData<Resource<ResponseBody>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("addReading")
    fun addReadingChapter(@Field("comic_id") comicId: Int,
                       @Field("chapter_id") chapterId: Int,
                       @Field("user_id") userId: Int,
                       @Field("is_reading") isRead: Int): LiveData<Resource<ResponseBody>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("getComicDetail")
    fun getComicDetail(@Field("comic_id") comicId: Int, @Field("user_id") userId: Int): LiveData<Resource<ComicDetail>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("user_email") userEmail: String, @Field("password") password: String,
              @Field("device_id") deviceId: Int, @Field("device_token") deviceToken: String): LiveData<Resource<User>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("fbglogin")
    fun fbLogin(@Field("user_name") userName: String, @Field("device_id") device_id: Int,
                @Field("device_token") deviceToken: String, @Field("fb_id") fbId: String,
                @Field("g_id") gId: String,
                @Field("profile_image_url") profileImageUrl: String,
                @Field("profile_image_thumb_url") profileImageThumbUrl: String): LiveData<Resource<User>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("register")
    fun register(@Field("user_email") userEmail: String, @Field("phone_number") phoneNumber: String,
                 @Field("user_name") userName: String, @Field("password") password: String,
                 @Field("device_id") deviceId: Int, @Field("device_token") deviceToken: String): LiveData<Resource<User>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("searchComics")
    fun searchComic(@Field("search") search: String, @Field("user_id") userId: Int):
            LiveData<Resource<Search>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("favouriteUnfavourite")
    fun favoriteUnFavorite(@Field("user_id") userId: Int, @Field("comic_id") comicId: Int):
            LiveData<Resource<ResponseBody>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("comicRating")
    fun comicRating(@Field("user_id") userId: Int, @Field("comic_id") comicId: Int,
                    @Field("ratings") rating: Int):
            LiveData<Resource<ResponseBody>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("getFavourite")
    fun getFavorite(@Field("user_id") userId: Int): LiveData<Resource<Comic>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("getReadReading")
    fun getReadReading(@Field("user_id") userId: Int): LiveData<Resource<ChapterReadReading>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("notifSettings")
    fun updateNotification(@Field("user_id") userId: Int, @Field("notifs") notificationStatus: Int):
            LiveData<Resource<ResponseBody>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("updateprofile")
    fun updateProfile(@Field("user_id") userId: Int, @Field("user_name") userName: String,
                      @Field("phone_number") phoneNumber: String, @Field("profile_status") profileStatus: String):
            LiveData<Resource<User>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("updatePassword")
    fun updatePassword(@Field("user_email") userEmail: String, @Field("oldpassword") oldPassword: String,
                       @Field("newpassword") newPassword: String):
            LiveData<Resource<ResponseBody>>

}