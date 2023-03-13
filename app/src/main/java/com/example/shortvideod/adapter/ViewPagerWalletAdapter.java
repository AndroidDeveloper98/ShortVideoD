package com.example.shortvideod.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shortvideod.fragment.MyCoinFragment;
import com.example.shortvideod.fragment.MyIncomeFragment;

public class ViewPagerWalletAdapter extends FragmentPagerAdapter {

    public ViewPagerWalletAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new MyCoinFragment();
        } else if (position == 1) {
            fragment = new MyIncomeFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }


}

