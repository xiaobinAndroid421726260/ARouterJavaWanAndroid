package com.dbz.system.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class SystemAdapter extends FragmentPagerAdapter {

    private final List<String> mTitles;
    private final List<Fragment> mFragment;

    public SystemAdapter(@NonNull FragmentManager fm, int behavior, List<String> titles, List<Fragment> fragments) {
        super(fm, behavior);
        this.mTitles = titles;
        this.mFragment = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment != null ? mFragment.size() : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}