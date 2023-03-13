package com.example.shortvideod.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.example.shortvideod.R;
import com.example.shortvideod.adapter.SongsAdapter;
import com.example.shortvideod.databinding.ActivitySongPickerBinding;
import com.example.shortvideod.design.Democontents;
import com.example.shortvideod.workers.FileDownloadWorker;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.File;

import nl.changer.audiowife.AudioWife;


public class SongPickerActivity extends BaseActivity {

    private static final String TAG = "SongPickerActivity";


    ActivitySongPickerBinding binding;
    SongsAdapter songsAdapter = new SongsAdapter();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_song_picker);

        progressBar = new ProgressBar(this);

        songsAdapter.setOnSongClickListner(song -> {
            binding.progressbar.setVisibility(View.VISIBLE);
            downloadSelectedSong();
            binding.browse.setVisibility(View.GONE);
        });

        binding.rvSongs.setAdapter(songsAdapter);
        initView();

        songsAdapter.addData(Democontents.getSongFiles());


    }


    private void initView() {
        binding.browse.setVisibility(View.VISIBLE);
        View sheet = findViewById(R.id.song_preview_sheet);
        BottomSheetBehavior<View> bsb = BottomSheetBehavior.from(sheet);
        bsb.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {

            @Override
            public void onStateChanged(@NonNull View sheet, int state) {
                Log.v(TAG, "Song preview sheet state is: " + state);
                if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                    AudioWife.getInstance().release();
                }
            }

            @Override
            public void onSlide(@NonNull View sheet, float offset) {

            }


        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AudioWife.getInstance().release();

    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioWife.getInstance().pause();
    }

    public void downloadSelectedSong() {
        File songs = new File(getFilesDir(), "songs");
        if (!songs.exists() && !songs.mkdirs()) {
            Log.w(TAG, "Could not create directory at " + songs);
        }
        binding.progressbar.setVisibility(View.GONE);


        KProgressHUD progress = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.progress_title))
                .setCancellable(false)
                .show();
        Data input = new Data.Builder()
                .build();
        WorkRequest request = new OneTimeWorkRequest.Builder(FileDownloadWorker.class)
                .setInputData(input)
                .build();
        WorkManager wm = WorkManager.getInstance(this);
        wm.enqueue(request);
        wm.getWorkInfoByIdLiveData(request.getId())
                .observe(this, info -> {
                    Log.d(TAG, "downloadSelectedSong: " + info);
                    boolean ended = info.getState() == WorkInfo.State.CANCELLED
                            || info.getState() == WorkInfo.State.FAILED
                            || info.getState() == WorkInfo.State.SUCCEEDED;
                    if (ended) {
                        progress.dismiss();
                    }

                    if (info.getState() == WorkInfo.State.SUCCEEDED) {
                        //
                    }
                });
    }


}
