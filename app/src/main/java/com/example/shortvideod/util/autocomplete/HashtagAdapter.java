package com.example.shortvideod.util.autocomplete;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shortvideod.R;
import com.example.shortvideod.design.HashtagAuto;

import java.util.ArrayList;
import java.util.List;


public class HashtagAdapter extends RecyclerView.Adapter<HashtagAdapter.HashtagViewHolder> {

    private final Context mContext;
    private final OnClickListener mListener;
    private List<HashtagAuto> mItems = new ArrayList<>();

    protected HashtagAdapter(@NonNull Context context, @NonNull OnClickListener listener) {
        mContext = context;
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return mItems == null ? 0 : mItems.size();
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull HashtagViewHolder holder, int position) {
        final HashtagAuto hashtag = mItems.get(position);
        holder.name.setText("#" + hashtag.getHashtag());
        holder.itemView.setOnClickListener(v -> mListener.onHashtagClick(hashtag));
    }

    @NonNull
    @Override
    public HashtagViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext)
                .inflate(R.layout.item_hashtag_slim, parent, false);
        return new HashtagViewHolder(root);
    }

    public void submitData(List<HashtagAuto> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    interface OnClickListener {

        void onHashtagClick(HashtagAuto hashtag);
    }

    public class HashtagViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView clips;

        public HashtagViewHolder(@NonNull View root) {
            super(root);
            name = root.findViewById(R.id.name);
            clips = root.findViewById(R.id.clips);
        }
    }
}
