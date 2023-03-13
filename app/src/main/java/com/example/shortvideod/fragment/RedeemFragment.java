package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.RedeemCoinAdapter;
import com.example.shortvideod.databinding.FragmentRedeemBinding;
import com.example.shortvideod.popupbuilder.PopupBuilder;

public class RedeemFragment extends Fragment {
    FragmentRedeemBinding binding;
    RedeemCoinAdapter redeemCoinAdapter;
    int myRcoin = 3000;

    public RedeemFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_redeem, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        redeemCoinAdapter = new RedeemCoinAdapter();
        binding.rvConvertCoin.setAdapter(redeemCoinAdapter);

        redeemCoinAdapter.setOnClickReedem(() -> {
            PopupBuilder popupBuilder = new PopupBuilder(getActivity());
            popupBuilder.showRcoinConvertPopup(true, myRcoin, rcoin -> {
                int cash = rcoin / 100;
                String s = "Your " + rcoin + " Successfully Redeem Your " + cash + " Diamonds";
                popupBuilder.showSimplePopup(s, "Continue");
            });
        });
    }

}
