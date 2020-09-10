package com.mikeinvents.coronavirusupdate.adapter;

import com.mikeinvents.coronavirusupdate.fragment.CountriesFragment;
import com.mikeinvents.coronavirusupdate.fragment.GlobalFragment;
import com.mikeinvents.coronavirusupdate.fragment.NewsFeedFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PageAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public PageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        mNumOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new GlobalFragment();
            case 1:
                return new CountriesFragment();
            case 2:
                return new NewsFeedFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
