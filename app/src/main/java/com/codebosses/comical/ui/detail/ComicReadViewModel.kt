package com.codebosses.comical.ui.detail

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.chapters.ChaptersRepository
import javax.inject.Inject

class ComicReadViewModel @Inject constructor(private val chaptersRepository: ChaptersRepository) :
    ViewModel() {

    private fun comicDetail(
        comicId: Int
    ) = chaptersRepository.getComicDetail(comicId)

    fun getComicDetail(comicId: Int) = comicDetail(comicId)


}