package com.codebosses.comical.ui.detail

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.codebosses.comical.R
import com.codebosses.comical.repository.model.comicdetail.ComicDetailResult

class ComicDetailPagerAdapter(val chapterDetailResult: ComicDetailResult, val context: Context, fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
            when (position) {
                0 -> FragmentAbout.newInstance(chapterDetailResult.details)
                1 -> FragmentChapters.newInstance(chapterDetailResult.chapters)
                2 -> FragmentMovies()
                else -> FragmentAbout()
            }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getStringArray(R.array.comic_detail_pager_title)[position]
    }

}