package com.example.shortvideod.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.shortvideod.R;
import com.example.shortvideod.util.SessionManager;

public class SplashActivity extends BaseActivity {
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);

        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        finish();

    }
}