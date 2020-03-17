package com.codebosses.comical.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.codebosses.comical.R
import com.codebosses.comical.repository.model.comicdetail.ComicDetailData
import com.codebosses.comical.ui.registration.login.LoginActivity
import com.codebosses.comical.utils.PrefUtils
import com.codebosses.comical.utils.ToastUtil
import com.codebosses.comical.utils.extensions.startActivity
import com.codebosses.comical.utils.intentShareText
import com.hsalf.smilerating.BaseRating
import kotlinx.android.synthetic.main.dialog_comic_rating.view.*
import kotlinx.android.synthetic.main.fragment_about.view.*

class FragmentAbout : Fragment(), View.OnClickListener {

    private lateinit var chapterData: ComicDetailData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            chapterData = it.getParcelable(ARG_CHAPTER_DETAIL)!!
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        view.ratingBarAbout.rating = chapterData.rating.toFloat()
        view.textViewPublisher.text = chapterData.category
        view.textViewFirstAppearance.text = chapterData.comic_release_date.toString();
        view.textViewDescription.text = chapterData.summary
        view.textViewAuthor.text = chapterData.author
        view.textViewStatus.text = chapterData.comic_status
        view.textViewViewsAbout.text = "${chapterData.views} views"

//        All Listeners....
        view.imageViewShareAbout.setOnClickListener(this)
        view.imageViewRateAbout.setOnClickListener(this)

        return view
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.imageViewShareAbout -> {
                intentShareText(activity!!, "https://play.google.com/store/apps/details?id=com.codebosses.comical&hl=en")
            }
            R.id.imageViewRateAbout -> {
                if (PrefUtils.isUserLoggedIn) {
                    showRatingDialog()
                } else {
                    activity?.startActivity(LoginActivity::class.java)
                }
            }
        }
    }

    private fun showRatingDialog() {
        var rating = 0
        val view = LayoutInflater.from(activity!!).inflate(R.layout.dialog_comic_rating, null)
        val dialog = AlertDialog.Builder(activity!!)
                .setView(view)
                .create()
        dialog.show()
        view.textViewComicNameComicRatingDialog.text = chapterData.comic_name
        view.buttonRateComicRatingDialog.setOnClickListener {
            if (rating != 0) {
                (activity as ComicDetailActivity).rateComic(rating)
                dialog.dismiss()
            } else
                ToastUtil.showCustomToast(activity!!, resources.getString(R.string.select_your_mood))
        }
        view.ratingViewComicRatingDialog.setOnRatingSelectedListener { level, reselected ->
            rating = level
        }
        view.ratingViewComicRatingDialog.selectedSmile = BaseRating.OKAY

    }

    companion object {
        const val ARG_CHAPTER_DETAIL = "arg_chapter_detail"

        @JvmStatic
        fun newInstance(chapterData: ComicDetailData) =
                FragmentAbout().apply {
                    arguments = Bundle().apply {
                        putParcelable(ARG_CHAPTER_DETAIL, chapterData)
                    }
                }
    }


}