package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemDotsBinding;


public class DotAdapter extends RecyclerView.Adapter<DotAdapter.DotViewHolder> {
    final int slides;
    Context context;
    int color;
    int selctedPos = 0;

    public DotAdapter(int slides) {
        this.slides = slides;
    }

    @NonNull
    @Override
    public DotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new DotViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dots, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DotViewHolder holder, int position) {
        if (selctedPos == position) {
            holder.binding.dot.setVisibility(View.GONE);
            holder.binding.selectDots.setVisibility(View.VISIBLE);
        } else {
            holder.binding.dot.setVisibility(View.VISIBLE);
            holder.binding.selectDots.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return slides;
    }

    public void changePos(int pos) {
        selctedPos = pos;
        notifyDataSetChanged();
    }


    public class DotViewHolder extends RecyclerView.ViewHolder {
        ItemDotsBinding binding;

        public DotViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDotsBinding.bind(itemView);
        }
    }
}

