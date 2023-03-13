package com.example.shortvideod.activity;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ActivityGiftBinding;

public class GiftActivity extends BaseActivity {
    ActivityGiftBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_gift);

        binding.back.setOnClickListener(v -> finish());

    }
}