package com.example.shortvideod.popupbuilder;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemPopupDiscardReliteBinding;
import com.example.shortvideod.databinding.ItemSimplepopupBinding;
import com.example.shortvideod.databinding.PopupRcoinConvertBinding;
import com.example.shortvideod.fragment.ConvertCoinFragment;
import com.example.shortvideod.util.SessionManager;

public class PopupBuilder {
    private final Context mContext;
    Dialog mBuilder;
    SessionManager sessionManager;

    public interface OnPopupClickListner {
        void onClickCountinue();
    }

    public PopupBuilder(Context context) {
        this.mContext = context;
        if (mContext != null) {
            sessionManager = new SessionManager(context);
            mBuilder = new Dialog(mContext);
            mBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            mBuilder.setCancelable(false);
            mBuilder.setCanceledOnTouchOutside(false);
            if (mBuilder != null && mBuilder.getWindow() != null) {
                mBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
    }

    public void showRcoinConvertPopup(boolean isCashOut, int maxCoin, ConvertCoinFragment.OnRcoinConvertPopupClickListner onRcoinConvertPopupClickListner) {
        if (mContext == null)
            return;

        mBuilder.setCancelable(true);
        mBuilder.setCanceledOnTouchOutside(true);
        PopupRcoinConvertBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.popup_rcoin_convert, null, false);
        mBuilder.setContentView(binding.getRoot());
        final int[] coin = new int[1];


        binding.etRcoin.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().isEmpty()) {
                    coin[0] = Integer.parseInt(s.toString());
                    if (coin[0] < 100) {
                        binding.tvDiamondsValue.setText("Minimum amount is 100 Rcoins");
                        binding.tvDiamondsValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                        new Handler(Looper.getMainLooper()).postDelayed(() -> binding.tvDiamondsValue.setTextColor(ContextCompat.getColor(mContext, R.color.yellow)), 1000);

                    } else if (coin[0] > maxCoin) {
                        binding.tvDiamondsValue.setText("You not have enough Rcoins");
                        binding.tvDiamondsValue.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                        new Handler(Looper.getMainLooper()).postDelayed(() -> binding.tvDiamondsValue.setTextColor(ContextCompat.getColor(mContext, R.color.yellow)), 1000);
                    } else {
                        if (isCashOut) {
                            int diamond = coin[0] / 100;
                            binding.tvDiamondsValue.setText("You Will Receive " + diamond + "Const.Currency");
                        } else {
                            int diamond = coin[0] / 100;
                            binding.tvDiamondsValue.setText("You Will Receive " + diamond + " Diamonds");
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.btncountinue.setOnClickListener(v -> {
            if (binding.etRcoin.getText().toString().isEmpty()) {
                Toast.makeText(mContext, "Please enter value sufficient value ", Toast.LENGTH_SHORT).show();
                return;
            }
            mBuilder.dismiss();
            onRcoinConvertPopupClickListner.onClickConvert(coin[0]);
        });

        binding.btnCancel.setOnClickListener(v -> mBuilder.dismiss());
        mBuilder.show();


    }

    public void showReliteDiscardPopup(String s1, String s2, String btn1, String btn2, OnPopupClickListner onPopupClickListner) {
        if (this.mContext != null) {
            this.mBuilder.setCancelable(true);
            this.mBuilder.setCanceledOnTouchOutside(true);
            ItemPopupDiscardReliteBinding binding = (ItemPopupDiscardReliteBinding) DataBindingUtil.inflate(LayoutInflater.from(this.mContext), R.layout.item_popup_discard_relite, (ViewGroup) null, false);
            this.mBuilder.setContentView(binding.getRoot());
            binding.tvText.setText(s1);
            binding.tvText2.setText(s2);
            binding.btncountinue.setText(btn1);
            binding.btnCancel.setText(btn2);
            binding.btnCancel.setOnClickListener(view -> mBuilder.dismiss());
            //binding.btncountinue.setOnClickListener(new PopupBuilder$$ExternalSyntheticLambda1(this, onPopupClickListner));
            binding.btncountinue.setOnClickListener(view -> onPopupClickListner.onClickCountinue());
            if (s1.isEmpty()) {
                binding.tvText.setVisibility(View.GONE);
            }
            if (s2.isEmpty()) {
                binding.tvText2.setVisibility(View.GONE);
            }
            if (btn1.isEmpty()) {
                binding.btncountinue.setVisibility(View.GONE);
            }
            if (btn2.isEmpty()) {
                binding.btnCancel.setVisibility(View.GONE);
            }
            this.mBuilder.show();
        }
    }

    public void showSimplePopup(String s, String btnText) {
        if (mContext == null)
            return;

        mBuilder.setCancelable(true);
        mBuilder.setCanceledOnTouchOutside(true);
        ItemSimplepopupBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_simplepopup, null, false);
        mBuilder.setContentView(binding.getRoot());
        binding.tvText.setText(s);
        binding.btncountinue.setText(btnText);
        binding.btncountinue.setOnClickListener(v -> mBuilder.dismiss());
        mBuilder.show();

    }

}
