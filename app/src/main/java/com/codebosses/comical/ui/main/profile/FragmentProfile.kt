package com.codebosses.comical.ui.main.profile

import android.os.Bundle
import android.view.*
import com.codebosses.comical.R
import com.codebosses.comical.ui.main.MainActivity
import com.codebosses.comical.ui.main.base.BaseFragment
import com.codebosses.comical.utils.PrefUtils
import kotlinx.android.synthetic.main.fragment_profile.view.*


class FragmentProfile : BaseFragment() {

    companion object {
        fun getInstance() = FragmentProfile()
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        setHasOptionsMenu(true)

        view.textViewNameProfile.text = PrefUtils.userName


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemLogOut -> {
                (activity as MainActivity).logOutUser()
            }
//            R.id.menuItemSetting -> {
//
//            }
        }
        return super.onOptionsItemSelected(item)
    }

}
