package com.codebosses.comical.ui.main.search

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.comic.ComicRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val chaptersRepository: ComicRepository) : ViewModel() {

    private fun search(searchText: String,userId: Int) = chaptersRepository.searchComic(searchText,userId)

    fun searchComic(searchText: String,userId: Int) = search(searchText,userId)

}