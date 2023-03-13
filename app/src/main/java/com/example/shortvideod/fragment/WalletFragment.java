package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.ViewPagerWalletAdapter;
import com.example.shortvideod.databinding.FragmentWalletBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class WalletFragment extends Fragment {

    FragmentWalletBinding binding;
    NavController navController;

    public WalletFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_wallet, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        navController = Navigation.findNavController(Objects.requireNonNull(getActivity()), R.id.host);
        setTabLyt();
        settab();

        binding.back.setOnClickListener(v -> navController.popBackStack());

    }

    private void setTabLyt() {
        if (getActivity() != null) {
            binding.tablayout.setupWithViewPager(binding.pager);
            binding.pager.setAdapter(new ViewPagerWalletAdapter(getChildFragmentManager()));


            binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    View v = tab.getCustomView();
                    if (v != null) {
                        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.layout);
                        TextView textView = (TextView) v.findViewById(R.id.tvTab);
                        textView.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                        layout.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.bg_greadent_round_10dp));
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    View v = tab.getCustomView();
                    if (v != null) {
                        RelativeLayout layout = (RelativeLayout) v.findViewById(R.id.layout);
                        TextView textView = (TextView) v.findViewById(R.id.tvTab);
                        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
                        layout.setBackgroundDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.bg_greadent_round_10dp_appcolor));
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

//        for (int i = 0; i < category.size(); i++) {
//            binding.tablayout.addTab(binding.tablayout.newTab().setCustomView(createCustomView(category.get(i).getName())));
//        }
        binding.tablayout.addTab(binding.tablayout.newTab().setCustomView(createCustomView("My Coins")));
        binding.tablayout.addTab(binding.tablayout.newTab().setCustomView(createCustomView("My Income")));

        final ViewGroup test = (ViewGroup) (binding.tablayout.getChildAt(0));
        int tabLen = test.getChildCount();

        for (int i = 0; i < tabLen; i++) {
            View v = test.getChildAt(i);
            v.setPadding(5, 0, 5, 0);
        }
    }


    private View createCustomView(String s) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab_pinkbg, null);
        TextView tv = (TextView) v.findViewById(R.id.tvTab);
        tv.setText(s);
        tv.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
        return v;

    }

}
