package com.codebosses.comical.ui.main.search

import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.repo.chapters.ChaptersRepository
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val chaptersRepository: ChaptersRepository) : ViewModel() {

    private fun search(searchText: String) = chaptersRepository.searchComic(searchText)

    fun searchComic(searchText: String) = search(searchText)

}