package com.example.shortvideod.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.CommentAdapter;
import com.example.shortvideod.databinding.ActivityPostCommentBinding;
import com.example.shortvideod.design.Democontents;

public class CommentPostActivity extends BaseActivity {

    ActivityPostCommentBinding binding;
    CommentAdapter commentAdapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_comment);


        commentAdapter = new CommentAdapter();
        binding.rvComment.setAdapter(commentAdapter);

        commentAdapter.addData(Democontents.getComments());

        binding.back.setOnClickListener(v -> finish());


        binding.send.setOnClickListener(v -> {
            if (!binding.etChat.getText().toString().equals("")) {
                binding.etChat.setText("");
            }
        });

    }
}