package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemLikeBinding;
import com.example.shortvideod.design.Likes;

import java.util.ArrayList;
import java.util.List;

public class LikesAdapter extends RecyclerView.Adapter<LikesAdapter.LikesHolder> {
    List<Likes> likes = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public LikesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LikesHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_like, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LikesHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return likes.size();
    }

    public void addData(List<Likes> like) {
        likes.addAll(like);
        notifyItemRangeInserted(likes.size(), like.size());
    }

    public class LikesHolder extends RecyclerView.ViewHolder {
        ItemLikeBinding binding;

        public LikesHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemLikeBinding.bind(itemView);
        }

        public void setData(int position) {
            Likes like = likes.get(position);

            Glide.with(context).load(like.getUserimg()).into(binding.userImg);
            binding.comment.setText(like.getComment());
            binding.date.setText(like.getDate());

        }
    }
}
