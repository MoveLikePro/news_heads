package com.enigmaticdevs.newsheads.activity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragment = new ArrayList<>();
    private final List<String> title = new ArrayList<>();
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
    void AddFragment(Fragment fragment, String titles){
        mFragment.add(fragment);
        title.add(titles);

    }
}
