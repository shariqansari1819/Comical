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
import kotlinx.android.synthetic.main.row_search_comic.view.*
import org.greenrobot.eventbus.EventBus

class SearchComicAdapter(val context: Context, val chapterList: List<ComicResult>) : RecyclerView.Adapter<SearchComicAdapter.SearchComicHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchComicHolder {
        return SearchComicHolder(layoutInflater.inflate(R.layout.row_search_comic, parent, false))
    }

    override fun getItemCount(): Int {
        return chapterList.count()
    }

    override fun onBindViewHolder(holder: SearchComicHolder, position: Int) {
        holder.bindData(chapterList[position])
    }

    inner class SearchComicHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(chapterResult: ComicResult) {
            Glide.with(context)
                    .load(chapterResult.comic_poster_path)
                    .centerCrop()
                    .placeholder(R.drawable.comic_search_placeholder)
                    .into(view.imageViewComicSearchRow)
            view.textViewNameSearchComicRow.text = chapterResult.comic_name

            view.setOnClickListener {
                EventBus.getDefault().post(EventBusSearchClick(adapterPosition))
            }
        }
    }

}