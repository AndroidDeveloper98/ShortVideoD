package com.example.shortvideod.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.example.shortvideod.R;
import com.example.shortvideod.activity.CommentPostActivity;
import com.example.shortvideod.adapter.ReelsAdapter;
import com.example.shortvideod.databinding.FragmentHomeBinding;
import com.example.shortvideod.databinding.ItemReelsBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.design.Reels;
import com.example.shortvideod.other.MainApplication;
import com.example.shortvideod.util.Const;
import com.example.shortvideod.viewmodel.ReelsViewModel;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Random;

import io.branch.referral.BranchViewHandler;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class HomeFragment extends Fragment implements Player.EventListener {

    FragmentHomeBinding binding;

    NavController navController;
    int like;

    BottomSheetDialogFragment bottomSheetDialogFragment;
    Animation animation;
    Reels reels;
    SimpleExoPlayer player;
    ItemReelsBinding playerBinding;
    int lastPosition = 0;
    Animation rotateanimation;
    ReelsAdapter reelsAdapter = new ReelsAdapter();

    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        bottomSheetDialogFragment = new GiftBottomSheetFrgament();


        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);

        rotateanimation = AnimationUtils.loadAnimation(binding.getRoot().getContext(), R.anim.slow_rotate);

        navController = Navigation.findNavController(requireActivity(), R.id.host);
        binding.rvReels.setAdapter(reelsAdapter);
        reelsAdapter.addData(Democontents.getReels());
        new PagerSnapHelper().attachToRecyclerView(binding.rvReels);
        binding.rvReels.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                int position;
                View view;
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0 && (position = ((LinearLayoutManager) HomeFragment.this.binding.rvReels.getLayoutManager()).findFirstCompletelyVisibleItemPosition()) > -1 && HomeFragment.this.lastPosition != position && HomeFragment.this.binding.rvReels.getLayoutManager() != null && (view = HomeFragment.this.binding.rvReels.getLayoutManager().findViewByPosition(position)) != null) {
                    int unused = HomeFragment.this.lastPosition = position;
                    ItemReelsBinding binding1 = (ItemReelsBinding) DataBindingUtil.bind(view);
                    if (binding1 != null) {
                        binding1.lytSound.startAnimation(rotateanimation);
                        HomeFragment homeFragment = HomeFragment.this;
                        homeFragment.playVideo(reelsAdapter.getList().get(position).getVideo(), binding1);
                        HomeFragment homeFragment2 = HomeFragment.this;
                        homeFragment2.setThumbnail(reelsAdapter.getList().get(position).getVideo(), binding1);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        reelsAdapter.setOnReelsVideoAdapterListner(new ReelsAdapter.OnReelsVideoAdapterListner(){
            @Override
            public void onItemClick(ItemReelsBinding reelsBinding, int pos, int type) {
                if (type == 1) {
                    int unused = HomeFragment.this.lastPosition = pos;
                    HomeFragment homeFragment = HomeFragment.this;
                    homeFragment.playVideo(reelsAdapter.getList().get(pos).getVideo(), reelsBinding);
                    HomeFragment homeFragment2 = HomeFragment.this;
                    homeFragment2.setThumbnail(reelsAdapter.getList().get(pos).getVideo(), reelsBinding);
                } else if (HomeFragment.this.player == null) {
                } else {
                    if (HomeFragment.this.player.isPlaying()) {
                        HomeFragment.this.player.setPlayWhenReady(false);
                    } else {
                        HomeFragment.this.player.setPlayWhenReady(true);
                    }
                }
            }

            @Override
            public void onDoubleClick(Reels model, MotionEvent event, ItemReelsBinding binding) {
                showHeart(event, binding);
                if (!model.isIslike()) {
                    binding.like.performClick();
                }
            }

            @Override
            public void onClickLike(ItemReelsBinding reelsBinding, int pos) {
                Reels model = reelsAdapter.getList().get(pos);
                boolean isLiked = model.isIslike();
                Log.d("========", "onClickLike: " + isLiked);
                if (model.getLikes() > 0) {
                    if (isLiked) {
                        HomeFragment.this.like = model.getLikes() + 1;
                    } else {
                        HomeFragment.this.like = model.getLikes() - 1;
                    }
                }
                reelsBinding.like.setLiked(Boolean.valueOf(!isLiked));
                reelsBinding.likeCount.setText(String.valueOf(HomeFragment.this.like));
                model.setIslike(!isLiked);
                model.setLikes(HomeFragment.this.like);
                reelsAdapter.notifyItemChanged(pos, model);
            }

            @Override
            public void onClickUser(Reels reel) {
                HomeFragment.this.reels = reel;
                Bundle b = new Bundle();
                b.putString(Const.USERIMAGE, reel.getUser().getImage());
                b.putString(Const.USERNAMELIST, reel.getUser().getName());
                HomeFragment.this.navController.navigate((int) R.id.action_homeFragment_to_userProfileFragment, b);
            }

            @Override
            public void onClickComments(Reels reels) {
                Intent i = new Intent(HomeFragment.this.requireActivity(), CommentPostActivity.class);
                i.putExtra(Const.REELSUSERIMAGE, reels.getUser().getImage());
                i.putExtra(Const.REELSUSERNAME, reels.getUser().getName());
                HomeFragment.this.startActivity(i);
            }

            @Override
            public void onClickShare(Reels reel) {
                share("This is demo app");
            }

            @Override
            public void onClickGift() {
                bottomSheetDialogFragment.show(HomeFragment.this.getChildFragmentManager(), "giftfragment");
            }
        });


        binding.getSuggested.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_followingFragment));


        binding.wallet.setOnClickListener(v ->
                navController.navigate(R.id.action_homeFragment_to_walletFragment));

    }

    public void clickUerProfile() {
        Bundle b = new Bundle();
        b.putString(Const.USERIMAGE, this.reels.getUser().getImage());
        b.putString(Const.USERNAMELIST, this.reels.getUser().getName());
        this.navController.navigate((int) R.id.action_homeFragment_to_userProfileFragment, b);
    }

    public void onClickofUser() {
        this.binding.getSuggested.performClick();
    }

    /* access modifiers changed from: private */
    public void share(String video) {
        Intent share = new Intent("android.intent.action.SEND");
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra("android.intent.extra.SUBJECT", R.string.app_name);
        share.putExtra("android.intent.extra.TEXT", video);
        startActivity(Intent.createChooser(share, "Share link!"));
    }

    public void showHeart(MotionEvent e, final ItemReelsBinding binding2) {
        int x = ((int) e.getX()) + BranchViewHandler.BRANCH_VIEW_ERR_ALREADY_SHOWING;
        int y = ((int) e.getY()) + BranchViewHandler.BRANCH_VIEW_ERR_ALREADY_SHOWING;
        Log.i("TAG", "showHeart: " + x + "------" + y);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-2, -2);
        final ImageView iv = new ImageView(getActivity());
        lp.setMargins(x, y, 0, 0);
        iv.setLayoutParams(lp);
        iv.setRotation((float) (new Random().nextInt(60) + -30));
        iv.setImageResource(R.drawable.ic_heart_gradient);
        if (binding2.rtl.getChildCount() > 0) {
            binding2.rtl.removeAllViews();
        }
        binding2.rtl.addView(iv);
        Animation fadeoutani = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce);
        fadeoutani.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                binding2.rtl.removeView(iv);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        iv.startAnimation(fadeoutani);
    }

    public void setThumbnail(String video, ItemReelsBinding reelsBinding) {
        Glide.with(reelsBinding.getRoot()).load(video).apply((BaseRequestOptions<?>) RequestOptions.bitmapTransform(new BlurTransformation(25, 5))).into(reelsBinding.thumbnailVideo);
    }

    public void playVideo(String videoUrl, ItemReelsBinding binding2) {
        SimpleExoPlayer simpleExoPlayer = this.player;
        if (simpleExoPlayer != null) {
            simpleExoPlayer.removeListener(this);
            this.player.setPlayWhenReady(false);
            this.player.release();
        }
        Log.d("TAG", "playVideo:URL  " + videoUrl);
        this.playerBinding = binding2;
        this.player = new SimpleExoPlayer.Builder(getActivity()).build();
        ProgressiveMediaSource progressiveMediaSource = new ProgressiveMediaSource.Factory(new CacheDataSourceFactory(MainApplication.simpleCache, new DefaultHttpDataSourceFactory(Util.getUserAgent(getActivity(), "TejTok")), CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)).createMediaSource(Uri.parse(videoUrl));
        binding2.playerView.setPlayer(this.player);
        this.player.setPlayWhenReady(true);
        this.player.seekTo(0, 0);
        this.player.setRepeatMode(Player.REPEAT_MODE_ALL);
        this.player.addListener(this);
        binding2.playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
        this.player.prepare(progressiveMediaSource, true, false);
    }




    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        //new Implemention by reverese
        if (playbackState == 2) {
            if (this.playerBinding != null) {
                this.binding.buffering.setVisibility(View.VISIBLE);
            }
        } else if (playbackState == 3 && this.playerBinding != null) {
            this.binding.buffering.setVisibility(View.GONE);
        }
    }

    public void showWallet() {
        navController = Navigation.findNavController(requireActivity(), R.id.host);
        navController.navigate(R.id.action_homeActivityFragment_to_walletFragment);
    }

    @Override
    public void onResume() {
        if (player != null) {
            player.setPlayWhenReady(true);
        }
        super.onResume();
    }

    @Override
    public void onStop() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (player != null) {
            player.setPlayWhenReady(false);
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (player != null) {
            player.setPlayWhenReady(false);
            player.stop();
            player.release();
        }
        super.onDestroy();
    }

}
