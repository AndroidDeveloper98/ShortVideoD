package com.example.shortvideod.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.LocationAdapter;
import com.example.shortvideod.databinding.ActivityLocationChooseBinding;
import com.example.shortvideod.design.Democontents;


public class LocationChooseActivity extends BaseActivity {
    public static final int REQ_CODE_LOCATION = 123;
    ActivityLocationChooseBinding binding;
    LocationAdapter locationAdapter = new LocationAdapter();
    String keyword = "Surat";
    int start = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_location_choose);


        binding.btnDone.setOnClickListener(v -> {
            Intent i = getIntent();
            setResult(RESULT_OK, i);
            finish();
        });

        binding.rvLocation.setAdapter(locationAdapter);
        locationAdapter.addData(Democontents.getlocationlist());


        locationAdapter.setOnLocationClickLisnter(selectedLocation -> {
            Intent i = getIntent();
            setResult(RESULT_OK, i);
            finish();
        });

        binding.etLocation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyword = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}