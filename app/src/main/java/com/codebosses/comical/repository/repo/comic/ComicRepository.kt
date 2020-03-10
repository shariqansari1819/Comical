package com.codebosses.comical.repository.repo.comic

import androidx.lifecycle.LiveData
import com.codebosses.comical.repository.api.ApiServices
import com.codebosses.comical.repository.api.network.NetworkResource
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.repository.model.comicdetail.ComicDetail
import com.codebosses.comical.repository.model.comics.Comic
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ComicRepository @Inject constructor(
        private val apiServices: ApiServices
) {

    fun getComics():
            LiveData<Resource<Comic>> {

        return object : NetworkResource<Comic>() {
            override fun createCall(): LiveData<Resource<Comic>> {
                return apiServices.getComics()
            }

        }.asLiveData()
    }

    fun getComicDetail(comicId: Int): LiveData<Resource<ComicDetail>> {
        return object : NetworkResource<ComicDetail>() {
            override fun createCall(): LiveData<Resource<ComicDetail>> {
                return apiServices.getComicDetail(comicId)
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

    fun searchComic(search: String): LiveData<Resource<Comic>> {
        return object : NetworkResource<Comic>() {
            override fun createCall(): LiveData<Resource<Comic>> {
                return apiServices.searchComic(search)
            }

        }.asLiveData()
    }

}