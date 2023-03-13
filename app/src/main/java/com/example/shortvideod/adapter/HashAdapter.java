package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemHashtagBinding;

import java.util.ArrayList;
import java.util.List;

public class HashAdapter extends RecyclerView.Adapter<HashAdapter.HashtagHolder> {
    Context context;
    List<Integer> hashtag = new ArrayList<>();

    OnHashtagClick onHashtagClick;

    public OnHashtagClick getOnHashtagClick() {
        return onHashtagClick;
    }

    public void setOnHashtagClick(OnHashtagClick onHashtagClick) {
        this.onHashtagClick = onHashtagClick;
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        Glide.with(context).load(hashtag.get(position)).into(holder.binding.videoThumb);

        holder.itemView.setOnClickListener(v ->
                onHashtagClick.onclick());

    }


    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HashAdapter.HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hashtag, parent, false));
    }

    public interface OnHashtagClick {
        void onclick();
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
        ItemHashtagBinding binding;

        public HashtagHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemHashtagBinding.bind(itemView);
        }
    }
}
