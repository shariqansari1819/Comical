package com.codebosses.comical.ui.detail

import android.os.Bundle
import android.view.MenuItem
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.repository.model.comicdetail.Chapter
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.visible
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import kotlinx.android.synthetic.main.activity_chapter_read.*


class ChapterReadActivity : BaseActivity() {

    //    View model field....
    private val readComicViewModel by lazy {
        getViewModel<ChapterReadViewModel>()
    }

    //    Android fields....
//    private lateinit var sweetAlertDialog: SweetAlertDialog

    //    Instance fields....
    private lateinit var comics: Chapter
    private lateinit var comicDetail: ChapterDetail
    private var comicImagesList: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_read)

        setSupportActionBar(toolbarReadComic)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        //        Dialog initialization....
//        sweetAlertDialog = SweetAlertDialog((this))
//        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
//        sweetAlertDialog.titleText = "Please wait..."
//        sweetAlertDialog.setCancelable(false)

        intent?.let {
            comics = it.getParcelableExtra(Constants.IntentConstants.COMIC)!!
            getComicDetail(comics.chapter_id)
            supportActionBar?.setTitle(comics.chapter_name)
        }

    }

    private fun getComicDetail(chapterId: Int) {
        readComicViewModel.getChapterDetail(chapterId).observe(this) {
            when {
                it.status.isLoading() -> {
//                    sweetAlertDialog.show()
                    circularProgressBarReadChapter.visible()
                }
                it.status.isSuccessful() -> {
//                    sweetAlertDialog.dismiss()
                    circularProgressBarReadChapter.gone()
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
//                    sweetAlertDialog.dismiss()
                    circularProgressBarReadChapter.gone()
                    ToastUtil.showCustomToast(this, it.errorMessage.toString())
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
