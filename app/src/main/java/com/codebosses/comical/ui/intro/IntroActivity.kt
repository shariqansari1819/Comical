package com.codebosses.comical.ui.intro

import android.os.Bundle
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.codebosses.comical.R
import com.codebosses.comical.di.base.Injectable
import com.codebosses.comical.repository.model.intro.Intro
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.utils.extensions.startActivityWithFinish
import com.codebosses.comical.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseActivity(), Injectable, ViewPager.OnPageChangeListener {

    //    View model field....
    private val introViewModel by lazy {
        getViewModel<IntroViewModel>()
    }

    //    Instance fields....
    private var introList:List<Intro> = arrayListOf()

    //    Adapter fields....
    private lateinit var introPagerAdapter: IntroPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        //        Setting adapter....
        introViewModel.getIntroList().observe(this){
            introList = it
            introPagerAdapter = IntroPagerAdapter(this, introList)
            viewPagerIntro.adapter = introPagerAdapter
        }

        changeImageSelection(imageViewCircleOneIntro, imageViewCircleTwoIntro)

        viewPagerIntro.addOnPageChangeListener(this)
        textViewSkipIntro.setOnClickListener {
            PrefUtils.firstRun = false
            startActivityWithFinish(MainActivity::class.java)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> {
                changeImageSelection(imageViewCircleOneIntro, imageViewCircleTwoIntro)
            }
            1 -> {
                changeImageSelection(imageViewCircleTwoIntro, imageViewCircleOneIntro)
            }
        }
    }

    private fun changeImageSelection(imageViewSelected: ImageView, imageViewDeselected: ImageView) {
        imageViewSelected.isSelected = true
        imageViewDeselected.isSelected = false
    }


}
