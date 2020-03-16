package com.codebosses.comical.ui.detail

import android.graphics.Typeface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.repository.eventbus.EventBusRateComic
import com.codebosses.comical.repository.model.comicdetail.ComicDetailData
import com.codebosses.comical.repository.model.comics.ComicResult
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.ui.main.comic.ComicsViewModel
import com.codebosses.comical.ui.registration.login.LoginActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.extensions.*
import kotlinx.android.synthetic.main.activity_comic_detail.*
import kotlinx.android.synthetic.main.activity_comic_detail.view.*
import org.greenrobot.eventbus.EventBus


class ComicDetailActivity : BaseActivity(), View.OnClickListener {

    //    View model field....
    private val chaptersViewModel by lazy {
        getViewModel<ComicsViewModel>()
    }

    //    Instance fields....
    private lateinit var comicResult: ComicResult
    private lateinit var comicData: ComicDetailData
    private lateinit var typeface: Typeface
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_detail)

        typeface = Typeface.createFromAsset(assets, "fonts/comic_bold.ttf");

        comicResult = intent.getParcelableExtra(Constants.IntentConstants.COMIC_CHAPTER)!!

        setSupportActionBar(toolbarComicDetail as Toolbar)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }

        if (comicResult != null) {
            userId = PrefUtils.userId
            setFavoriteUnFavorite()
            getComicDetail(comicResult.comic_id, userId)
        }

//        All listeners....
        imageViewFavoriteComicDetail.setOnClickListener(this)

    }

    private fun setFavoriteUnFavorite() {
        if (comicResult.is_favorite == 0) {
            imageViewFavoriteComicDetail.setImageResource(R.drawable.ic_action_stroke_heart)
        } else {
            imageViewFavoriteComicDetail.setImageResource(R.drawable.ic_action_fill_heart)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageViewFavoriteComicDetail -> {
                if (PrefUtils.isUserLoggedIn) {
                    favoriteUnFavoriteComic(comicResult.comic_id, PrefUtils.userId)
                } else {
                    startActivity(LoginActivity::class.java)
                }
            }
        }
    }

    private fun favoriteUnFavoriteComic(comicId: Int, userId: Int) {
        chaptersViewModel.favoriteUnFavoriteComic(comicId, userId)
                .observe(this) {
                    when {
                        it.status.isLoading() -> {

                        }
                        it.status.isSuccessful() -> {
                            if (comicResult.is_favorite == 0) {
                                comicResult.is_favorite = 1
                            } else {
                                comicResult.is_favorite = 0
                            }
                            setFavoriteUnFavorite()
                            EventBus.getDefault().post(EventBusRateComic(comicResult.comic_id, comicResult.is_favorite))
                        }
                        it.status.isError() -> {

                        }
                    }
                }
    }

    private fun getComicDetail(comicId: Int, userId: Int) {
        chaptersViewModel.getComicDetail(comicId, userId).observe(this) {
            when {
                it.status.isLoading() -> {
                    circularProgressBarChapterDetail.visible()
                }
                it.status.isSuccessful() -> {
                    circularProgressBarChapterDetail.gone()
                    comicData = it.data!!.result.details
                    setChapterDetailData()
                    val chapterDetailPagerAdapter = ComicDetailPagerAdapter(it.data!!.result, this, supportFragmentManager)
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

    fun rateComic(rating: Int) {
        chaptersViewModel.rateComic(comicResult.comic_id, PrefUtils.userId, rating)
                .observe(this) {
                    when {
                        it.status.isSuccessful() -> {
                            ToastUtil.showCustomToast(this, resources.getString(R.string.rating_saved))
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
                    tabViewChild.isAllCaps = false
                    tabViewChild.typeface = typeface
                }
            }
        }
    }

    private fun setChapterDetailData() {
        Glide.with(this)
                .load(comicData.comic_banner_path)
                .placeholder(R.drawable.banner_placeholder)
                .into(imageViewHeaderComicDetail)
        textViewTitleComicDetail.text = comicData.comic_name
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
