package com.example.shortvideod.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemSongBinding;
import com.example.shortvideod.design.Song;

import java.util.ArrayList;
import java.util.List;

public class SongsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    OnSongClickListner onSongClickListner;
    private List<Song> songItems = new ArrayList<>();

    public OnSongClickListner getOnSongClickListner() {
        return onSongClickListner;
    }

    public void setOnSongClickListner(OnSongClickListner onSongClickListner) {
        this.onSongClickListner = onSongClickListner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SongsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false));


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((SongsViewHolder) holder).setData(position);
    }

    @Override
    public int getItemCount() {
        return songItems.size();
    }

    public void addData(List<Song> songDummies) {

        this.songItems.addAll(songDummies);
        notifyItemRangeInserted(this.songItems.size(), songDummies.size());
    }

    public interface OnSongClickListner {
        void onSongClick(Song songDummy);
    }

    public class SongsViewHolder extends RecyclerView.ViewHolder {
        ItemSongBinding binding;

        public SongsViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSongBinding.bind(itemView);
            Glide.with(itemView).load(R.drawable.music).into(binding.imgSong);
        }

        public void setData(int position) {
            Song songDummy = songItems.get(position);
            Glide.with(binding.getRoot()).load(songDummy.albumImage).into(binding.imgSong);
            binding.getRoot().setOnClickListener(v -> onSongClickListner.onSongClick(songDummy));
        }
    }
}

