package com.codebosses.comical.ui.main.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.repository.eventbus.EventBusSearchClick
import com.codebosses.comical.repository.model.comics.ComicResult
import com.codebosses.comical.repository.model.search.SearchResult
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.visible
import kotlinx.android.synthetic.main.row_search_comic.view.*
import org.greenrobot.eventbus.EventBus

class SearchComicAdapter(val context: Context, val searchList: List<SearchResult>) : RecyclerView.Adapter<SearchComicAdapter.SearchComicHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchComicHolder {
        return SearchComicHolder(layoutInflater.inflate(R.layout.row_search_comic, parent, false))
    }

    override fun getItemCount(): Int {
        return searchList.count()
    }

    override fun onBindViewHolder(holder: SearchComicHolder, position: Int) {
        holder.bindData(searchList[position])
    }

    inner class SearchComicHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(searchResult: SearchResult) {
            Glide.with(context)
                    .load(searchResult.comic_banner_path)
                    .centerCrop()
                    .placeholder(R.drawable.banner_placeholder)
                    .into(view.imageViewComicSearchRow)
            view.textViewNameSearchComicRow.text = searchResult.comic_name
            view.textViewDescriptionSearchComic.text = searchResult.summary
            if (searchResult.is_favorite == 0) {
                view.imageViewFavoriteSearchComic.gone()
            } else {
                view.imageViewFavoriteSearchComic.visible()
            }

            view.setOnClickListener {
                EventBus.getDefault().post(EventBusSearchClick(adapterPosition))
            }
        }
    }

}