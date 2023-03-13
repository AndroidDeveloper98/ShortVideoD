package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemHashtagsBinding;
import com.example.shortvideod.design.Hashtag;

import java.util.ArrayList;
import java.util.List;


public class HashtagsVideoAdapter extends RecyclerView.Adapter<HashtagsVideoAdapter.HashtagViewHolder> {
    OnHashtagsClickLisnter onMentionssClickLisnter;
    Context context;
    List<Hashtag> hashtags = new ArrayList<>();

    public OnHashtagsClickLisnter getOnHashtagsClickLisnter() {
        return onMentionssClickLisnter;
    }

    public void setOnHashtagsClickLisnter(OnHashtagsClickLisnter onMentionssClickLisnter) {
        this.onMentionssClickLisnter = onMentionssClickLisnter;
    }

    @NonNull
    @Override
    public HashtagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HashtagViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hashtags, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return hashtags.size();
    }

    public void addData(List<Hashtag> hashtags) {
        this.hashtags.addAll(hashtags);
        notifyItemRangeInserted(this.hashtags.size(), hashtags.size());
    }

    public void clear() {
        this.hashtags.clear();
        notifyDataSetChanged();
    }

    public interface OnHashtagsClickLisnter {
        void onHashtagClick(Hashtag hashtag);
    }

    public class HashtagViewHolder extends RecyclerView.ViewHolder {
        ItemHashtagsBinding binding;

        public HashtagViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemHashtagsBinding.bind(itemView);

        }

        public void setData(int position) {
            Hashtag hashtagItem = hashtags.get(position);
            binding.tvHashtag.setText(hashtagItem.getHashtagtext());
            binding.getRoot().setOnClickListener(v -> onMentionssClickLisnter.onHashtagClick(hashtags.get(position)));
        }
    }
}
