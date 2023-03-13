package com.example.shortvideod.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shortvideod.R;
import com.example.shortvideod.activity.WebActivity;
import com.example.shortvideod.databinding.ItemReelsBinding;
import com.example.shortvideod.design.Reels;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.List;

public class ReelsAdapter extends RecyclerView.Adapter<ReelsAdapter.ReelsViewHolder> {

    OnReelsVideoAdapterListner onReelsVideoAdapterListner;
    private List<Reels> reels = new ArrayList<>();
    private int playAtPosition = 0;
    Context context;

    @Override
    public ReelsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ReelsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reels, parent, false));
    }

    public OnReelsVideoAdapterListner getOnReelsVideoAdapterListner() {
        return onReelsVideoAdapterListner;
    }

    public void setOnReelsVideoAdapterListner(OnReelsVideoAdapterListner onReelsVideoAdapterListner) {
        this.onReelsVideoAdapterListner = onReelsVideoAdapterListner;
    }

    @Override
    public void onBindViewHolder(ReelsViewHolder holder, int position) {

        holder.setData(position);
    }

    @Override
    public int getItemCount() {
        return reels.size();
    }

    public List<Reels> getList() {
        return reels;
    }

    public void addData(List<Reels> reels) {
        this.reels.addAll(reels);
        notifyItemRangeInserted(this.reels.size(), reels.size());
    }

    public interface OnReelsVideoAdapterListner {
        void onItemClick(ItemReelsBinding reelsBinding, int pos, int type);

        void onDoubleClick(Reels model, MotionEvent event, ItemReelsBinding binding);

        void onClickLike(ItemReelsBinding reelsBinding, int pos);

        void onClickUser(Reels reel);

        void onClickComments(Reels reels);

        void onClickShare(Reels reel);

        void onClickGift();
    }

    public class ReelsViewHolder extends RecyclerView.ViewHolder {
        ItemReelsBinding binding;

        public ReelsViewHolder(View itemView) {
            super(itemView);
            binding = ItemReelsBinding.bind(itemView);
        }

        public void setData(int position) {
            Reels reel = reels.get(position);
            Glide.with(binding.getRoot()).load(reel.getUser().getImage()).into(binding.thumbnail);
            binding.username.setText(reel.getUser().getName());
            binding.email.setText(reel.getEmail());
            binding.bio.setText(reel.getCaption());
            binding.songName.setText("Sound Name..");
            binding.likeCount.setText(String.valueOf(reel.getLikes()));
            binding.commentCount.setText(String.valueOf(reel.getComments()));

            if (position == playAtPosition) {
                Animation animation = AnimationUtils.loadAnimation(binding.getRoot().getContext(), R.anim.slow_rotate);
                onReelsVideoAdapterListner.onItemClick(binding, playAtPosition, 1);
                binding.lytSound.startAnimation(animation);
            }

            if (position % 3 == 0) binding.shopLay.setVisibility(View.VISIBLE);
            else binding.shopLay.setVisibility(View.GONE);

            binding.thumbnail.setOnClickListener(v -> onReelsVideoAdapterListner.onClickUser(reel));
            binding.comment.setOnClickListener(v -> onReelsVideoAdapterListner.onClickComments(reel));

            binding.giftLay.setOnClickListener(v ->
                    onReelsVideoAdapterListner.onClickGift());


            binding.comment.setOnClickListener(v ->
                    onReelsVideoAdapterListner.onClickComments(reel));


            binding.share.setOnClickListener(v ->
                    onReelsVideoAdapterListner.onClickShare(reel));


            binding.like.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    onReelsVideoAdapterListner.onClickLike(binding, position);
                }

                @Override
                public void unLiked(LikeButton likeButton) {
                    onReelsVideoAdapterListner.onClickLike(binding, position);
                }
            });

            binding.shopLay.setOnClickListener(v -> {
                Intent i = new Intent(context, WebActivity.class);
                context.startActivity(i);
            });

            binding.playerView.setOnTouchListener(new View.OnTouchListener() {
                GestureDetector gestureDetector = new GestureDetector(binding.getRoot().getContext(), new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        Log.d("TAGA", "onSingleTapUp: ");

                        return true;
                    }

                    @Override
                    public void onShowPress(MotionEvent e) {
                        Log.d("TAGA", "onShowPress: ");
                        super.onShowPress(e);
                    }

                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
                        Log.d("TAGA", "onSingleTapConfirmed: ");
                        onReelsVideoAdapterListner.onItemClick(binding, position, 2);
                        return super.onSingleTapConfirmed(e);
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        super.onLongPress(e);
                    }

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        Log.d("TAGA", "onDoubleTap: ");
                        onReelsVideoAdapterListner.onDoubleClick(reel, e, binding);
                        return true;
                    }
                });

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    gestureDetector.onTouchEvent(event);
                    return false;
                }
            });

        }
    }

}
