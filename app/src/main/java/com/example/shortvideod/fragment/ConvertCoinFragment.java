package com.example.shortvideod.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.ConvertCoinAdapter;
import com.example.shortvideod.databinding.FragmentCovertCoinBinding;
import com.example.shortvideod.popupbuilder.PopupBuilder;

public class ConvertCoinFragment extends Fragment {

    FragmentCovertCoinBinding binding;
    ConvertCoinAdapter coinAdapter;
    Dialog mBuilder;
    int myRcoin = 3000;

    public ConvertCoinFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_covert_coin, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        mBuilder = new Dialog(getActivity());
        mBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBuilder.setCancelable(false);
        mBuilder.setCanceledOnTouchOutside(false);
        if (mBuilder != null && mBuilder.getWindow() != null) {
            mBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }


        coinAdapter = new ConvertCoinAdapter();
        binding.rvConvertCoin.setAdapter(coinAdapter);

        coinAdapter.setOnClickConvert(() -> {
            PopupBuilder popupBuilder = new PopupBuilder(getActivity());
            popupBuilder.showRcoinConvertPopup(false, myRcoin, rcoin -> {
                int dimonds = rcoin / 100;
                String s = "Your " + rcoin + " Rcoin Successfully Converted into " + dimonds + " Diamonds";
                popupBuilder.showSimplePopup(s, "Continue");
            });
        });


    }


    public interface OnRcoinConvertPopupClickListner {
        void onClickConvert(int rcoin);
    }
}
