package com.codebosses.comical.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.codebosses.comical.R;
import com.codebosses.comical.fragments.FragmentAbout;
import com.codebosses.comical.fragments.FragmentComics;
import com.codebosses.comical.fragments.FragmentMovies;

public class ComicDetailsPagerAdapter extends FragmentStatePagerAdapter {

    private Context context;

    public ComicDetailsPagerAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentAbout();
            case 1:
                return new FragmentComics();
            case 2:
                return new FragmentMovies();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getResources().getStringArray(R.array.comic_detail_pager_title)[position];
    }
}
