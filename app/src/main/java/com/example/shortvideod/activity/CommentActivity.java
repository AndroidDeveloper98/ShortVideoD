package com.example.shortvideod.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.CommentAdapter;
import com.example.shortvideod.databinding.ActivityCommentBinding;
import com.example.shortvideod.design.Democontents;

public class CommentActivity extends BaseActivity {

    ActivityCommentBinding binding;
    CommentAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_comment);

        commentAdapter = new CommentAdapter();
        binding.rvComment.setAdapter(commentAdapter);

        commentAdapter.addData(Democontents.getComments());

        binding.back.setOnClickListener(v ->
                finish());

    }
}