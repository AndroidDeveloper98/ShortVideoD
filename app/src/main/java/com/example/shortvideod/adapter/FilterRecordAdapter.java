package com.example.shortvideod.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;


public class FilterRecordAdapter extends RecyclerView.Adapter<FilterRecordAdapter.FilterViewHolder> {

    private final Context mContext;
    private final List<VideoFilter> mFilters = Arrays.asList(VideoFilter.values());
    ArrayList<String> filterThumbList = new ArrayList<>();
    private OnFilterSelectListener mListener;
    private int selectedEffect = 0;

    public FilterRecordAdapter(Context context) {
        mContext = context;
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
        FilterViewHolder holder = new FilterViewHolder(view);
        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    @SuppressLint("SetTextI18n")
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        holder.ivselectedeffect.setVisibility(View.GONE);


        Log.d("filter", "onBindViewHolder: " + filterThumbList.get(position));
        Glide.with(mContext).load(filterThumbList.get(position)).into(holder.image);

        if (position == selectedEffect) {
            holder.ivselectedeffect.setVisibility(View.VISIBLE);
        }

        final VideoFilter filter = mFilters.get(position);
        switch (filter) {
            case NONE:
                break;
            case BRIGHTNESS: {
                GPUImageBrightnessFilter glf = new GPUImageBrightnessFilter();
                glf.setBrightness(0.2f);
                break;
            }
            case EXPOSURE:
                break;
            case GAMMA: {
                GPUImageGammaFilter glf = new GPUImageGammaFilter();
                glf.setGamma(2f);
                break;
            }
            case GRAYSCALE:
                break;
            case HAZE: {
                GPUImageHazeFilter glf = new GPUImageHazeFilter();
                glf.setSlope(-0.5f);
                break;
            }
            case INVERT:
                break;
            case MONOCHROME:
                break;
            case PIXELATED: {
                GPUImagePixelationFilter glf = new GPUImagePixelationFilter();
                glf.setPixel(5);
                break;
            }
            case POSTERIZE:
                break;
            case SEPIA:
                break;
            case SHARP: {
                GPUImageSharpenFilter glf = new GPUImageSharpenFilter();
                glf.setSharpness(1f);
                break;
            }
            case SOLARIZE:
                break;
            case VIGNETTE:
                break;
            default:
                break;
        }

        String name = filter.name().toLowerCase(Locale.US);
        holder.name.setText(name.substring(0, 1).toUpperCase() + name.substring(1));
        holder.itemView.setOnClickListener(view -> {
            selectedEffect = position;
            if (mListener != null) {
                mListener.onSelectFilter(filter);
            }
            notifyDataSetChanged();
        });
    }

    public void setListener(OnFilterSelectListener listener) {
        mListener = listener;
    }

    public void setListener(FilterAdapter.OnFilterSelectListener onFilterSelectListener) {

    }

    public interface OnFilterSelectListener {
        void onSelectFilter(VideoFilter filter);
    }

    static class FilterViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView ivselectedeffect;
        TextView name;

        public FilterViewHolder(@NonNull View root) {
            super(root);
            image = root.findViewById(R.id.image);
            ivselectedeffect = root.findViewById(R.id.iv_selected_effect);
            name = root.findViewById(R.id.name);
        }
    }
}
