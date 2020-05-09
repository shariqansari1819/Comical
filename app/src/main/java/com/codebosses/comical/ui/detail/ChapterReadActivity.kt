package com.codebosses.comical.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.repository.model.comicdetail.Chapter
import com.codebosses.comical.repository.model.chapterdetail.ChapterDetail
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.getViewModel
import com.codebosses.comical.utils.extensions.observe
import com.codebosses.comical.ui.BaseActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.visible
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer
import kotlinx.android.synthetic.main.activity_chapter_read.*
import kotlinx.android.synthetic.main.dialog_comic_read_complete.view.*


class ChapterReadActivity : BaseActivity() {

    //    View model field....
    private val readComicViewModel by lazy {
        getViewModel<ChapterReadViewModel>()
    }

    //    Instance fields....
    private lateinit var comics: Chapter
    private lateinit var comicDetail: ChapterDetail
    private var comicImagesList: List<String> = ArrayList()
    private var isComicRead: Boolean = false

    //    Android fields....
    private lateinit var sweetAlertDialog: SweetAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter_read)

        setSupportActionBar(toolbarReadComic)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        sweetAlertDialog = SweetAlertDialog(this)
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
        sweetAlertDialog.titleText = "Please wait...."
        sweetAlertDialog.setCancelable(false)

        intent?.let {
            comics = it.getParcelableExtra(Constants.IntentConstants.COMIC)!!
            getComicDetail(comics.chapter_id, PrefUtils.userId)
            supportActionBar?.setTitle(comics.chapter_name)
        }

    }

    private fun getComicDetail(chapterId: Int, userId: Int) {
        readComicViewModel.getChapterDetail(chapterId, userId).observe(this) {
            when {
                it.status.isLoading() -> {
                    circularProgressBarReadChapter.visible()
                }
                it.status.isSuccessful() -> {
                    circularProgressBarReadChapter.gone()
                    comicDetail = it.data!!
                    comicImagesList = comicDetail.result[0].images_url_array.split(",").map {
                        it.trim()
                    }
                    if (PrefUtils.isUserLoggedIn) {
                        if (comicDetail.result[0].is_reading == 0 && comicDetail.result[0].is_read == 0) {
                            addReadingChapter(comicDetail.result[0].comic_id, comicDetail.result[0].chapter_id, PrefUtils.userId)
                        }
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
                    circularProgressBarReadChapter.gone()
                    ToastUtil.showCustomToast(this, it.errorMessage.toString())
                }
            }
        }
    }

    private fun addReadingChapter(comicId: Int, chapterId: Int, userId: Int) {
        readComicViewModel.addReadingChapter(comicId, chapterId, userId)
                .observe(this) {
                    when {
                        it.status.isLoading() -> {
                        }
                        it.status.isSuccessful() -> {
                            comicDetail.result[0].is_reading = 1
                        }
                        it.status.isError() -> {
                            ToastUtil.showCustomToast(this, it.errorMessage.toString())
                        }
                    }
                }
    }

    private fun addReadChapter(comicId: Int, chapterId: Int, userId: Int) {
        readComicViewModel.addReadChapter(comicId, chapterId, userId)
                .observe(this) {
                    when {
                        it.status.isLoading() -> {
                            sweetAlertDialog.show()
                        }
                        it.status.isSuccessful() -> {
                            sweetAlertDialog.dismiss()
                            isComicRead = true
                            ToastUtil.showCustomToast(this, "Chapter added in your read list.")
                            onBackPressed()
                        }
                        it.status.isError() -> {
                            isComicRead = true
                            sweetAlertDialog.dismiss()
                            ToastUtil.showCustomToast(this, it.errorMessage.toString())
                            onBackPressed()
                        }
                    }
                }
    }

    override fun onBackPressed() {
        if (PrefUtils.isUserLoggedIn && comicImagesList.isNotEmpty() && !isComicRead &&
                comicDetail.result[0].is_read == 0) {
            if (viewPager.currentItem == comicImagesList.size - 1) {
                val view = LayoutInflater.from(this).inflate(R.layout.dialog_comic_read_complete, null)
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setView(view)
                view.textViewChapterNameComicReadDialog.text = comics.chapter_name
                val dialog = dialogBuilder.create()
                view.buttonReadComicDialog.setOnClickListener {
                    if (comicDetail != null) {
                        addReadChapter(
                                comicDetail.result[0].comic_id,
                                comicDetail.result[0].chapter_id,
                                PrefUtils.userId)
                    }
                }
                dialog.setOnDismissListener {
                    isComicRead = true
                }
                dialog.show()
            } else {
                isComicRead = true
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
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
