package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemMentionsBinding;
import com.example.shortvideod.design.Mention;

import java.util.ArrayList;
import java.util.List;


public class MentionsAdapter extends RecyclerView.Adapter<MentionsAdapter.MentionViewHolder> {
    OnMentionssClickLisnter onMentionssClickLisnter;
    private Context context;
    private List<Mention> mentions = new ArrayList<>();

    public OnMentionssClickLisnter getOnHashtagsClickLisnter() {
        return onMentionssClickLisnter;
    }

    public void setOnHashtagsClickLisnter(OnMentionssClickLisnter onMentionssClickLisnter) {
        this.onMentionssClickLisnter = onMentionssClickLisnter;
    }

    @NonNull
    @Override
    public MentionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MentionViewHolder(LayoutInflater.from(context).inflate(R.layout.item_mentions, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MentionViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mentions.size();
    }

    public void addData(List<Mention> hashtags) {
        this.mentions.addAll(hashtags);
        notifyItemRangeInserted(this.mentions.size(), hashtags.size());
    }

    public void clear() {
        this.mentions.clear();
        notifyDataSetChanged();
    }

    public interface OnMentionssClickLisnter {
        void onHashtagClick(Mention user);
    }

    public class MentionViewHolder extends RecyclerView.ViewHolder {
        ItemMentionsBinding binding;

        public MentionViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemMentionsBinding.bind(itemView);

        }

        public void setData(int position) {
            binding.tvHashtag.setText(mentions.get(position).getUsername());
            Glide.with(context).load(mentions.get(position).getImage())
                    .circleCrop().into(binding.imageview);
            binding.getRoot().setOnClickListener(v -> onMentionssClickLisnter.onHashtagClick(mentions.get(position)));

        }
    }
}
