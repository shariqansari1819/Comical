package com.codebosses.comical.ui.main.chapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.repository.model.chapters.Chapter
import com.codebosses.comical.repository.repo.chapters.ChaptersRepository
import javax.inject.Inject

class ChaptersViewModel @Inject constructor(
    private val chaptersRepository: ChaptersRepository
) : ViewModel() {


    private fun chapters(): LiveData<Resource<Chapter>> =
        chaptersRepository.getChapters()

    fun getChapters() = chapters()

    private fun chapterDetail(chapterId: Int): LiveData<Resource<ChapterDetail>> =
        chaptersRepository.getChapterDetail(chapterId)

    fun getChapterDetail(chapterId:Int) = chapterDetail(chapterId)

}