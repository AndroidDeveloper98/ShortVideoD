package com.example.shortvideod.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemPostVideoBinding;
import com.example.shortvideod.design.UserPostVideo;

import java.util.ArrayList;
import java.util.List;

public class UserPostVideoAdapter extends RecyclerView.Adapter<UserPostVideoAdapter.PostVideoViewHolder> {

    private List<UserPostVideo> userPost = new ArrayList<>();

    OnItemUserVideoClick onItemUserVideoClick;

    public OnItemUserVideoClick getOnItemUserVideoClick() {
        return onItemUserVideoClick;
    }

    public void setOnItemUserVideoClick(OnItemUserVideoClick onItemUserVideoClick) {
        this.onItemUserVideoClick = onItemUserVideoClick;
    }


    public interface OnItemUserVideoClick {
        void onClick();
    }


    @Override
    public PostVideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PostVideoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post_video, parent, false));
    }


    @Override
    public void onBindViewHolder(PostVideoViewHolder holder, int position) {

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return userPost.size();
    }

    public List<UserPostVideo> getList() {
        return userPost;
    }

    public void addData(List<UserPostVideo> userpost) {
        this.userPost.addAll(userpost);
        notifyItemRangeInserted(this.userPost.size(), userpost.size());
    }


    public class PostVideoViewHolder extends RecyclerView.ViewHolder {
        ItemPostVideoBinding binding;

        public PostVideoViewHolder(View itemView) {
            super(itemView);
            binding = ItemPostVideoBinding.bind(itemView);
        }

        public void setData(int position) {
            UserPostVideo post = userPost.get(position);


            Glide.with(binding.getRoot()).load(post.getImg()).into(binding.postThumbnail);

            if (post.getBio().equals("")) {
                binding.bio.setVisibility(View.GONE);
            } else {
                binding.bio.setVisibility(View.VISIBLE);
                binding.bio.setText(post.getBio());
            }

            itemView.setOnClickListener(v ->
                    onItemUserVideoClick.onClick());


        }
    }

}
