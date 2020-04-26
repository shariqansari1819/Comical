package com.codebosses.comical.repository.repo.comic

import androidx.lifecycle.LiveData
import com.codebosses.comical.repository.api.ApiServices
import com.codebosses.comical.repository.api.network.NetworkResource
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.repository.model.chapterreadreading.ChapterReadReading
import com.codebosses.comical.repository.model.comicdetail.ComicDetail
import com.codebosses.comical.repository.model.comics.Comic
import com.codebosses.comical.repository.model.search.Search
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicRepository @Inject constructor(
        private val apiServices: ApiServices
) {

    fun getComics(userId: Int):
            LiveData<Resource<Comic>> {

        return object : NetworkResource<Comic>() {
            override fun createCall(): LiveData<Resource<Comic>> {
                return apiServices.getComics(userId)
            }

        }.asLiveData()
    }

    fun getComicDetail(comicId: Int, userId: Int): LiveData<Resource<ComicDetail>> {
        return object : NetworkResource<ComicDetail>() {
            override fun createCall(): LiveData<Resource<ComicDetail>> {
                return apiServices.getComicDetail(comicId, userId)
            }

        }.asLiveData()
    }

    fun getChapterDetail(chapterId: Int): LiveData<Resource<ChapterDetail>> {
        return object : NetworkResource<ChapterDetail>() {
            override fun createCall(): LiveData<Resource<ChapterDetail>> {
                return apiServices.getChapterDetail(chapterId)
            }

        }.asLiveData()
    }

    fun searchComic(search: String, userId: Int): LiveData<Resource<Search>> {
        return object : NetworkResource<Search>() {
            override fun createCall(): LiveData<Resource<Search>> {
                return apiServices.searchComic(search, userId)
            }

        }.asLiveData()
    }

    fun favoriteUnFavoriteComic(comicId: Int, userId: Int): LiveData<Resource<ResponseBody>> {
        return object : NetworkResource<ResponseBody>() {
            override fun createCall(): LiveData<Resource<ResponseBody>> {
                return apiServices.favoriteUnFavorite(userId, comicId)
            }

        }.asLiveData()
    }

    fun comicRating(comicId: Int, userId: Int, rating: Int): LiveData<Resource<ResponseBody>> {
        return object : NetworkResource<ResponseBody>() {
            override fun createCall(): LiveData<Resource<ResponseBody>> {
                return apiServices.comicRating(userId, comicId, rating)
            }

        }.asLiveData()
    }

    fun getFavoriteComics(userId: Int): LiveData<Resource<Comic>> {
        return object : NetworkResource<Comic>() {
            override fun createCall(): LiveData<Resource<Comic>> {
                return apiServices.getFavorite(userId)
            }

        }.asLiveData()
    }

    fun getReadReading(userId: Int): LiveData<Resource<ChapterReadReading>> {
        return object : NetworkResource<ChapterReadReading>() {
            override fun createCall(): LiveData<Resource<ChapterReadReading>> {
                return apiServices.getReadReading(userId)
            }

        }.asLiveData()
    }


}