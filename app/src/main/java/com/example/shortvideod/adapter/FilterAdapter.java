package com.example.shortvideod.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.filter.VideoFilter;

import java.util.ArrayList;
import java.util.List;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private final Context mContext;
    private List<String> mFilters = new ArrayList<>();

    public FilterAdapter(Context mContext, List<String> filterlist) {
        this.mContext = mContext;
        this.mFilters = filterlist;
    }

    @Override
    public int getItemCount() {
        return mFilters.size();
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_filter, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        Glide.with(mContext).load(mFilters.get(position)).into(holder.image);
        holder.itemView.setOnClickListener(view -> {
            //
        });
    }


    public interface OnFilterSelectListener {

        void onSelectFilter(VideoFilter filter);
    }

    static class FilterViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;

        public FilterViewHolder(@NonNull View root) {
            super(root);
            image = root.findViewById(R.id.image);
            name = root.findViewById(R.id.name);
        }
    }
}
