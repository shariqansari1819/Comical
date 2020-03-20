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
import androidx.recyclerview.widget.GridLayoutManager

import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.di.base.Injectable
import com.codebosses.comical.repository.eventbus.EventBusFavoriteClick
import com.codebosses.comical.repository.model.comicdetail.ComicDetailData
import com.codebosses.comical.repository.model.comics.ComicResult
import com.codebosses.comical.ui.detail.ComicDetailActivity
import com.codebosses.comical.ui.detail.FragmentAbout
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.ui.main.comic.ComicsAdapter
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.utils.extensions.startActivity
import com.codebosses.comical.utils.extensions.visible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_comic_detail.*
import kotlinx.android.synthetic.main.fragment_comics.view.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_comics.*
import kotlinx.android.synthetic.main.fragment_profile_comics.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class FragmentProfileComics : BaseFragment(), Injectable {

    //    View model fields....
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var profileViewModel: ProfileViewModel

    //    Instance fields...
    private var profileDataType: String = ""
    private var selectedItemPosition = 0

    //    Adapter fields....
    private var favoriteComicsList = ArrayList<ComicResult>()
    private lateinit var comicsAdapter: ComicsAdapter

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
        EventBus.getDefault().register(this)

        profileViewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel::class.java)

        with(view.recyclerViewProfileComics) {
            layoutManager = GridLayoutManager(activity!!, 2)
            comicsAdapter = ComicsAdapter(activity!!, favoriteComicsList, "favorite")
            adapter = comicsAdapter
        }

        if (profileDataType.isNotEmpty()) {
            when (profileDataType) {
                "favorite" -> {
                    getFavoriteComics()
                }
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    private fun getFavoriteComics() {
        profileViewModel.getFavoriteComics(PrefUtils.userId)
                .observe(this) {
                    when {
                        it.status.isLoading() -> {
                            circularProgressBarProfileComics.visible()
                        }
                        it.status.isSuccessful() -> {
                            circularProgressBarProfileComics.gone()
                            favoriteComicsList.addAll(it.data!!.favorite)
                            if (favoriteComicsList.isNotEmpty()) {
                                comicsAdapter.notifyItemRangeInserted(0, favoriteComicsList.count())
                            } else {

                            }
                        }
                        it.status.isError() -> {
                            circularProgressBarProfileComics.gone()
                            ToastUtil.showCustomToast(activity!!, it.errorMessage!!)
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusFavoriteComicClick(eventBusFavoriteClick: EventBusFavoriteClick) {
        selectedItemPosition = eventBusFavoriteClick.position
        val bundle = Bundle()
        bundle.putParcelable(
                Constants.IntentConstants.COMIC_CHAPTER,
                favoriteComicsList[eventBusFavoriteClick.position]
        )
        activity!!.startActivity(ComicDetailActivity::class.java, bundle)
    }

}