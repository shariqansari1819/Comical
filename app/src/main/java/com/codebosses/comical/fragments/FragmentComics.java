package com.codebosses.comical.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.FragmentComicsBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.ComicGroup;

public class FragmentComics extends Fragment {

    private FragmentComicsBinding comicsBinding;

    public static FragmentComics getInstance(ComicGroup comicGroup) {
        FragmentComics fragmentComics = new FragmentComics();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EndpointKeys.COMIC, comicGroup);
        fragmentComics.setArguments(bundle);
        return fragmentComics;
    }

    public FragmentComics() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        comicsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_comics, container, false);

        return comicsBinding.getRoot();
    }

}
