package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemBannerBinding;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.BannerHolder> {
    Context context;


    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BannerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_banner, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {
        if (position == 0)
            Glide.with(context).load(R.drawable.banner_image).into(holder.binding.bannerImage);
        else if (position == 1)
            Glide.with(context).load(R.drawable.banner1).into(holder.binding.bannerImage);
        else if (position == 2)
            Glide.with(context).load(R.drawable.banner2).into(holder.binding.bannerImage);
        else if (position == 3)
            Glide.with(context).load(R.drawable.banner3).into(holder.binding.bannerImage);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class BannerHolder extends RecyclerView.ViewHolder {
        ItemBannerBinding binding;

        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBannerBinding.bind(itemView);
        }
    }
}
