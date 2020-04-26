package com.codebosses.comical.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.repository.eventbus.EventBusComicClick
import com.codebosses.comical.repository.eventbus.EventBusReadClick
import com.codebosses.comical.repository.eventbus.EventBusReadingClick
import com.codebosses.comical.repository.model.comicdetail.Chapter
import kotlinx.android.synthetic.main.row_chapter.view.*
import org.greenrobot.eventbus.EventBus

class ChaptersAdapter(val context: Context, val comicsList: ArrayList<Chapter>, val chapterType: String = "") :
        RecyclerView.Adapter<ChaptersAdapter.ComicsHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsHolder {
        return ComicsHolder(layoutInflater.inflate(R.layout.row_chapter, parent, false))
    }

    override fun getItemCount(): Int {
        return comicsList.count()
    }

    override fun onBindViewHolder(holder: ComicsHolder, position: Int) {
        holder.bindComic(comicsList[position])
    }

    inner class ComicsHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindComic(comic: Chapter) {
            Glide.with(context)
                    .load(comic.poster_path)
                    .placeholder(R.drawable.poster_path_placeholder)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .into(view.imageViewComicDetailRow)
            view.textViewNameComicDetailRow.text = comic.chapter_name

            view.setOnClickListener {
                if(chapterType == "reading"){
                    EventBus.getDefault().post(EventBusReadingClick(adapterPosition))
                }else if(chapterType == "read"){
                    EventBus.getDefault().post(EventBusReadClick(adapterPosition))
                }else {
                    EventBus.getDefault().post(EventBusComicClick(adapterPosition))
                }
            }
        }

    }
}