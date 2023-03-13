package com.example.shortvideod.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.shortvideod.R;
import com.example.shortvideod.activity.EditActivity;
import com.example.shortvideod.activity.SettingActivity;
import com.example.shortvideod.activity.UserFollowerActivity;
import com.example.shortvideod.adapter.UserPostVideoAdapter;
import com.example.shortvideod.databinding.FragmentMainProfileBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.util.SessionManager;

public class MainProfileFragment extends Fragment {
    FragmentMainProfileBinding binding;
    UserPostVideoAdapter userPostVideoAdapter;
    NavController navController;
    SessionManager sessionManager;

    public MainProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_profile, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {

        userPostVideoAdapter = new UserPostVideoAdapter();

        sessionManager = new SessionManager(getActivity());


        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);


        binding.rvPostVideo.setLayoutManager(staggeredGridLayoutManager);
        binding.rvPostVideo.setAdapter(userPostVideoAdapter);

        navController = Navigation.findNavController(requireActivity(), R.id.host);

        userPostVideoAdapter.addData(Democontents.getPostVideo());

        userPostVideoAdapter.setOnItemUserVideoClick(() ->
                navController.navigate(R.id.action_mainprofilefragment_to_Homefragment));


        binding.edit.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), EditActivity.class);
            startActivity(i);
        });

        binding.followerLay.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), UserFollowerActivity.class);
            startActivity(i);
        });

        binding.followingLay.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), UserFollowerActivity.class);
            startActivity(i);
        });

        binding.wallet.setOnClickListener(v -> navController.navigate(R.id.action_mainProfileFragment_to_walletFragment));


        binding.setting.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), SettingActivity.class);
            startActivity(i);
        });


    }
}
