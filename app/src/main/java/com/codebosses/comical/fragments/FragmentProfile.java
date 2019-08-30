package com.codebosses.comical.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.fragments.base.BaseFragment;

public class FragmentProfile extends BaseFragment {

    private static FragmentProfile fragmentProfile;

    public static FragmentProfile getInstance() {
        if (fragmentProfile == null) {
            fragmentProfile = new FragmentProfile();
        }
        return fragmentProfile;
    }

    public FragmentProfile() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

}
