package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemChatUserListBinding;
import com.example.shortvideod.design.ChatUser;

import java.util.ArrayList;
import java.util.List;

public class ChatUserListAdapter extends RecyclerView.Adapter<ChatUserListAdapter.HashtagHolder> {
    List<ChatUser> userlist = new ArrayList<>();
    OnItemClick onItemClick;
    Context context;

    public OnItemClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public HashtagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new HashtagHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_user_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HashtagHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public List<ChatUser> getList() {
        return userlist;
    }

    public void addData(List<ChatUser> user) {
        this.userlist.addAll(user);
        notifyItemRangeInserted(this.userlist.size(), user.size());
    }

    public interface OnItemClick {
        void onClick(int pos);

        void onProfile(int pos);
    }

    public class HashtagHolder extends RecyclerView.ViewHolder {
        ItemChatUserListBinding binding;

        public HashtagHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemChatUserListBinding.bind(itemView);
        }

        public void setData(int position) {
            ChatUser chatUser = userlist.get(position);

            if (userlist.get(position).isIsonline()) {
                binding.onlineView.setVisibility(View.VISIBLE);
                binding.offlineView.setVisibility(View.GONE);
                binding.msgLay.setVisibility(View.VISIBLE);
            } else {
                binding.onlineView.setVisibility(View.GONE);
                binding.offlineView.setVisibility(View.VISIBLE);
                binding.msgLay.setVisibility(View.GONE);
            }

            Glide.with(binding.getRoot()).load(chatUser.getImg()).circleCrop().into(binding.userImg);
            binding.name.setText(chatUser.getUsername());
            binding.time.setText(chatUser.getTime());
            binding.lastMsg.setText(chatUser.getMessage());

            binding.chatName.setOnClickListener(v ->
                    onItemClick.onClick(position));


            binding.profile.setOnClickListener(v ->
                    onItemClick.onProfile(position));
        }

    }
}
