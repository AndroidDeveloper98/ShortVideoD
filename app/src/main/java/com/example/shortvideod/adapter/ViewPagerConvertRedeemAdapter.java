package com.example.shortvideod.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shortvideod.fragment.ConvertCoinFragment;
import com.example.shortvideod.fragment.RedeemFragment;

public class ViewPagerConvertRedeemAdapter extends FragmentPagerAdapter {

    public ViewPagerConvertRedeemAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new ConvertCoinFragment();
        } else if (position == 1) {
            fragment = new RedeemFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }


}

