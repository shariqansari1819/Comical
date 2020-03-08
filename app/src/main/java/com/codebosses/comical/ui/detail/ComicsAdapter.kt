package com.codebosses.comical.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.repository.eventbus.EventBusComicClick
import com.codebosses.comical.repository.model.chapterdetail.Comics
import kotlinx.android.synthetic.main.row_comic.view.*
import org.greenrobot.eventbus.EventBus

class ComicsAdapter(val context: Context, val comicsList: ArrayList<Comics>) :
    RecyclerView.Adapter<ComicsAdapter.ComicsHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsHolder {
        return ComicsHolder(layoutInflater.inflate(R.layout.row_comic, parent, false))
    }

    override fun getItemCount(): Int {
        return comicsList.count()
    }

    override fun onBindViewHolder(holder: ComicsHolder, position: Int) {
        holder.bindComic(comicsList[position])
    }


    inner class ComicsHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bindComic(comic: Comics) {
            Glide.with(context)
                .load(comic.poster_path)
                .placeholder(R.drawable.comic_placeholder)
                .centerCrop()
                .into(view.imageViewComicDetailRow)
            view.textViewNameComicDetailRow.text = comic.chapter_name

            view.setOnClickListener {
                EventBus.getDefault().post(EventBusComicClick(adapterPosition))
            }
        }

    }
}