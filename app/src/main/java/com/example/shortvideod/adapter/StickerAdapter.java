package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemStickerGridBinding;
import com.example.shortvideod.design.Sticker;

import java.util.ArrayList;
import java.util.List;

public class StickerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    OnStickerClickListner onSongClickListner;
    Context context;
    private List<Sticker> stickerDummies = new ArrayList<>();

    public OnStickerClickListner getOnSongClickListner() {
        return onSongClickListner;
    }

    public void setOnSongClickListner(OnStickerClickListner onSongClickListner) {
        this.onSongClickListner = onSongClickListner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new StickerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sticker_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((StickerViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return stickerDummies.size();
    }

    public void addData(List<Sticker> songs) {

        this.stickerDummies.addAll(songs);
        notifyItemRangeInserted(this.stickerDummies.size(), songs.size());
    }

    public interface OnStickerClickListner {
        void onStickerClick(Sticker song);
    }

    public class StickerViewHolder extends RecyclerView.ViewHolder {
        ItemStickerGridBinding binding;

        public StickerViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStickerGridBinding.bind(itemView);

        }

        public void setData(int position) {
            Sticker sticker = stickerDummies.get(position);
            Glide.with(context).load(sticker.getImage()).into(binding.image);
            binding.getRoot().setOnClickListener(v -> onSongClickListner.onStickerClick(sticker));
        }
    }
}

