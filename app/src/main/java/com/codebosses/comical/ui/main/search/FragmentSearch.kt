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
import com.codebosses.comical.R
import com.codebosses.comical.di.base.Injectable
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.ui.main.chapters.ChaptersViewModel
import com.codebosses.comical.utils.extensions.observe
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_search.view.*
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

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)

//        Listeners....
        view.editTextSearch.addTextChangedListener(this)
        return view
    }

    private val input_finish_checker = Runnable {
        if (System.currentTimeMillis() > last_text_edit + delay - 500) { // TODO: do what you need here

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

                }
                it.status.isError() -> {

                }
                it.status.isSuccessful() -> {

                }
            }
        }
    }

}
