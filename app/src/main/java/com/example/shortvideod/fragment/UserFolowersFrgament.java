package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.FollowersUserAdapter;
import com.example.shortvideod.databinding.FragmentUserFollowersBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.design.SuggestedUser;

import java.util.ArrayList;
import java.util.List;

public class UserFolowersFrgament extends Fragment {
    FragmentUserFollowersBinding binding;
    FollowersUserAdapter followersUserAdapter;
    int pos;
    List<SuggestedUser> suggestedUsers = new ArrayList<>();


    public UserFolowersFrgament(int position) {
        this.pos = position;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_followers, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        followersUserAdapter = new FollowersUserAdapter(pos);
        binding.rvFollowers.setAdapter(followersUserAdapter);

        suggestedUsers = Democontents.getSuggestedUser();

        followersUserAdapter.addData(Democontents.getSuggestedUser());


        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    searchUser(s.toString());
                } else {
                    followersUserAdapter.clear();
                    followersUserAdapter.addData(suggestedUsers);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        Log.d("===========", "initView: userfollowers " + pos);

    }

    private void searchUser(String toString) {
        followersUserAdapter.clear();
        List<SuggestedUser> finelUser = new ArrayList<>();
        for (SuggestedUser u : suggestedUsers
        ) {
            if (u.getName().toLowerCase().contains(toString.toLowerCase())) {
                finelUser.add(u);
            }

        }
        followersUserAdapter.addData(finelUser);
    }


}
