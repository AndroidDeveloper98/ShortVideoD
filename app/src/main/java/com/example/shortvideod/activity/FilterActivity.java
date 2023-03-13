package com.example.shortvideod.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.daasuu.gpuv.egl.filter.GlFilter;
import com.daasuu.gpuv.player.GPUPlayerView;
import com.example.shortvideod.R;
import com.example.shortvideod.adapter.VideoEffectListAdapter;
import com.example.shortvideod.databinding.ActivityFilterBinding;
import com.example.shortvideod.effect.FilterType;
import com.example.shortvideod.util.BitmapUtil;
import com.example.shortvideod.util.TempUtil;
import com.example.shortvideod.util.VideoUtil;
import com.example.shortvideod.workers.VideoFilterWorker;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class FilterActivity extends BaseActivity {

    public static final String EXTRA_SONG = "song";
    public static final String EXTRA_VIDEO = "video";
    static final String TAG = "FilterActivity";

    FilterActivityViewModel mModel;
    SimpleExoPlayer mPlayer;
    GPUPlayerView mPlayerView;
    String mSong;
    String mVideo;
    ActivityFilterBinding binding;
    GlFilter filter = new GlFilter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_filter);
        mModel = new ViewModelProvider(this).get(FilterActivityViewModel.class);
        mSong = getIntent().getStringExtra(EXTRA_SONG);
        mVideo = getIntent().getStringExtra(EXTRA_VIDEO);
        Log.d(TAG, "onCreate:songid " + mSong);
        Log.d(TAG, "onCreate:video  " + mVideo);

        Bitmap frame = VideoUtil.getFrameAtTime(this.mVideo, TimeUnit.SECONDS.toMicros(3));
        Bitmap square = BitmapUtil.getSquareThumbnail(frame, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
        frame.recycle();
        Bitmap addRoundCorners = BitmapUtil.addRoundCorners(square, 25);
        square.recycle();


        setupViews();

        binding.btnDone.setOnClickListener(v -> submitForFilter());

    }

    private void submitForFilter() {
        Log.d(TAG, "submitForFilter: ");
        this.mPlayer.setPlayWhenReady(false);
        KProgressHUD progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(getString(R.string.progress_title)).setCancellable(false).show();
        File filtered = TempUtil.createNewFile((Context) this, ".mp4");
        Data data = new Data.Builder().putString("input", this.mVideo).putString("output", filtered.getAbsolutePath()).putString("filter", this.mModel.filter.name()).build();
        Log.d(TAG, "submitForFilter: " + this.mModel.filter.name());
        OneTimeWorkRequest request = (OneTimeWorkRequest) ((OneTimeWorkRequest.Builder) new OneTimeWorkRequest.Builder(VideoFilterWorker.class).setInputData(data)).build();
        WorkManager wm = WorkManager.getInstance(this);
        wm.enqueue((WorkRequest) request);
        //wm.getWorkInfoByIdLiveData(request.getId()).observe(this, new FilterActivity$$ExternalSyntheticLambda1(this, progress, filtered));
        wm.getWorkInfoByIdLiveData(request.getId()).observe(this, workInfo -> {
            boolean ended = workInfo.getState() == WorkInfo.State.CANCELLED || workInfo.getState() == WorkInfo.State.FAILED;
            if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                progress.dismiss();
                closeFinally(filtered);
            } else if (ended) {
                progress.dismiss();
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        mPlayerView.onPause();
        mPlayer.setPlayWhenReady(false);
        mPlayer.stop(true);
        mPlayer.release();
        mPlayer = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        File video = new File(this.mVideo);
        if (!video.delete()) {
            Log.w(TAG, "Could not delete input video: " + video);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPlayer = new SimpleExoPlayer.Builder(this).build();
        mPlayer = ExoPlayerFactory.newSimpleInstance(this);
        DefaultDataSourceFactory factory =
                new DefaultDataSourceFactory(this, getString(R.string.app_name));
        ProgressiveMediaSource source = new ProgressiveMediaSource.Factory(factory)
                .createMediaSource(Uri.fromFile(new File(mVideo)));
        mPlayer.setPlayWhenReady(true);
        mPlayer.prepare(source);
        mPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        GPUPlayerView gPUPlayerView = (GPUPlayerView) findViewById(R.id.player);
        mPlayerView = gPUPlayerView;
        mPlayerView.setSimpleExoPlayer(mPlayer);
        mPlayerView.onResume();
    }



    private void closeFinally(File clip) {
        Log.d(TAG, "Filter was successfully applied to " + clip);
        Intent intent = new Intent(this, UploadActivity.class);
        intent.putExtra("song", this.mSong);
        intent.putExtra("video", clip.getAbsolutePath());
        startActivity(intent);
        finish();
    }


    public void setupViews() {
        final List<FilterType> filterTypes = FilterType.createFilterList();

        VideoEffectListAdapter adapter = new VideoEffectListAdapter(this, filterTypes, filterType -> {

            filter = null;
            filter = FilterType.createGlFilter(filterType, this);


            mModel.filter = filterType;
            binding.player.setGlFilter(filter);

        });

        binding.filters.setItemAnimator(new DefaultItemAnimator());
        binding.filters.setAdapter(adapter);
    }


    public static class FilterActivityViewModel extends ViewModel {
        FilterType filter = FilterType.DEFAULT;
    }

}
