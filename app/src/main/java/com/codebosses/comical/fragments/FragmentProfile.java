package com.codebosses.comical.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.fragments.base.BaseFragment;
import com.google.firebase.auth.FirebaseAuth;

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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        view.findViewById(R.id.buttonLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        return view;
    }

}
