package com.codebosses.comical.ui.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codebosses.comical.R
import com.codebosses.comical.ui.main.base.BaseFragment


class FragmentSearch : BaseFragment() {

    companion object{
        fun getInstance() = FragmentSearch()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        return view
    }


}
