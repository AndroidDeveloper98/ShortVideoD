package com.example.shortvideod.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.LikesAdapter;
import com.example.shortvideod.databinding.ActivityLikesBinding;
import com.example.shortvideod.design.Democontents;

public class LikesActivity extends BaseActivity {
    ActivityLikesBinding binding;
    LikesAdapter likesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_likes);

        likesAdapter = new LikesAdapter();
        binding.rvLike.setAdapter(likesAdapter);

        likesAdapter.addData(Democontents.getLikes());

        binding.back.setOnClickListener(v -> finish());


    }
}