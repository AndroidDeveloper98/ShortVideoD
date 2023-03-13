package com.example.shortvideod.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ItemVideoEffectBinding;
import com.example.shortvideod.effect.FilterType;
import com.example.shortvideod.util.Const;

import java.util.ArrayList;
import java.util.List;


public class VideoEffectListAdapter extends RecyclerView.Adapter<VideoEffectListAdapter.PlaceViewHolder> {

    List<FilterType> shaders;
    VideoEffectAdapterListener listener;
    ArrayList<String> filterThumbList = new ArrayList<>();
    private Context context;
    private int selectedEffect = 0;

    public VideoEffectListAdapter(Context context, List<FilterType> shaders, VideoEffectAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.shaders = shaders;
        this.filterThumbList = Const.listAssetFilterThumb(context);
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {

        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_effect,
                parent, false);
        return new PlaceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PlaceViewHolder holder, final int position) {

        holder.binding.ivSelectedEffect.setVisibility(View.GONE);

        Glide.with(context).load(filterThumbList.get(position)).into(holder.binding.ivVideoEffect);

        if (position == selectedEffect) {
            holder.binding.ivSelectedEffect.setVisibility(View.VISIBLE);
        }

        holder.binding.tvEffectName.setText(shaders.get(position).name());

        holder.itemView.setOnClickListener(view -> {
            /*selectedEffect = position;
            listener.onVideoEffectSelected(shaders.get(position));
            notifyDataSetChanged();*/
            int unused = VideoEffectListAdapter.this.selectedEffect = position;
            VideoEffectListAdapter.this.listener.onVideoEffectSelected(VideoEffectListAdapter.this.shaders.get(position));
            VideoEffectListAdapter.this.notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return shaders.size();
    }

    public interface VideoEffectAdapterListener {
        void onVideoEffectSelected(FilterType filterType);
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder {

        ItemVideoEffectBinding binding;

        public PlaceViewHolder(View itemView) {
            super(itemView);
            binding = ItemVideoEffectBinding.bind(itemView);
        }
    }
}
