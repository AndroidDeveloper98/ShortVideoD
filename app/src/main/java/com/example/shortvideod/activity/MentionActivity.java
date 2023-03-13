package com.example.shortvideod.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.LikesAdapter;
import com.example.shortvideod.adapter.MentionAdapter;
import com.example.shortvideod.databinding.ActivityMentionBinding;
import com.example.shortvideod.design.Democontents;

public class MentionActivity extends BaseActivity {

    ActivityMentionBinding binding;
    MentionAdapter mentionAdapter;
    LikesAdapter likesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mention);

        mentionAdapter = new MentionAdapter();
        likesAdapter = new LikesAdapter();

        binding.rvMention.setAdapter(mentionAdapter);
        mentionAdapter.addData(Democontents.getMentions());

        binding.back.setOnClickListener(v -> finish());


    }
}