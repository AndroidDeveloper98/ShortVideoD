package com.example.shortvideod.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shortvideod.fragment.UserFolowersFrgament;

public class ViewPagerUserFollowersAdapter extends FragmentPagerAdapter {


    public ViewPagerUserFollowersAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new UserFolowersFrgament(position);
    }


    @Override
    public int getCount() {
        return 2;
    }


}
