package com.example.shortvideod.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.BuildConfig;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ActivitySettingBinding;
import com.example.shortvideod.util.SessionManager;

public class SettingActivity extends BaseActivity {
    ActivitySettingBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_setting);

        sessionManager = new SessionManager(this);

        binding.logout.setOnClickListener(v -> {
            Intent i = new Intent(SettingActivity.this, SplashActivity.class);
            startActivity(i);
            finish();

        });

        binding.tvVersion.setText(getText(R.string.app_name) + " : " + BuildConfig.VERSION_NAME);


    }
}