package com.codebosses.comical.ui.main.profile

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.codebosses.comical.R
import com.codebosses.comical.di.base.Injectable
import com.codebosses.comical.repository.model.comicdetail.ComicDetailData
import com.codebosses.comical.ui.detail.FragmentAbout
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.extensions.observe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_comic_detail.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import javax.inject.Inject

class FragmentProfileComics : BaseFragment(), Injectable {

    //    View model fields....
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var profileViewModel: ProfileViewModel

    //    Instance fields...
    private var profileDataType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            profileDataType = it.getString(ARG_PROFILE_TYPE, "")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_comics, container, false)

        profileViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)

        if (profileDataType.isNotEmpty()) {
            when (profileDataType) {
                "favorite" -> {
                    getFavoriteComics()
                }
            }
        }

        return view
    }

    private fun getFavoriteComics() {
        profileViewModel.getFavoriteComics(PrefUtils.userId)
                .observe(this) {
                    when {
                        it.status.isLoading() -> {

                        }
                        it.status.isSuccessful() -> {

                        }
                        it.status.isError() -> {

                        }
                    }
                }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    companion object {
        const val ARG_PROFILE_TYPE = "arg_profile_type"

        @JvmStatic
        fun newInstance(profileType: String) =
                FragmentProfileComics().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PROFILE_TYPE, profileType)
                    }
                }
    }

}