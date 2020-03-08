package com.codebosses.comical.ui.detail

import android.graphics.Typeface
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.repository.model.chapterdetail.ChapterData
import com.codebosses.comical.repository.model.chapters.ChapterResult
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.utils.extensions.visible
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.ui.main.chapters.ChaptersViewModel
import kotlinx.android.synthetic.main.activity_chapter_detail.*


class ChapterDetailActivity : BaseActivity() {

    //    View model field....
    private val chaptersViewModel by lazy {
        getViewModel<ChaptersViewModel>()
    }

    //    Instance fields....
    private lateinit var chapterResult: ChapterResult
    private lateinit var chapterData: ChapterData
    private lateinit var typeface: Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_detail)

        typeface = Typeface.createFromAsset(assets, "fonts/comic_bold.ttf");

        chapterResult = intent.getParcelableExtra(Constants.IntentConstants.COMIC_CHAPTER)!!

        if (chapterResult != null) {
            getChapterDetail(chapterResult.comic_id)
        }

    }

    private fun getChapterDetail(chapterId: Int) {
        chaptersViewModel.getChapterDetail(chapterId).observe(this) {
            when {
                it.status.isLoading() -> {
                    circularProgressBarChapterDetail.visible()
                }
                it.status.isSuccessful() -> {
                    circularProgressBarChapterDetail.gone()
                    chapterData = it.data!!.result.details
                    setChapterDetailData()
                    val chapterDetailPagerAdapter = ChapterDetailPagerAdapter(it.data!!.result, this, supportFragmentManager)
                    viewPagerComicDetail.adapter = chapterDetailPagerAdapter
                    viewPagerComicDetail.offscreenPageLimit = 2
                    tabLayoutComicDetail.setupWithViewPager(viewPagerComicDetail)
                    changeTabsFont()
                }
                it.status.isError() -> {
                    circularProgressBarChapterDetail.gone()
                    ToastUtil.showCustomToast(this, it.errorMessage.toString())
                }
            }
        }
    }

    private fun changeTabsFont() {
        val vg = tabLayoutComicDetail.getChildAt(0) as ViewGroup
        val tabsCount = vg.childCount
        for (j in 0 until tabsCount) {
            val vgTab = vg.getChildAt(j) as ViewGroup
            val tabChildsCount = vgTab.childCount
            for (i in 0 until tabChildsCount) {
                val tabViewChild = vgTab.getChildAt(i)
                if (tabViewChild is TextView) {
                    tabViewChild.typeface = typeface
                }
            }
        }
    }

    private fun setChapterDetailData() {
        Glide.with(this)
                .load(chapterData.comic_banner_path)
                .into(imageViewHeaderComicDetail)
        textViewTitleComicDetail.text = chapterData.comic_name
    }

}
