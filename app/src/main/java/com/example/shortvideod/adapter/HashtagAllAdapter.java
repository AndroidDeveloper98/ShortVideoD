package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemHashtagRecyclerBinding;
import com.example.shortvideod.design.Democontents;

public class HashtagAllAdapter extends RecyclerView.Adapter<HashtagAllAdapter.HashtagHolder> {
    Context context;
    HashAdapter hashtagAdapter;
    OnViewAllClick onViewAllClick;

    public OnViewAllClick getOnViewAllClick() {
        return onViewAllClick;
    }

    public void setOnViewAllClick(OnViewAllClick onViewAllClick) {
        this.onViewAllClick = onViewAllClick;
    }


    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HashtagAllAdapter.HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hashtag_recycler, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        hashtagAdapter = new HashAdapter();
        holder.binding.rvSkills.setAdapter(hashtagAdapter);

        hashtagAdapter.setOnHashtagClick(() ->
                onViewAllClick.onVideoClick());


        if (position == 0) {
            hashtagAdapter.addData(Democontents.getClothHashtag());
            holder.binding.hashtagImg.setImageResource(R.drawable.cloth1);
            holder.binding.hashtagBg.setImageResource(R.drawable.skill_shape_pink_10r);
            holder.binding.txthashtag.setText("Skill");

        } else if (position == 1) {
            hashtagAdapter.addData(Democontents.getSkillHashtag());
            holder.binding.hashtagImg.setImageResource(R.drawable.girl2);
            holder.binding.hashtagBg.setImageResource(R.drawable.skill_shape_purple);
            holder.binding.txthashtag.setText("Dance");

        } else if (position == 2) {
            hashtagAdapter.addData(Democontents.getClothHashtag());
            holder.binding.hashtagImg.setImageResource(R.drawable.cloth1);
            holder.binding.hashtagBg.setImageResource(R.drawable.skill_shape_pink_10r);
            holder.binding.txthashtag.setText("Fashion");

        } else if (position == 3) {
            hashtagAdapter.addData(Democontents.getSkillHashtag());
            holder.binding.hashtagImg.setImageResource(R.drawable.girl2);
            holder.binding.hashtagBg.setImageResource(R.drawable.skill_shape_purple);
            holder.binding.txthashtag.setText("SelfLove");

        }
        holder.binding.viewAll.setOnClickListener(v ->
                onViewAllClick.onClick(holder.binding.txthashtag.getText().toString()));


    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public interface OnViewAllClick {
        void onClick(String hashtag);

        void onVideoClick();
    }


    public class HashtagHolder extends RecyclerView.ViewHolder {
        ItemHashtagRecyclerBinding binding;

        public HashtagHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemHashtagRecyclerBinding.bind(itemView);
        }
    }
}
