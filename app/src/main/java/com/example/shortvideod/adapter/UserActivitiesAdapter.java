package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemActivitiesBinding;

import java.util.ArrayList;
import java.util.List;

public class UserActivitiesAdapter extends RecyclerView.Adapter<UserActivitiesAdapter.HashtagHolder> {
    Context context;
    List<Integer> hashtag = new ArrayList<>();


    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new UserActivitiesAdapter.HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activities, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        Glide.with(context).load(hashtag.get(position)).into(holder.binding.thumbnail);
    }

    @Override
    public int getItemCount() {
        return hashtag.size();
    }

    public void addData(List<Integer> hashtagList) {
        hashtag.addAll(hashtagList);
        notifyItemRangeInserted(hashtag.size(), hashtagList.size());
    }


    public class HashtagHolder extends RecyclerView.ViewHolder {
        ItemActivitiesBinding binding;

        public HashtagHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemActivitiesBinding.bind(itemView);
        }
    }
}
