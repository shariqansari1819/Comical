package com.codebosses.comical.ui.detail

import android.os.Bundle
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.repository.model.chapterdetail.Comics
import com.codebosses.comical.repository.model.comicdetail.ComicDetail
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.ui.BaseActivity
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import kotlinx.android.synthetic.main.activity_comic_read.*


class ComicReadActivity : BaseActivity() {

    //    View model field....
    private val readComicViewModel by lazy {
        getViewModel<ComicReadViewModel>()
    }

    //    Instance fields....
    private lateinit var comics: Comics
    private lateinit var comicDetail: ComicDetail
    private var comicImagesList: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_read)

        setSupportActionBar(toolbarReadComic)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        intent?.let {
            comics = it.getParcelableExtra(Constants.IntentConstants.COMIC)!!
            getComicDetail(comics.chapter_id)
            supportActionBar?.setTitle(comics.chapter_name)
        }

    }

    private fun getComicDetail(comicId: Int) {
        readComicViewModel.getComicDetail(comicId).observe(this) {
            when {
                it.status.isLoading() -> {
//                    circularProgressBarChapterDetail.visible()
                }
                it.status.isSuccessful() -> {
                    comicDetail = it.data!!
                    comicImagesList = comicDetail.result[0].images_url_array.split(",").map {
                        it.trim()
                    }
                    viewPager.let {
                        val adapter = ImagesPagerAdapter(this, comicImagesList)
                        it.adapter = adapter
                        val bookFlipPageTransformer = BookFlipPageTransformer()
                        bookFlipPageTransformer.isEnableScale = true
                        bookFlipPageTransformer.scaleAmountPercent = 10f
                        it.setPageTransformer(true, bookFlipPageTransformer)
                    }
                }
                it.status.isError() -> {
//                    circularProgressBarChapterDetail.gone()
                    ToastUtil.showCustomToast(this, it.errorMessage.toString())
                }
            }
        }
    }

}
