package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemCommentlistBinding;
import com.example.shortvideod.design.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    List<Comment> comments = new ArrayList<>();
    Context context;
    boolean issingle = false;

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CommentHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commentlist, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        holder.setData(position);
    }

    public void addSingleMessage(Comment dataItem) {
        comments.add(dataItem);
        notifyItemInserted(comments.size() - 1);
        issingle = true;
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }

    public void addData(List<Comment> comment) {
        comments.addAll(comment);
        notifyItemRangeInserted(comments.size(), comment.size());
    }

    public class CommentHolder extends RecyclerView.ViewHolder {
        ItemCommentlistBinding binding;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemCommentlistBinding.bind(itemView);
        }

        public void setData(int position) {
            Comment comment = comments.get(position);

            Glide.with(context).load(comment.getUserimg()).into(binding.userImg);
            binding.comment.setText(comment.getComm());
            binding.date.setText(comment.getDate());
            binding.username.setText(comment.getUsername());

        }
    }
}
