package com.example.shortvideod.activity;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.shortvideod.NavGraphDirections;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ActivityMainPageBinding;

public class MainPageActivity extends BaseActivity {
    ActivityMainPageBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(SOFT_INPUT_ADJUST_PAN);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_page);
        initView();
    }

    private void initView() {
        binding.home.setImageTintList(ContextCompat.getColorStateList(this, R.color.pink));
        binding.txtHome.setTextColor(ContextCompat.getColorStateList(this, R.color.pink));
        navController = findNavController();

        binding.homeLay.setOnClickListener(v -> {
            setDefault();
            navController.navigate(NavGraphDirections.actionHome());
            binding.home.setImageTintList(ContextCompat.getColorStateList(this, R.color.pink));
            binding.txtHome.setTextColor(ContextCompat.getColorStateList(this, R.color.pink));

        });

        binding.searchLay.setOnClickListener(v -> {
            setDefault();
            navController.navigate(NavGraphDirections.actionSearch());
            binding.search.setImageTintList(ContextCompat.getColorStateList(this, R.color.pink));
            binding.txtSearch.setTextColor(ContextCompat.getColorStateList(this, R.color.pink));

        });


        binding.chatLay.setOnClickListener(v -> {
            setDefault();


            navController.navigate(NavGraphDirections.actionChat());
            binding.chat.setImageTintList(ContextCompat.getColorStateList(this, R.color.pink));
            binding.txtChat.setTextColor(ContextCompat.getColorStateList(this, R.color.pink));

        });

        binding.profileLay.setOnClickListener(v -> {
            setDefault();

            navController.navigate(NavGraphDirections.actionMainProfile());
            binding.profile.setImageTintList(ContextCompat.getColorStateList(this, R.color.pink));
            binding.txtProfile.setTextColor(ContextCompat.getColorStateList(this, R.color.pink));

        });

        binding.plusLay.setOnClickListener(v -> {
            startActivity(new Intent(this, RecorderActivity.class));
        });
    }

    private void setDefault() {
        binding.home.setImageTintList(ContextCompat.getColorStateList(this, R.color.icon_color));
        binding.search.setImageTintList(ContextCompat.getColorStateList(this, R.color.icon_color));
        binding.chat.setImageTintList(ContextCompat.getColorStateList(this, R.color.icon_color));
        binding.profile.setImageTintList(ContextCompat.getColorStateList(this, R.color.icon_color));
        binding.txtHome.setTextColor(ContextCompat.getColor(this, R.color.icon_color));
        binding.txtSearch.setTextColor(ContextCompat.getColor(this, R.color.icon_color));
        binding.txtChat.setTextColor(ContextCompat.getColor(this, R.color.icon_color));
        binding.txtProfile.setTextColor(ContextCompat.getColor(this, R.color.icon_color));
    }



    public NavController findNavController() {
        NavHostFragment fragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.host);
        return fragment.getNavController();
    }


}