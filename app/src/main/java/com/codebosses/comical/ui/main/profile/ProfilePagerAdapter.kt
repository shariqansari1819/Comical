package com.codebosses.comical.ui.main.profile

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.codebosses.comical.R

class ProfilePagerAdapter(fm: FragmentManager, val context: Context) :
        FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return FragmentProfileComics.newInstance("reading")
            1 -> return FragmentProfileComics.newInstance("read")
            2 -> return FragmentProfileComics.newInstance("favorite")
            else -> return FragmentProfileComics()
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getStringArray(R.array.profile_pager_title)[position]
    }

}