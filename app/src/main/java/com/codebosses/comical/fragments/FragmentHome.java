package com.codebosses.comical.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.activities.ComicDetailActivity;
import com.codebosses.comical.adapters.ComicsHomeAdapter;
import com.codebosses.comical.databinding.FragmentHomeBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.fragments.base.BaseFragment;
import com.codebosses.comical.pojo.Comics;
import com.codebosses.comical.pojo.event_bus.EventBusAdapterClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends BaseFragment {

    private static FragmentHome fragmentHome;

    //    Android fields....
    private FragmentHomeBinding homeBinding;

    //    Adapter fields....
    private List<Comics> comicsList = new ArrayList<>();
    private ComicsHomeAdapter comicsHomeAdapter;

    public static FragmentHome getInstance() {
        if (fragmentHome == null) {
            fragmentHome = new FragmentHome();
        }
        return fragmentHome;
    }

    public FragmentHome() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        comicsHomeAdapter = new ComicsHomeAdapter(getActivity(), comicsList);
        homeBinding.recyclerViewHome.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeBinding.recyclerViewHome.setAdapter(comicsHomeAdapter);

        return homeBinding.getRoot();
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
    public void onComicClick(EventBusAdapterClick eventBusAdapterClick) {
        if (eventBusAdapterClick.getClickType().equals(EndpointKeys.HOME_COMIC_CLICK)) {
            Intent intent = new Intent(getActivity(), ComicDetailActivity.class);
            intent.putExtra(EndpointKeys.COMIC, comicsList.get(eventBusAdapterClick.getPosition()));
            startActivity(intent);
        }
    }
}