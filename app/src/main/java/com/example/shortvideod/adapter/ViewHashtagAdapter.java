package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemViewHashtagBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewHashtagAdapter extends RecyclerView.Adapter<ViewHashtagAdapter.HashtagHolder> {
    Context context;
    List<String> hashtag = new ArrayList<>();

    OnHashtagVideoClick onHashtagVideoClick;

    public OnHashtagVideoClick getOnHashtagVideoClick() {
        return onHashtagVideoClick;
    }

    public void setOnHashtagVideoClick(OnHashtagVideoClick onHashtagVideoClick) {
        this.onHashtagVideoClick = onHashtagVideoClick;
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        Glide.with(context).load(hashtag.get(position)).into(holder.binding.videoThumb);

        holder.itemView.setOnClickListener(v ->
                onHashtagVideoClick.onclick());

    }


    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHashtagAdapter.HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_hashtag, parent, false));
    }

    public interface OnHashtagVideoClick {
        void onclick();
    }

    @Override
    public int getItemCount() {
        return hashtag.size();
    }

    public void addData(List<String> hashtagList) {
        hashtag.addAll(hashtagList);
        notifyItemRangeInserted(hashtag.size(), hashtagList.size());
    }


    public class HashtagHolder extends RecyclerView.ViewHolder {
        ItemViewHashtagBinding binding;

        public HashtagHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemViewHashtagBinding.bind(itemView);
        }
    }
}
