package com.codebosses.comical.ui.detail

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.comic.ComicRepository
import javax.inject.Inject

class ChapterReadViewModel @Inject constructor(private val chaptersRepository: ComicRepository) :
    ViewModel() {

    private fun chapterDetail(
        chapterId: Int
    ) = chaptersRepository.getChapterDetail(chapterId)

    fun getChapterDetail(chapterId: Int) = chapterDetail(chapterId)


}