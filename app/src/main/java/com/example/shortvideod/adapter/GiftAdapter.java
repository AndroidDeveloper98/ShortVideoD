package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemGiftBinding;
import com.example.shortvideod.design.Gift;

import java.util.ArrayList;
import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.GiftViewHolder> {
    Context context;
    private List<Gift> gifts = new ArrayList<>();
    boolean isshowcount = false;
    OnGiftClick onGiftClick;
    int selectpos = -1;

    public OnGiftClick getOnGiftClick() {
        return onGiftClick;
    }

    public void setOnGiftClick(OnGiftClick onGiftClick) {
        this.onGiftClick = onGiftClick;
    }

    public void isshow(boolean isshowcount) {
        this.isshowcount = isshowcount;
    }

    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new GiftViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift, parent, false));
    }

    public void refreshSelectValue() {
        selectpos = -1;
    }

    @Override
    public void onBindViewHolder(GiftViewHolder holder, int position) {

        if (selectpos == position)
            holder.binding.selectedLay.setVisibility(View.VISIBLE);
        else
            holder.binding.selectedLay.setVisibility(View.GONE);

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    public void clear() {
        gifts.clear();
        notifyDataSetChanged();
    }


    public interface OnGiftClick {
        void onGiftClick(Gift gift);

        void onSendGiftClick(Gift gift);
    }

    public List<Gift> getList() {
        return gifts;
    }

    public void addData(List<Gift> gift) {
        this.gifts.addAll(gift);
        notifyItemRangeInserted(this.gifts.size(), gift.size());
    }

    public class GiftViewHolder extends RecyclerView.ViewHolder {
        ItemGiftBinding binding;

        public GiftViewHolder(View itemView) {
            super(itemView);
            binding = ItemGiftBinding.bind(itemView);
        }

        public void setData(int position) {
            Gift gift = gifts.get(position);
            Glide.with(binding.getRoot()).load(gift.getImg()).placeholder(R.drawable.gift).into(binding.giftImg);
            Glide.with(binding.getRoot()).load(gift.getImg()).placeholder(R.drawable.gift).into(binding.selectGift);

            binding.selectedCoin.setText(gift.getCoins() + " " + "Coins");

            binding.btnSend.setOnClickListener(v ->
                    onGiftClick.onSendGiftClick(gift));


            binding.name.setText(gift.getName());
            binding.coins.setText(gift.getCoins() + " " + "Coins");

            itemView.setOnClickListener(v -> {
                onGiftClick.onGiftClick(gift);
                selectpos = position;
                notifyDataSetChanged();
            });


        }
    }

}
