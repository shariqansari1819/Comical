package com.codebosses.comical.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codebosses.comical.R
import com.codebosses.comical.repository.model.chapterdetail.ChapterData
import kotlinx.android.synthetic.main.fragment_about.view.*


class FragmentAbout : Fragment() {

    private lateinit var chapterData: ChapterData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            chapterData = it.getParcelable<ChapterData>(ARG_CHAPTER_DETAIL)!!
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
        return view
    }

    companion object {
        const val ARG_CHAPTER_DETAIL = "arg_chapter_detail"

        @JvmStatic
        fun newInstance(chapterData: ChapterData) =
            FragmentAbout().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CHAPTER_DETAIL, chapterData)
                }
            }
    }


}
