package com.codebosses.comical.repository.repo.chapters

import androidx.lifecycle.LiveData
import com.codebosses.comical.repository.api.ApiServices
import com.codebosses.comical.repository.api.network.NetworkResource
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.repository.model.chapters.Chapter
import com.codebosses.comical.repository.model.comicdetail.ComicDetail
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChaptersRepository @Inject constructor(
    private val apiServices: ApiServices
) {

    fun getChapters():
            LiveData<Resource<Chapter>> {

        return object : NetworkResource<Chapter>() {
            override fun createCall(): LiveData<Resource<Chapter>> {
                return apiServices.getChapters()
            }

        }.asLiveData()
    }

    fun getChapterDetail(chapterId: Int): LiveData<Resource<ChapterDetail>>{
        return object : NetworkResource<ChapterDetail>(){
            override fun createCall(): LiveData<Resource<ChapterDetail>> {
                return apiServices.getChapterDetail(chapterId)
            }

        }.asLiveData()
    }

    fun getComicDetail(comicId: Int): LiveData<Resource<ComicDetail>>{
        return object : NetworkResource<ComicDetail>(){
            override fun createCall(): LiveData<Resource<ComicDetail>> {
                return apiServices.getComicDetail(comicId)
            }

        }.asLiveData()
    }

}