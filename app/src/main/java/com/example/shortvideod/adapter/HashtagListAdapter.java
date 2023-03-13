package com.example.shortvideod.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemSearchHashtagBinding;
import com.example.shortvideod.design.Hashtag;

import java.util.ArrayList;
import java.util.List;

public class HashtagListAdapter extends RecyclerView.Adapter<HashtagListAdapter.HashtagHolder> {
    List<Hashtag> hashtaglist = new ArrayList<>();
    OnItemClick onItemClick;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_hashtag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return hashtaglist.size();
    }

    public List<Hashtag> getList() {
        return hashtaglist;
    }

    public void addData(List<Hashtag> user) {
        this.hashtaglist.addAll(user);
        notifyItemRangeInserted(this.hashtaglist.size(), user.size());
    }

    public interface OnItemClick {
        void onClick(String hastag);
    }

    public void clear() {
        hashtaglist.clear();
        notifyDataSetChanged();
    }


    public class HashtagHolder extends RecyclerView.ViewHolder {
        ItemSearchHashtagBinding binding;

        public HashtagHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSearchHashtagBinding.bind(itemView);
        }

        public void setData(int position) {
            Glide.with(binding.getRoot()).load(R.drawable.girl2).into(binding.thumbnail);
            binding.hashtagname.setText(hashtaglist.get(position).getHashtagtext());

            itemView.setOnClickListener(v ->
                    onItemClick.onClick(hashtaglist.get(position).getHashtagtext()));
        }

    }
}
