package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemRedeemCoinBinding;

public class RedeemCoinAdapter extends RecyclerView.Adapter<RedeemCoinAdapter.CoinHolder> {
    Context context;

    OnClickReedem onClickReedem;
    public OnClickReedem getOnClickReedem() {
        return onClickReedem;
    }

    public void setOnClickReedem(OnClickReedem onClickReedem) {
        this.onClickReedem = onClickReedem;
    }

    public interface OnClickReedem {
        void onClick();
    }

    @Override
    public void onBindViewHolder(@NonNull CoinHolder holder, int position) {
        holder.setData();
    }


    @NonNull
    @Override
    public CoinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CoinHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redeem_coin, parent, false));
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public interface OnHashtagClick {
        void onclick();
    }

//    public void addData(List<Integer> hashtagList) {
//        hashtag.addAll(hashtagList);
//        notifyItemRangeInserted(hashtag.size(), hashtagList.size());
//    }

    public class CoinHolder extends RecyclerView.ViewHolder {
        ItemRedeemCoinBinding binding;

        public CoinHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemRedeemCoinBinding.bind(itemView);
        }

        public void setData() {
            binding.coin.setOnClickListener(v ->
                    onClickReedem.onClick());


        }
    }
}
