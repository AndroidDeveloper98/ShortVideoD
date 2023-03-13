package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemMentionBinding;
import com.example.shortvideod.design.Mentions;

import java.util.ArrayList;
import java.util.List;

public class MentionAdapter extends RecyclerView.Adapter<MentionAdapter.MentionHolder> {
    List<Mentions> mentions = new ArrayList<>();
    Context context;

    @NonNull
    @Override
    public MentionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MentionHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mention, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MentionHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return mentions.size();
    }

    public void addData(List<Mentions> mention) {
        mentions.addAll(mention);
        notifyItemRangeInserted(mentions.size(), mention.size());
    }

    public class MentionHolder extends RecyclerView.ViewHolder {
        ItemMentionBinding binding;

        public MentionHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemMentionBinding.bind(itemView);
        }

        public void setData(int position) {
            Mentions mention = mentions.get(position);

            Glide.with(context).load(mention.getUserimg()).into(binding.userImg);
            binding.mentionbyUser.setText(mention.getMentionuser());
            binding.comment.setText("@Irina_Iriser" + " " + mention.getComment());
            binding.date.setText(mention.getDate());

        }
    }
}
