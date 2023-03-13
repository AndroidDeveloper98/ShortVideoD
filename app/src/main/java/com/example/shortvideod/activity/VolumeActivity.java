package com.example.shortvideod.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shortvideod.R;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.material.slider.Slider;

import java.io.File;
import java.io.IOException;
import java.util.Locale;


public class VolumeActivity extends BaseActivity implements AnalyticsListener {

    public static final String EXTRA_AUDIO = "audio";
    public static final String EXTRA_SONG = "song";
    public static final String EXTRA_VIDEO = "video";

    static final String TAG = "VolumeActivity";

    final Handler mHandler = new Handler();
    VolumeActivityViewModel mModel;
    SimpleExoPlayer mPlayer1;
    MediaPlayer mPlayer2;
    final Runnable mHandlerCallback = this::stopAudioPlayer;
    String mAudio;
    String mSong;
    String mVideo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);

        ImageView done = findViewById(R.id.imgDone);
        done.setImageResource(R.drawable.ic_baseline_check_24);

        mModel = new ViewModelProvider(this).get(VolumeActivityViewModel.class);
        mAudio = getIntent().getStringExtra(EXTRA_AUDIO);
        mSong = getIntent().getStringExtra(EXTRA_SONG);
        mVideo = getIntent().getStringExtra(EXTRA_VIDEO);
        mModel.audio = false;


        mPlayer1 = new SimpleExoPlayer.Builder(this).build();
        mPlayer1.addAnalyticsListener(this);
        mPlayer2 = new MediaPlayer();

        try {
            mPlayer2.setDataSource(mAudio);
            mPlayer2.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Media player failed to initialize :?", e);
        }

        mPlayer2.setOnCompletionListener(player -> mHandler.removeCallbacks(mHandlerCallback));
        Slider video = findViewById(R.id.video);
        video.setLabelFormatter(value -> String.format(Locale.US, "%d%%", (int) value));
        video.setValue(mModel.video.getValue());
        video.addOnChangeListener((v, value, user) -> mModel.video.postValue(value));
        video.setEnabled(mModel.audio);
        PlayerView player = findViewById(R.id.player);
        player.setPlayer(mPlayer1);
        Slider song = findViewById(R.id.song);
        song.setLabelFormatter(value -> String.format(Locale.US, "%d%%", (int) value));
        song.setValue(mModel.song.getValue());
        song.addOnChangeListener((v, value, user) -> mModel.song.postValue(value));
        View overlay = findViewById(R.id.overlay);
        overlay.setOnClickListener(v -> mPlayer1.setPlayWhenReady(!mPlayer1.getPlayWhenReady()));
        mModel.video.observe(this, volume -> mPlayer1.setVolume(volume / 100));
        mModel.song.observe(this, volume ->
                mPlayer2.setVolume(volume / 100, volume / 100));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopVideoPlayer();
        mPlayer1.release();
        File video = new File(mVideo);
        if (!video.delete()) {
            Log.w(TAG, "Could not delete input video: " + video);
        }

        File audio = new File(mAudio);
        if (!audio.delete()) {
            Log.w(TAG, "Could not delete input audio: " + audio);
        }
    }

    @Override
    public void onIsPlayingChanged(@Nullable EventTime time, boolean playing) {
        if (playing) {
            startAudioPlayer();
        } else {
            stopAudioPlayer();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopVideoPlayer();
    }

    @Override
    public void onPlayerStateChanged(@Nullable EventTime time, boolean ready, int state) {
        if (state == Player.STATE_ENDED) {
            mPlayer1.setPlayWhenReady(false);
            mPlayer1.seekTo(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPlayer1.getPlaybackState() == Player.STATE_IDLE) {
            startVideoPlayer();
        }
    }

    @Override
    public void onSeekProcessed(@Nullable EventTime time) {
        long position1 = mPlayer1.getCurrentPosition();
        long duration1 = mPlayer1.getDuration();
        long duration2 = mPlayer2.getDuration();
        long maximum = Math.min(duration1, duration2);
        if (position1 < maximum) {
            mPlayer2.seekTo((int) position1);
        } else if (mPlayer2.isPlaying()) {
            mPlayer2.pause();
        }
    }


    private void startAudioPlayer() {
        if (mPlayer2.isPlaying()) {
            return;
        }

        long position1 = mPlayer1.getCurrentPosition();
        long duration1 = mPlayer1.getDuration();
        long duration2 = mPlayer2.getDuration();
        long maximum = Math.min(duration1, duration2);
        if (position1 < maximum) {
            mPlayer2.seekTo((int) position1);
            mPlayer2.start();
            mHandler.postDelayed(mHandlerCallback, maximum - position1);
        }
    }

    private void startVideoPlayer() {
        DefaultDataSourceFactory factory =
                new DefaultDataSourceFactory(this, getString(R.string.app_name));
        ProgressiveMediaSource source = new ProgressiveMediaSource.Factory(factory)
                .createMediaSource(Uri.fromFile(new File(mVideo)));
        mPlayer1.setPlayWhenReady(true);
        mPlayer1.seekTo(mModel.window, mModel.position);
        mPlayer1.prepare(source, false, false);
    }

    private void stopAudioPlayer() {
        if (mPlayer2.isPlaying()) {
            mPlayer2.pause();
        }

        mHandler.removeCallbacks(mHandlerCallback);
    }

    private void stopVideoPlayer() {
        mModel.position = mPlayer1.getCurrentPosition();
        mModel.window = mPlayer1.getCurrentWindowIndex();
        mPlayer1.setPlayWhenReady(false);
        mPlayer1.stop();
    }


    public static class VolumeActivityViewModel extends ViewModel {
        boolean audio;
        MutableLiveData<Float> song = new MutableLiveData<>(100.0f);
        MutableLiveData<Float> video = new MutableLiveData<>(100.0f);
        long position;
        int window;
    }
}
