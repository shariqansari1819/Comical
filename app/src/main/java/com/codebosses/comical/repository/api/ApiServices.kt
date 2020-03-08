package com.codebosses.comical.repository.api

import androidx.lifecycle.LiveData
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.repository.model.chapters.Chapter
import com.codebosses.comical.repository.model.comicdetail.ComicDetail
import com.codebosses.comical.repository.model.user.User
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 * Api services to communicate with server
 *
 */
interface ApiServices {

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @GET("getComics")
    fun getChapters(): LiveData<Resource<Chapter>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("getChepterDetail")
    fun getComicDetail(@Field("chapter_id") chapterId: Int): LiveData<Resource<ComicDetail>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("getComicDetail")
    fun getChapterDetail(@Field("comic_id") comicId: Int): LiveData<Resource<ChapterDetail>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("login")
    fun login(@Field("user_email") userEmail: String, @Field("password") password: String): LiveData<Resource<User>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("fbglogin")
    fun fbLogin(@Field("user_name") userName: String, @Field("device_id") device_id: Int,
                @Field("device_token") deviceToken: String, @Field("fb_id") fbId: String,
                @Field("profile_image_url") profileImageUrl: String,
                @Field("profile_image_thumb_url") profileImageThumbUrl: String): LiveData<Resource<User>>

    @Headers("secret_key: 23omE@Numb4_2!*&9")
    @FormUrlEncoded
    @POST("register")
    fun register(@Field("user_email") userEmail: String, @Field("phone_number") phoneNumber: String,
                 @Field("user_name") userName: String, @Field("password") password: String,
                 @Field("device_id") deviceId: Int,@Field("device_token") deviceToken: String): LiveData<Resource<User>>


}
