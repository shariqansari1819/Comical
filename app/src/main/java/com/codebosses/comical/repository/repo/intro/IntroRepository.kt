package com.codebosses.comical.repository.repo.intro

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.codebosses.comical.R
import com.codebosses.comical.repository.model.intro.Intro
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntroRepository@Inject constructor(
    private val context: Context
)  {

    private val introListLiveData = MutableLiveData<List<Intro>>()

    fun getIntroData() : LiveData<List<Intro>>{
        val list = arrayListOf<Intro>()

        list.add(Intro(context.resources.getString(R.string.intro_one_heading), context.resources.getString(R.string.intro_one_description), R.drawable.icon_intro_one))
        list.add(Intro(context.resources.getString(R.string.intro_two_heading), context.resources.getString(R.string.intro_two_description), R.drawable.icon_intro_two))

        introListLiveData.value = list

        return introListLiveData
    }
}