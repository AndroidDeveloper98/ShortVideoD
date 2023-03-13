package com.example.shortvideod.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ActivityLoginBinding;
import com.example.shortvideod.util.SessionManager;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
    }

    private void initView() {
        sessionManager = new SessionManager(this);

        binding.btnLogin.setOnClickListener(v -> {

            startActivity(new Intent(this, MainPageActivity.class));
            finish();

        });


        binding.facebook.setOnClickListener(v ->
                binding.btnLogin.performClick());

        binding.google.setOnClickListener(v ->
                binding.btnLogin.performClick());

    }




}