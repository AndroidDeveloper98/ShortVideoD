package com.example.shortvideod.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shortvideod.fragment.ChatFragment;
import com.example.shortvideod.fragment.UserActivityFragment;

public class ViewPagerChatAdapter extends FragmentPagerAdapter {

    public ViewPagerChatAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new UserActivityFragment();
        } else if (position == 1) {
            fragment = new ChatFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0) {
            title = "User";
        } else if (position == 1) {
            title = "Hashtag";
        }
        return title;
    }
}

