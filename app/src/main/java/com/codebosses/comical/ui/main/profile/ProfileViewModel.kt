package com.codebosses.comical.ui.main.profile

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.comic.ComicRepository
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
        private val comicRepository: ComicRepository
) : ViewModel() {

    private fun favorite(userId: Int) = comicRepository.getFavoriteComics(userId)

    fun getFavoriteComics(userId: Int) = favorite(userId)

}