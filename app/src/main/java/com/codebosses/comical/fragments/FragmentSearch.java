package com.codebosses.comical.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.activities.ComicDetailActivity;
import com.codebosses.comical.adapters.SearchComicAdapter;
import com.codebosses.comical.databinding.FragmentSearchBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.fragments.base.BaseFragment;
import com.codebosses.comical.pojo.Comic;
import com.codebosses.comical.pojo.event_bus.EventBusAdapterClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FragmentSearch extends BaseFragment {

    private static FragmentSearch fragmentSearch;

    //    Android fields....
    private FragmentSearchBinding searchBinding;

    //    Adapter fields....
    private SearchComicAdapter searchComicAdapter;
    private List<Comic> comicsList = new ArrayList<>();

    public static FragmentSearch getInstance() {
        if (fragmentSearch == null) {
            fragmentSearch = new FragmentSearch();
        }
        return fragmentSearch;
    }

    public FragmentSearch() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        searchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        searchComicAdapter = new SearchComicAdapter(getActivity(), comicsList);
        searchBinding.recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        searchBinding.recyclerViewSearch.setAdapter(searchComicAdapter);

        return searchBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSearchComicClick(EventBusAdapterClick eventBusAdapterClick) {
        if (eventBusAdapterClick.getClickType().equals(EndpointKeys.SEARCH_COMIC_CLICK)) {
            Intent intent = new Intent(getActivity(), ComicDetailActivity.class);
//            intent.putExtra(EndpointKeys.COMIC, comicsList.get(eventBusAdapterClick.getPosition()));
            startActivity(intent);
        }
    }
}