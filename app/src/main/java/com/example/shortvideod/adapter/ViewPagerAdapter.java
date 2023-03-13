package com.example.shortvideod.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shortvideod.fragment.SearchHashtagFragment;
import com.example.shortvideod.fragment.SearchUserFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int pos;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new SearchUserFragment();
        } else if (position == 1) {
            fragment = new SearchHashtagFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        this.pos = position;
        String title = null;
        if (position == 0) {
            title = "User";
        } else if (position == 1) {
            title = "Hashtag";
        }
        return title;
    }

    public int getPosition() {
        return pos;
    }


}

