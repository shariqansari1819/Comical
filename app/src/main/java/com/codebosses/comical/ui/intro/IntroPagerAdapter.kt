package com.codebosses.comical.ui.intro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.repository.model.intro.Intro
import kotlinx.android.synthetic.main.pager_item_intro.view.*

class IntroPagerAdapter(var context: Context, var listOfIntroPager: List<Intro>) : PagerAdapter() {

    private val layoutInflater:LayoutInflater = LayoutInflater.from(context)

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view == `object`

    override fun getCount(): Int =
        listOfIntroPager.count()

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.pager_item_intro,container,false)

        val imageView = view.imageViewPagerItemIntro
        val textViewHeading = view.textViewHeadingPagerItemIntro
        val textViewDescription = view.textViewDescriptionPagerItemIntro

        Glide.with(context)
            .load(listOfIntroPager[position].image)
            .into(imageView)
        textViewHeading.text = listOfIntroPager[position].title
        textViewDescription.text = listOfIntroPager[position].description

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}