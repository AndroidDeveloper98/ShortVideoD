package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemLocationBinding;
import com.example.shortvideod.design.LocationList;

import java.util.ArrayList;
import java.util.List;


public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    OnLocationClickLisnter onLocationClickLisnter;
    Context context;
    List<LocationList> locations = new ArrayList<>();

    public OnLocationClickLisnter getOnLocationClickLisnter() {
        return onLocationClickLisnter;
    }

    public void setOnLocationClickLisnter(OnLocationClickLisnter onLocationClickLisnter) {
        this.onLocationClickLisnter = onLocationClickLisnter;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new LocationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_location, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public void addData(List<LocationList> locations) {

        this.locations.addAll(locations);
        notifyItemRangeInserted(this.locations.size(), locations.size());
    }

    public void clear() {
        this.locations.clear();
        notifyDataSetChanged();
    }

    public interface OnLocationClickLisnter {
        void onLocationclick(LocationList hashtag);
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        ItemLocationBinding binding;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemLocationBinding.bind(itemView);
        }

        public void setData(int position) {
            LocationList location = locations.get(position);

            binding.tvLocation.setText(location.getContry());

            binding.getRoot().setOnClickListener(v -> onLocationClickLisnter.onLocationclick(locations.get(position)));
        }
    }
}
