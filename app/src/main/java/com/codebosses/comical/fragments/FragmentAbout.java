package com.codebosses.comical.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.FragmentAboutBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.Comic;

public class FragmentAbout extends Fragment {

    //    Android fields....
    private FragmentAboutBinding aboutBinding;

    public static FragmentAbout getInstance(Comic comicGroup) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(EndpointKeys.COMIC, comicGroup);
        FragmentAbout fragmentAbout = new FragmentAbout();
        fragmentAbout.setArguments(bundle);
        return fragmentAbout;
    }

    public FragmentAbout() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        aboutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);

        if (getArguments() != null) {
            Comic comicGroup = getArguments().getParcelable(EndpointKeys.COMIC);
            if (comicGroup != null) {
                aboutBinding.setComic(comicGroup);
            }
        }

        return aboutBinding.getRoot();
    }

}