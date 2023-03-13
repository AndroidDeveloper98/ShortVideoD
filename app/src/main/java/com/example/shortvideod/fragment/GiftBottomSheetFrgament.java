package com.example.shortvideod.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.FragmentGiftBottomsheetBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GiftBottomSheetFrgament extends BottomSheetDialogFragment {
    FragmentGiftBottomsheetBinding binding;

    boolean isdataadd = false;
    DialogInterface dialogbottom;


    public GiftBottomSheetFrgament() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme);
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_gift_bottomsheet, container, false);
        initData();
        return binding.getRoot();
    }

    private void initData() {
        binding.rechargeLay.setOnClickListener(v -> {
            Fragment parentFragment = getParentFragment();
            if (parentFragment instanceof HomeFragment) {
                ((HomeFragment) parentFragment).showWallet();
            }
        });

    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        this.dialogbottom = dialog;
    }
}
