package com.example.shortvideod.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.shortvideod.R;
import com.example.shortvideod.activity.trimmer.TimeBar;
import com.example.shortvideod.activity.trimmer.TrimTimeBar;
import com.example.shortvideod.util.Const;
import com.example.shortvideod.util.TempUtil;
import com.example.shortvideod.util.TextFormatUtil;
import com.example.shortvideod.util.VideoUtil;
import com.example.shortvideod.workers.VideoTrimmerWorker3;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.source.ClippingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;
import java.util.concurrent.TimeUnit;


public class TrimmerActivity extends BaseActivity implements AnalyticsListener, TimeBar.Listener {

    public static final String EXTRA_AUDIO = "audio";
    public static final String EXTRA_SONG = "song";
    public static final String EXTRA_VIDEO = "video";
    static final String TAG = "TrimmerActivity";
    final Handler mHandler = new Handler(Looper.getMainLooper());
    private TrimTimeBar trimTimeBar;
    public TrimTimeBar mTimeBar;
    String mAudio;
    int mDuration = 0;
    SimpleExoPlayer mPlayer;
    int mTrimEndTime = 0;
    int mTrimStartTime = 0;
    String mSong;
    String mVideo;
    public final Runnable mProgress = new Runnable() {
        public void run() {
            if (TrimmerActivity.this.mPlayer != null && TrimmerActivity.this.mPlayer.isPlaying()) {
                TrimmerActivity.this.mTimeBar.setTime(TrimmerActivity.this.mTrimStartTime + ((int) TrimmerActivity.this.mPlayer.getCurrentPosition()), TrimmerActivity.this.mDuration, TrimmerActivity.this.mTrimStartTime, TrimmerActivity.this.mTrimEndTime);
            }
            TrimmerActivity.this.mHandler.postDelayed(TrimmerActivity.this.mProgress, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trimmer);

        ImageView done = findViewById(R.id.check);
        done.setImageResource(R.drawable.ic_baseline_check_24);
        done.setOnClickListener(v ->  commitSelection());
        mAudio = getIntent().getStringExtra(EXTRA_AUDIO);
        mSong = getIntent().getStringExtra(EXTRA_SONG);
        mVideo = getIntent().getStringExtra(EXTRA_VIDEO);
        mPlayer = new SimpleExoPlayer.Builder(this).build();
        mPlayer.addAnalyticsListener(this);
        mPlayer.setRepeatMode(Player.REPEAT_MODE_ALL);
        PlayerView player = findViewById(R.id.player);
        player.setPlayer(mPlayer);
        int duration = (int) VideoUtil.getDuration(this, Uri.parse(this.mVideo));
        this.mDuration = duration;
        this.mTrimStartTime = 0;
        this.mTrimEndTime = Math.min(duration, (int) Const.MAX_DURATION);
        Log.v(TAG, "Duration of video is " + this.mDuration + "ms.");
        TrimTimeBar trimTimeBar = new TrimTimeBar(this, this);
        this.mTimeBar = trimTimeBar;
        trimTimeBar.setTime(0, this.mDuration, 0, this.mTrimEndTime);
        ((LinearLayout) findViewById(R.id.trimmer)).addView(this.mTimeBar);
        startPlayer();

    }

    public void onClickBack(View view){
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPlayer.setPlayWhenReady(false);
        if (mPlayer.isPlaying()) {
            mPlayer.stop(true);
        }

        mPlayer.release();
        mPlayer = null;
        File video = new File(mVideo);
        if (!video.delete()) {
            Log.w(TAG, "Could not delete input video: " + video);
        }
    }


    @Override
    public void onScrubbingEnd(int time, int start, int end) {
        Log.v(TAG, "Scrub position is " + start + "ms to " + end + "ms.");
        this.mTrimStartTime = start;
        this.mTrimEndTime = end;
        startPlayer();
    }

    @Override
    public void onScrubbingMove(int i) {

    }

    @Override
    public void onScrubbingStart() {
        this.mPlayer.setPlayWhenReady(false);
    }

    private void closeFinally(File file) {
        if (TextUtils.isEmpty(this.mAudio)) {
            Intent intent = new Intent(this, FilterActivity.class);
            intent.putExtra("video", file.getAbsolutePath());
            startActivity(intent);
            finish();
            return;
        }
        Intent intent2 = new Intent(this, VolumeActivity.class);
        intent2.putExtra("audio", this.mAudio);
        intent2.putExtra("song", this.mSong);
        intent2.putExtra("video", file.getAbsolutePath());
        startActivity(intent2);
        finish();
    }

    private void commitSelection() {
        int duration = this.mTrimEndTime - this.mTrimStartTime;
        if (((long) duration) > Const.MAX_DURATION) {
            Toast.makeText(this, getString(R.string.message_trim_too_long, new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(Const.MAX_DURATION))}), Toast.LENGTH_SHORT).show();
        } else if (((long) duration) < Const.MIN_DURATION) {
            Toast.makeText(this, getString(R.string.message_trim_too_short, new Object[]{Long.valueOf(TimeUnit.MILLISECONDS.toSeconds(Const.MIN_DURATION))}), Toast.LENGTH_SHORT).show();
        } else {
            submitForTrim();
        }
    }

    private void startPlayer() {
        this.mPlayer.setPlayWhenReady(true);
        SimpleExoPlayer simpleExoPlayer = this.mPlayer;
        simpleExoPlayer.prepare(new ClippingMediaSource(new ProgressiveMediaSource.Factory(new DefaultDataSourceFactory((Context) this, getString(R.string.app_name))).createMediaSource(Uri.fromFile(new File(this.mVideo))), TimeUnit.MILLISECONDS.toMicros((long) this.mTrimStartTime), TimeUnit.MILLISECONDS.toMicros((long) this.mTrimEndTime)));
        this.mHandler.removeCallbacks(this.mProgress);
        this.mHandler.postDelayed(this.mProgress, 100);
        ((TextView) findViewById(R.id.selection)).setText(TextFormatUtil.toMMSS((long) (this.mTrimEndTime - this.mTrimStartTime)));
    }

    private void submitForTrim() {
        this.mPlayer.setPlayWhenReady(false);
        KProgressHUD progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(getString(R.string.progress_title)).setCancellable(false).show();
        WorkManager wm = WorkManager.getInstance(this);
        File trimmed = TempUtil.createNewFile((Context) this, ".mp4");
        OneTimeWorkRequest request = (OneTimeWorkRequest) ((OneTimeWorkRequest.Builder) new OneTimeWorkRequest.Builder(VideoTrimmerWorker3.class).setInputData(new Data.Builder().putString("input", this.mVideo).putString("output", trimmed.getAbsolutePath()).putLong("start", (long) this.mTrimStartTime).putLong("end", (long) this.mTrimEndTime).build())).build();
        wm.enqueue((WorkRequest) request);
        //wm.getWorkInfoByIdLiveData(request.getId()).observe(this, new TrimmerActivity$$ExternalSyntheticLambda1(this, progress, trimmed));
        wm.getWorkInfoByIdLiveData(request.getId()).observe(this, workInfo -> {
            mo4684x3a7e5c61(progress, trimmed, workInfo);
        });
    }

    /* renamed from: lambda$submitForTrim$1$com-example-shortvideod-activity-TrimmerActivity */
    public void mo4684x3a7e5c61(KProgressHUD progress, File trimmed, WorkInfo info2) {
        boolean ended = info2.getState() == WorkInfo.State.CANCELLED || info2.getState() == WorkInfo.State.FAILED;
        if (info2.getState() == WorkInfo.State.SUCCEEDED) {
            progress.dismiss();
            closeFinally(trimmed);
        } else if (ended) {
            progress.dismiss();
        }
    }
}
