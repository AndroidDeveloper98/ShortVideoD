package com.example.shortvideod.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.ChatAdapter;
import com.example.shortvideod.databinding.ActivityChatBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.fragment.GiftBottomSheetChatFrgament;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ChatActivity extends BaseActivity {
    ActivityChatBinding binding;
    ChatAdapter chatAdapter;
    BottomSheetDialogFragment bottomSheetDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        initView();
    }

    private void initView() {
        chatAdapter = new ChatAdapter();
        binding.rvChat.setAdapter(chatAdapter);


        bottomSheetDialogFragment = new GiftBottomSheetChatFrgament();

        chatAdapter.addData(Democontents.getRandomChat());

        binding.username.setOnClickListener(v -> finish());


        binding.gift.setOnClickListener(v ->
                bottomSheetDialogFragment.show(getSupportFragmentManager(), "giftfragment"));


        binding.galleryLay.setOnClickListener(v -> {
            if (binding.photoOption.getVisibility() == View.VISIBLE) {
                binding.photoOption.setVisibility(View.GONE);
                binding.galleryLay.setImageResource(R.drawable.chat_icon);
            } else {
                binding.photoOption.setVisibility(View.VISIBLE);
                binding.galleryLay.setImageResource(R.drawable.close);
            }
        });

        binding.back.setOnClickListener(v -> onBackPressed());


        binding.etChat.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            try {
                if (keyboardShown(binding.etChat.getRootView())) {
                    Log.d("keyboard", "keyboard UP");
                    binding.rvChat.getLayoutManager().scrollToPosition(chatAdapter.getItemCount() - 1);
                } else {
                    Log.d("keyboard", "keyboard Down");
                }
            } catch (Exception e) {
                //
            }
        });


        binding.send.setOnClickListener(v -> {
            if (!binding.etChat.getText().toString().equals("")) {
                binding.etChat.setText("");
            }
        });


        binding.camera.setOnClickListener(v ->
                Toast.makeText(ChatActivity.this, "This is demo version", Toast.LENGTH_SHORT).show());


        binding.gallry.setOnClickListener(v ->
                Toast.makeText(ChatActivity.this, "This is demo version", Toast.LENGTH_SHORT).show());


    }

    private boolean keyboardShown(View rootView) {
        final int softKeyboardHeight = 100;
        Rect r = new Rect();
        rootView.getWindowVisibleDisplayFrame(r);
        DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
        int heightDiff = rootView.getBottom() - r.bottom;
        return heightDiff > softKeyboardHeight * dm.density;
    }
}