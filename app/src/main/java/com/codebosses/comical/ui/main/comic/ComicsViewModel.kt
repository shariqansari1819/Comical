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


    private fun comics(userId: Int): LiveData<Resource<Comic>> =
            chaptersRepository.getComics(userId)

    fun getComics(userId: Int) = comics(userId)

    private fun comicDetail(comicId: Int, userId: Int): LiveData<Resource<ComicDetail>> =
            chaptersRepository.getComicDetail(comicId, userId)

    fun getComicDetail(comicId: Int, userId: Int) = comicDetail(comicId, userId)

    private fun favUnFavComic(comicId: Int, userId: Int) = chaptersRepository.favoriteUnFavoriteComic(comicId, userId)

    fun favoriteUnFavoriteComic(comicId: Int, userId: Int) = favUnFavComic(comicId, userId)

    private fun comicRating(comicId: Int, userId: Int, rating: Int) = chaptersRepository.comicRating(comicId, userId, rating)

    fun rateComic(comicId: Int, userId: Int, rating: Int) = comicRating(comicId, userId, rating)

}