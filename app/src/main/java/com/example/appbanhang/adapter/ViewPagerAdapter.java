package com.example.appbanhang.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appbanhang.fragment.FragmentHistory;
import com.example.appbanhang.fragment.FragmentHome;
import com.example.appbanhang.fragment.FragmentSearch;
import com.example.appbanhang.fragment.FragmentStatic;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new FragmentHome();
            case 1: return new FragmentHistory();
            case 2: return new FragmentSearch();
            case 3: return new FragmentStatic();
            default: return new FragmentHome();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
