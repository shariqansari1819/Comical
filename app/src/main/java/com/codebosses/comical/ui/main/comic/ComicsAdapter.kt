package com.codebosses.comical.ui.main.comic

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.repository.eventbus.EventBusChapterClick
import com.codebosses.comical.repository.model.comics.ComicResult
import kotlinx.android.synthetic.main.row_chapter.view.*
import org.greenrobot.eventbus.EventBus

class ComicsAdapter(val context: Context, val chapterList: List<ComicResult>): RecyclerView.Adapter<ComicsAdapter.ChaptersHolder>() {

    private var layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChaptersHolder {
        return ChaptersHolder(layoutInflater.inflate(R.layout.row_chapter,parent,false))
    }

    override fun getItemCount(): Int {
        return chapterList.count()
    }

    override fun onBindViewHolder(holder: ChaptersHolder, position: Int) {
        holder.bindData(chapterList[position])
    }

    inner class ChaptersHolder(val view: View) : RecyclerView.ViewHolder(view){

        fun bindData(chapterResult: ComicResult){
            Glide.with(context)
                .load(chapterResult.comic_poster_path)
                .placeholder(R.drawable.comic_placeholder)
                .centerCrop()
                .into(view.imageViewChapterRow)
            view.textViewNameChapterRow.text = chapterResult.comic_name

            view.setOnClickListener {
                EventBus.getDefault().post(EventBusChapterClick(adapterPosition))
            }
        }


    }
}