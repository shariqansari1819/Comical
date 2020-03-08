package com.codebosses.comical.ui.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.codebosses.comical.R
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.visible
import kotlinx.android.synthetic.main.row_image.view.*

class ImagesPagerAdapter(val context: Context, val imagesList: List<String>) : PagerAdapter() {

    private val layoutInflater = LayoutInflater.from(context)

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return imagesList.count()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.row_image,container,false)

        view.circularProgressReadComic.visible()
        Glide.with(context)
            .load(imagesList[position])
            .listener(object : RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    view.circularProgressReadComic.gone()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    view.circularProgressReadComic.gone()
                    return false
                }

            })
            .into(view.imageViewReadComic)

        container.addView(view)
        return view;
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}