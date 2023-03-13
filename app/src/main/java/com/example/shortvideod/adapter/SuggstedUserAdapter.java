package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemFollowingListBinding;
import com.example.shortvideod.design.SuggestedUser;

import java.util.ArrayList;
import java.util.List;

public class SuggstedUserAdapter extends RecyclerView.Adapter<SuggstedUserAdapter.SuggestHolder> {
    List<SuggestedUser> userlist = new ArrayList<>();
    Context context;

    OnClickUser onClickUser;

    public OnClickUser getOnClickUser() {
        return onClickUser;
    }

    public void setOnClickUser(OnClickUser onClickUser) {
        this.onClickUser = onClickUser;
    }

    public interface OnClickUser {
        void onClick(SuggestedUser suggestedUser);
    }


    public SuggstedUserAdapter() {

    }

    @NonNull
    @Override
    public SuggestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SuggstedUserAdapter.SuggestHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_following_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public List<SuggestedUser> getList() {
        return userlist;
    }

    public void addData(List<SuggestedUser> user) {
        this.userlist.addAll(user);
        notifyItemRangeInserted(this.userlist.size(), user.size());
    }

    public class SuggestHolder extends RecyclerView.ViewHolder {
        ItemFollowingListBinding binding;

        public SuggestHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemFollowingListBinding.bind(itemView);
        }

        public void setData(int position) {
            SuggestedUser user = userlist.get(position);
            Glide.with(binding.getRoot()).load(user.getImage()).into(binding.thumbnail);
            binding.username.setText(user.getName());
            binding.email.setText(user.getEmail());


            binding.btnFollow.setOnClickListener(v -> {
                if (binding.btnFollow.getText().toString().equalsIgnoreCase("follow")) {
                    binding.btnFollow.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_following));
                    binding.btnFollow.setText(R.string.following);
                }
            });


            binding.thumbnail.setOnClickListener(v ->
                    onClickUser.onClick(user));


        }
    }
}
