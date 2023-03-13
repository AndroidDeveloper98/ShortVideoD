package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemGiftNotificationBinding;
import com.example.shortvideod.design.GiftList;

import java.util.ArrayList;
import java.util.List;

public class GiftNotificationAdapter extends RecyclerView.Adapter<GiftNotificationAdapter.GiftViewHolder> {

    Context context;
    private List<GiftList> gifts = new ArrayList<>();


    @Override
    public GiftViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new GiftViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gift_notification, parent, false));
    }


    @Override
    public void onBindViewHolder(GiftViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return gifts.size();
    }

    public List<GiftList> getList() {
        return gifts;
    }

    public void addData(List<GiftList> gift) {
        this.gifts.addAll(gift);
        notifyItemRangeInserted(this.gifts.size(), gift.size());
    }


    public class GiftViewHolder extends RecyclerView.ViewHolder {
        ItemGiftNotificationBinding binding;

        public GiftViewHolder(View itemView) {
            super(itemView);
            binding = ItemGiftNotificationBinding.bind(itemView);
        }

        public void setData(int position) {

            GiftList gift = gifts.get(position);
            Glide.with(binding.getRoot()).load(gift.getSenderImg()).into(binding.userImg);
            Glide.with(binding.getRoot()).load(gift.getGiftImg()).into(binding.giftImg);

            binding.comment.setText(gift.getComment());

        }
    }

}
