package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.ViewPagerChatAdapter;
import com.example.shortvideod.databinding.FragmentChatActivityBinding;
import com.google.android.material.tabs.TabLayout;

public class ChatActivityFragment extends Fragment {
    FragmentChatActivityBinding binding;

    public ChatActivityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat_activity, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        setTabLyt();
        settab();
    }

    private void setTabLyt() {
        if (getActivity() != null) {
            binding.tablayout.setupWithViewPager(binding.pager);
            binding.pager.setAdapter(new ViewPagerChatAdapter(getChildFragmentManager()));

            binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    View v = tab.getCustomView();
                    if (v != null) {
                        View tv = (View) v.findViewById(R.id.view2);
                        TextView textView = (TextView) v.findViewById(R.id.tvTab);
                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                        tv.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.pink));
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    View v = tab.getCustomView();
                    if (v != null) {
                        View tv = (View) v.findViewById(R.id.view2);
                        TextView textView = (TextView) v.findViewById(R.id.tvTab);
                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.icon_color));
                        tv.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.app_color));
                    }
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
        }
    }


    private void settab() {
        binding.tablayout.setTabGravity(TabLayout.GRAVITY_START);
        binding.tablayout.removeAllTabs();

        binding.tablayout.addTab(binding.tablayout.newTab().setCustomView(createCustomView("Activities")));
        binding.tablayout.addTab(binding.tablayout.newTab().setCustomView(createCustomView("Message")));

        final ViewGroup test = (ViewGroup) (binding.tablayout.getChildAt(0));
        int tabLen = test.getChildCount();

        for (int i = 0; i < tabLen; i++) {
            View v = test.getChildAt(i);
            v.setPadding(5, 0, 5, 0);
        }
    }


    private View createCustomView(String s) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tabhorizontol, null);
        TextView tv = (TextView) v.findViewById(R.id.tvTab);
        tv.setText(s);
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.icon_color));
        return v;

    }


}
