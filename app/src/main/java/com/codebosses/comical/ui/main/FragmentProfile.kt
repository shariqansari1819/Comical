package com.codebosses.comical.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codebosses.comical.R
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.utils.PrefUtils
import kotlinx.android.synthetic.main.fragment_profile.view.*


class FragmentProfile : BaseFragment() {

    companion object {
        fun getInstance() = FragmentProfile()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.textViewNameProfile.text = PrefUtils.userName


        return view
    }


}
