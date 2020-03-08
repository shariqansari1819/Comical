package com.codebosses.comical.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.codebosses.comical.R
import com.codebosses.comical.common.Constants
import com.codebosses.comical.repository.eventbus.EventBusComicClick
import com.codebosses.comical.repository.model.chapterdetail.Comics
import com.codebosses.comical.utils.extensions.gone
import com.codebosses.comical.utils.extensions.startActivity
import com.codebosses.comical.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_comics.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class FragmentComics : Fragment() {

    //    Instance fields....
    private var comicList = ArrayList<Comics>()
    private lateinit var comicsAdapter: ComicsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            comicList = it.getParcelableArrayList<Comics>(ARG_COMICS)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comics, container, false)
        EventBus.getDefault().register(this)

        if(comicList.size > 0) {
            view.textViewNoComicFound.gone()
            with(view.recyclerViewComics) {
                layoutManager = GridLayoutManager(activity!!, 2)
                comicsAdapter = ComicsAdapter(activity!!, comicList)
                adapter = comicsAdapter
            }
        }else{
            view.textViewNoComicFound.visible()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusComicClick(eventBusComicClick: EventBusComicClick) {
        val bundle = Bundle().apply {
            putParcelable(Constants.IntentConstants.COMIC,comicList[eventBusComicClick.position])
        }
        activity?.startActivity(ComicReadActivity::class.java,bundle)
    }

    companion object {
        const val ARG_COMICS = "arg_comics"

        @JvmStatic
        fun newInstance(comicList: ArrayList<Comics>) =
            FragmentComics().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_COMICS, comicList)
                }
            }
    }


}
