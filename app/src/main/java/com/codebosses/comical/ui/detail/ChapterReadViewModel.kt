package com.codebosses.comical.ui.detail

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.comic.ComicRepository
import javax.inject.Inject

class ChapterReadViewModel @Inject constructor(private val chaptersRepository: ComicRepository) :
        ViewModel() {

    private fun chapterDetail(
            chapterId: Int,
            userId: Int
    ) = chaptersRepository.getChapterDetail(chapterId,userId)

    fun getChapterDetail(chapterId: Int,userId: Int) =
            chapterDetail(chapterId,userId)

    private fun addRead(comicId: Int, chapterId: Int, userId: Int) =
            chaptersRepository.addReadChapter(comicId, chapterId, userId)

    fun addReadChapter(comicId: Int, chapterId: Int, userId: Int) =
            addRead(comicId, chapterId, userId)

    private fun addReading(comicId: Int, chapterId: Int, userId: Int) =
            chaptersRepository.addReadingChapter(comicId, chapterId, userId)

    fun addReadingChapter(comicId: Int, chapterId: Int, userId: Int) =
            addReading(comicId, chapterId, userId)

}