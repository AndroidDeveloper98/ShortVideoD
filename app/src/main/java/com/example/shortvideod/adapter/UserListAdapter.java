package com.example.shortvideod.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemSearchUserBinding;
import com.example.shortvideod.design.UserList;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserHolder> {
    List<UserList> userlist = new ArrayList<>();

    OnItemUserClick onItemClick;

    public OnItemUserClick getOnItemClick() {
        return onItemClick;
    }

    public void setOnItemClick(OnItemUserClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public interface OnItemUserClick {
        void onClick(UserList userList);
    }


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_user, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public List<UserList> getList() {
        return userlist;
    }

    public void addData(List<UserList> user) {
        this.userlist.addAll(user);
        notifyItemRangeInserted(this.userlist.size(), user.size());
    }

    public void clear() {
        userlist.clear();
        notifyDataSetChanged();
    }

    public class UserHolder extends RecyclerView.ViewHolder {
        ItemSearchUserBinding binding;

        public UserHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemSearchUserBinding.bind(itemView);
        }

        public void setData(int position) {
            UserList user = userlist.get(position);
            Glide.with(binding.getRoot()).load(user.getImage()).into(binding.thumbnail);
            binding.username.setText(user.getName());
            binding.email.setText(user.getEmail());

            itemView.setOnClickListener(v ->
                    onItemClick.onClick(user));

        }


    }
}
