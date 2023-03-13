package com.example.shortvideod.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.CoinAdapter;
import com.example.shortvideod.databinding.FragmentMyCoinFragmentBinding;
import com.example.shortvideod.databinding.PaymentBottomsheetDialogBinding;
import com.example.shortvideod.purchase.Myplaystorebilling;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class MyCoinFragment extends Fragment {
    FragmentMyCoinFragmentBinding binding;
    CoinAdapter coinAdapter;
    Myplaystorebilling myplaystorebilling;
    PaymentBottomsheetDialogBinding paymentsheetbinding;
    BottomSheetDialog bottomSheetDialog;

    public MyCoinFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_coin_fragment, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        coinAdapter = new CoinAdapter();
        binding.rvCoin.setAdapter(coinAdapter);
        SetLogic();


        coinAdapter.setOnClickItem((coin, price) -> bottomSheetDialog.show());


        myplaystorebilling = new Myplaystorebilling(getActivity(), new Myplaystorebilling.OnPurchaseComplete() {
            @Override
            public void onConnected(boolean isConnect) {
            }

            @Override
            public void onPurchaseResult(boolean isPurchaseSuccess) {
                if (isPurchaseSuccess) {
                    Toast.makeText(getActivity(), "Purchased", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SetLogic() {
        bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.CustomBottomSheetDialogTheme);
        bottomSheetDialog.setOnShowListener(dialog -> {
            BottomSheetDialog d = (BottomSheetDialog) dialog;
            FrameLayout bottomSheet = (FrameLayout) d.findViewById(R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
        paymentsheetbinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.payment_bottomsheet_dialog, null, false);
        bottomSheetDialog.setContentView(paymentsheetbinding.getRoot());
        paymentsheetbinding.gpayLay.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Purchased Successfully...", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });


        paymentsheetbinding.stripeLay.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Purchased Successfully...", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });

    }


}
