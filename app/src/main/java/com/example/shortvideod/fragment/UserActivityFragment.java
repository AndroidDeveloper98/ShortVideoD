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

import com.example.shortvideod.R;
import com.example.shortvideod.activity.CommentActivity;
import com.example.shortvideod.activity.GiftActivity;
import com.example.shortvideod.activity.LikesActivity;
import com.example.shortvideod.activity.MentionActivity;
import com.example.shortvideod.activity.UserFollowerActivity;
import com.example.shortvideod.adapter.UserActivitiesAdapter;
import com.example.shortvideod.databinding.FragmentUserActivityBinding;
import com.example.shortvideod.design.Democontents;

public class UserActivityFragment extends Fragment {
    FragmentUserActivityBinding binding;
    UserActivitiesAdapter activitiesAdapter;
    NavController navController;


    public UserActivityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_activity, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        navController = Navigation.findNavController(requireActivity(), R.id.host);

        activitiesAdapter = new UserActivitiesAdapter();
        binding.rvactvities.setAdapter(activitiesAdapter);

        activitiesAdapter.addData(Democontents.getActivities());

        binding.likes.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), LikesActivity.class);
            startActivity(i);

        });
        binding.comments.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), CommentActivity.class);
            startActivity(i);

        });

        binding.followers.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), UserFollowerActivity.class);
            startActivity(i);
        });

        binding.mentions.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), MentionActivity.class);
            startActivity(i);

        });
        binding.gifts.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), GiftActivity.class);
            startActivity(i);
        });
    }
}
