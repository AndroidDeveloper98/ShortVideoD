package com.example.shortvideod.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.shortvideod.R;
import com.example.shortvideod.activity.ChatActivity;
import com.example.shortvideod.adapter.GiftProfileAdapter;
import com.example.shortvideod.adapter.UserPostVideoAdapter;
import com.example.shortvideod.databinding.FragmentUserProfileBinding;
import com.example.shortvideod.design.Democontents;

public class UserProfileFragment extends Fragment {
    FragmentUserProfileBinding binding;
    GiftProfileAdapter giftAdapter;
    UserPostVideoAdapter userPostVideoAdapter;
    NavController navController;

    public UserProfileFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false);
        initView();
        return binding.getRoot();
    }


    private void initView() {
        giftAdapter = new GiftProfileAdapter();
        userPostVideoAdapter = new UserPostVideoAdapter();

        navController = Navigation.findNavController(requireActivity(), R.id.host);

        String path = getArguments().getString("Const.USERIMAGE");
        String username = getArguments().getString("Const.USERNAMELIST");

        Log.d("TAG", "initView: path " + path);
        Log.d("TAG", "initView:  username " + username);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.bg)
                .error(R.drawable.banner_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH);

        Glide.with(binding.getRoot()).load(path).apply(options).into(binding.profilePic);


        binding.username.setText(username);
        binding.rvGift.setAdapter(giftAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        binding.rvPostVideo.setLayoutManager(staggeredGridLayoutManager);
        binding.rvPostVideo.setAdapter(userPostVideoAdapter);

        giftAdapter.addData(Democontents.getGiftList());
        userPostVideoAdapter.addData(Democontents.getPostVideo());


        userPostVideoAdapter.setOnItemUserVideoClick(() ->
                navController.navigate(R.id.action_mainprofilefragment_to_Homefragment));


        binding.back.setOnClickListener(v -> {
            try {
                navController.popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        binding.btnFollow.setOnClickListener(v -> {
            if (binding.btnFollow.getText().toString().equalsIgnoreCase("Follow")) {
                binding.btnFollow.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_following));
                binding.btnFollow.setText(R.string.following);
            }
        });

        binding.message.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ChatActivity.class);
            i.putExtra("Const.CHATUSERNAME", binding.username.getText().toString());
            startActivity(i);
        });

    }

}
