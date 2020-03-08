package com.codebosses.comical.ui.intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.codebosses.comical.repository.model.intro.Intro
import com.codebosses.comical.repository.repo.intro.IntroRepository
import javax.inject.Inject

class IntroViewModel@Inject constructor(
    introRepository: IntroRepository
): ViewModel(){


    private var introList: LiveData<List<Intro>> = introRepository.getIntroData()

    fun getIntroList() = introList

}