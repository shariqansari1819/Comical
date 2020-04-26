package com.codebosses.comical.ui.main.comic

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.di.base.Injectable
import com.codebosses.comical.repository.eventbus.EventBusChapterClick
import com.codebosses.comical.repository.eventbus.EventBusRateComic
import com.codebosses.comical.repository.model.comics.Comic
import com.codebosses.comical.repository.model.comics.ComicResult
import com.codebosses.comical.ui.detail.ComicDetailActivity
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.utils.extensions.startActivity
import com.codebosses.comical.utils.extensions.visible
import com.codebosses.comical.utils.intentOpenWebsite
import com.codebosses.comical.utils.intentShareText
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.Display
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_comics.*
import kotlinx.android.synthetic.main.fragment_comics.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class FragmentComics : BaseFragment(), Injectable {

    companion object {
        fun getInstance() = FragmentComics()
    }

    //    View model fields....
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var chaptersViewModel: ComicsViewModel

    //    Adapter fields....
    private var chaptersList = ArrayList<ComicResult>()
    private lateinit var comicsAdapter: ComicsAdapter

    //    Instance fields....
    private var userId = 0
    private var selectedItemPosition = -1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comics, container, false)
        EventBus.getDefault().register(this)

        chaptersViewModel =
                ViewModelProviders.of(this, viewModelFactory).get(ComicsViewModel::class.java)

        setHasOptionsMenu(true)

//        Setting adapter....
        with(view.recyclerViewHome) {
            layoutManager = GridLayoutManager(activity!!, 2)
            comicsAdapter = ComicsAdapter(activity!!, chaptersList, "comic")
            adapter = comicsAdapter
        }
        userId = PrefUtils.userId
        getComics(userId)

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    private fun getComics(userId: Int) {
        chaptersViewModel.getComics(userId).observe(this) {
            when {
                it.status.isLoading() -> {
                    circularProgressBarHome.visible()
                }
                it.status.isSuccessful() -> {
                    circularProgressBarHome.gone()
                    val chapter = it.data as Comic
                    when (chapter.status) {
                        true -> {
                            chaptersList.addAll(chapter.result)
                            comicsAdapter.notifyItemRangeInserted(0, chaptersList.count())
                        }
                        else -> {
                            ToastUtil.showCustomToast(activity!!, "No chapter found!")
                        }
                    }
                }
                it.status.isError() -> {
                    circularProgressBarHome.gone()
                    ToastUtil.showCustomToast(activity!!, it.errorMessage.toString())
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_home, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.menuItemPrivacyPolicy -> {
//                intentOpenWebsite(activity!!, "https://codebosses.blogspot.com/p/privacy-policy.html")
//                return true
//            }
            R.id.menuItemShareApp -> {
                intentShareText(activity!!, "https://play.google.com/store/apps/details?id=com.codebosses.comical&hl=en")
                return true
            }
            R.id.menuItemCheckUpdate -> {
                AppUpdater(activity!!)
                        .setDisplay(Display.DIALOG)
                        .showAppUpdated(true)
                        .start()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusChapterClick(eventBusChapterClick: EventBusChapterClick) {
        selectedItemPosition = eventBusChapterClick.position
        val bundle = Bundle()
        bundle.putParcelable(
                Constants.IntentConstants.COMIC_CHAPTER,
                chaptersList[eventBusChapterClick.position]
        )
        activity!!.startActivity(ComicDetailActivity::class.java, bundle)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusComicRated(eventBusRateComic: EventBusRateComic) {
        chaptersList[selectedItemPosition].is_favorite = eventBusRateComic.isFavorite
        comicsAdapter.notifyItemChanged(selectedItemPosition)
    }

}