package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemTotalCoinBinding;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinHolder> {
    Context context;
    OnClickItem onClickItem;

    public OnClickItem getOnClickItem() {
        return onClickItem;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {
        void onClick(String coin, String price);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinHolder holder, int position) {
        holder.setData(position);
    }


    @NonNull
    @Override
    public CoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CoinHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_total_coin, parent, false));
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public interface OnHashtagClick {
        void onclick();
    }

    public class CoinHolder extends RecyclerView.ViewHolder {
        ItemTotalCoinBinding binding;

        public CoinHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTotalCoinBinding.bind(itemView);
        }

        public void setData(int position) {

            if (position == 0) {
                binding.adsLay.setVisibility(View.VISIBLE);
                binding.price.setVisibility(View.GONE);
            } else {
                binding.adsLay.setVisibility(View.GONE);
                binding.price.setVisibility(View.VISIBLE);
            }

            String coin = binding.txtTotal.getText().toString();
            String price = binding.price.getText().toString();

            binding.getRoot().setOnClickListener(v -> {
                if (position == 0) {
                    Toast.makeText(context, "It is only for demo", Toast.LENGTH_SHORT).show();
                } else {
                    onClickItem.onClick(coin, price);
                }
            });
        }
    }
}
