package com.codebosses.comical.ui.main.profile

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.codebosses.comical.R
import com.codebosses.comical.di.base.Injectable
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.ui.main.search.SearchViewModel
import com.codebosses.comical.ui.setting.SettingActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.extensions.startActivity
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import javax.inject.Inject


class FragmentProfile : BaseFragment() {

    companion object {
        fun getInstance() = FragmentProfile()
    }

    //    Instance fields....
    private lateinit var typeface: Typeface

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        setHasOptionsMenu(true)

        view.textViewNameProfile.text = PrefUtils.userName
        if(PrefUtils.profileStatus.isEmpty()){
            view.textViewDescription.text = "Crazy about comics, reading books, play with cats and dogs!"
        }else {
            view.textViewDescription.text = PrefUtils.profileStatus
        }

        typeface = Typeface.createFromAsset(activity!!.assets, "fonts/comic_bold.ttf");

        val profilePagerAdapter = ProfilePagerAdapter(childFragmentManager, activity!!)
        with(view.viewPagerProfile) {
            adapter = profilePagerAdapter
            view.tabLayoutProfile.setupWithViewPager(this)
            this.offscreenPageLimit = 2
            changeTabsFont(view)
        }


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.menuItemLogOut -> {
//                (activity as MainActivity).logOutUser()
//                return true
//            }
            R.id.menuItemSetting -> {
                activity?.startActivity(SettingActivity::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun changeTabsFont(view: View) {
        val vg = view.tabLayoutProfile.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.isAllCaps = false
                    tabViewChild.typeface = typeface
                }
            }
        }
    }

}
