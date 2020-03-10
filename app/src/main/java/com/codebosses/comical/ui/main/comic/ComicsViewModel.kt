package com.codebosses.comical.ui.main.comic

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.api.network.Resource
import com.codebosses.comical.repository.model.comicdetail.ComicDetail
import com.codebosses.comical.repository.model.comics.Comic
import com.codebosses.comical.repository.repo.comic.ComicRepository
import javax.inject.Inject

class ComicsViewModel @Inject constructor(
        private val chaptersRepository: ComicRepository
) : ViewModel() {


    private fun comics(): LiveData<Resource<Comic>> =
            chaptersRepository.getComics()

    fun getComics() = comics()

    private fun comicDetail(comicId: Int): LiveData<Resource<ComicDetail>> =
            chaptersRepository.getComicDetail(comicId)

    fun getComicDetail(comicId: Int) = comicDetail(comicId)

}