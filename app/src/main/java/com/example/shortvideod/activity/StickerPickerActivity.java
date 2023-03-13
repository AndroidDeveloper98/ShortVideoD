package com.example.shortvideod.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.StickerAdapter;
import com.example.shortvideod.databinding.ActivityStickerPickerBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.design.Sticker;


public class StickerPickerActivity extends BaseActivity {

    public static final String EXTRA_STICKER = "sticker";


    ActivityStickerPickerBinding binding;
    StickerAdapter stickerAdapter = new StickerAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sticker_picker);

        binding.rvSongs.setAdapter(stickerAdapter);
        stickerAdapter.addData(Democontents.getStickers());
        stickerAdapter.setOnSongClickListner(this::closeWithSelection);

    }


    public void closeWithSelection(Sticker stickerDummy) {
        Intent data = new Intent();
        data.putExtra(EXTRA_STICKER, stickerDummy);
        setResult(RESULT_OK, data);
        finish();
    }


}
