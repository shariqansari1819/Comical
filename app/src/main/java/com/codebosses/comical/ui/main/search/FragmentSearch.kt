package com.codebosses.comical.ui.main.search

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.di.base.Injectable
import com.codebosses.comical.repository.eventbus.EventBusSearchClick
import com.codebosses.comical.repository.model.comics.ComicResult
import com.codebosses.comical.ui.detail.ComicDetailActivity
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.utils.extensions.startActivity
import com.codebosses.comical.utils.extensions.visible
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class FragmentSearch : BaseFragment(), Injectable, TextWatcher {

    companion object {
        fun getInstance() = FragmentSearch()
    }

    //    View model fields....
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var searchViewModel: SearchViewModel

    var delay: Long = 1000 // 1 seconds after user stops typing
    var last_text_edit: Long = 0
    var handler: Handler = Handler()

    //    Instance fields....
    private var chapterList = ArrayList<ComicResult>()
    private lateinit var searchComicAdapter: SearchComicAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        EventBus.getDefault().register(this)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

        with(view.recyclerViewSearch) {
            layoutManager = LinearLayoutManager(activity!!)
            searchComicAdapter = SearchComicAdapter(activity!!, chapterList)
            adapter = searchComicAdapter
        }

//        Listeners....
        view.editTextSearch.addTextChangedListener(this)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    private val input_finish_checker = Runnable {
        if (System.currentTimeMillis() > last_text_edit + delay - 500) { // TODO: do what you need here
            searchComic(editTextSearch.text.toString())
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (s!!.isNotEmpty()) {
            last_text_edit = System.currentTimeMillis()
            handler.postDelayed(input_finish_checker, delay)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        handler.removeCallbacks(input_finish_checker)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    private fun searchComic(searchText: String) {
        searchViewModel.searchComic(searchText).observe(this) {
            when {
                it.status.isLoading() -> {
                    circularProgressBarSearch.visible()
                }
                it.status.isError() -> {
                    circularProgressBarSearch.gone()
                    ToastUtil.showCustomToast(activity!!, it.errorMessage!!)
                }
                it.status.isSuccessful() -> {
                    circularProgressBarSearch.gone()
                    searchComicAdapter.notifyItemRangeRemoved(0, chapterList.count())
                    chapterList.clear()
                    chapterList.addAll(it.data!!.result)
                    searchComicAdapter.notifyItemRangeInserted(0, chapterList.count())
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusSearchClick(eventBusSearchClick: EventBusSearchClick) {
        val bundle = Bundle()
        bundle.putParcelable(
                Constants.IntentConstants.COMIC_CHAPTER,
                chapterList[eventBusSearchClick.position]
        )
        activity!!.startActivity(ComicDetailActivity::class.java, bundle)
    }

}
