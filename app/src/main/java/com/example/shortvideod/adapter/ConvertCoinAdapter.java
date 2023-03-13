package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemConvertCoinBinding;

public class ConvertCoinAdapter extends RecyclerView.Adapter<ConvertCoinAdapter.ConvertCoinHolder> {
    Context context;

    OnClickConvert onClickConvert;

    public OnClickConvert getOnClickConvert() {
        return onClickConvert;
    }

    public void setOnClickConvert(OnClickConvert onClickConvert) {
        this.onClickConvert = onClickConvert;
    }

    public interface OnClickConvert {
        void onClick();
    }


    @Override
    public void onBindViewHolder(@NonNull ConvertCoinHolder holder, int position) {
        holder.setData();
    }


    @NonNull
    @Override
    public ConvertCoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ConvertCoinHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_convert_coin, parent, false));
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public interface OnHashtagClick {
        void onclick();
    }


    public class ConvertCoinHolder extends RecyclerView.ViewHolder {
        ItemConvertCoinBinding binding;

        public ConvertCoinHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemConvertCoinBinding.bind(itemView);
        }

        public void setData() {


            binding.coin.setOnClickListener(v ->
                    onClickConvert.onClick());


        }
    }
}
