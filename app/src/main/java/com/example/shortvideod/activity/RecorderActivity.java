package com.example.shortvideod.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import com.daasuu.gpuv.egl.filter.GlFilter;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.segmentedprogressbar.ProgressBarListener;
import com.example.segmentedprogressbar.SegmentedProgressBar;
import com.example.shortvideod.R;
import com.example.shortvideod.adapter.FilterAdapter;
import com.example.shortvideod.adapter.FilterRecordAdapter;
import com.example.shortvideod.databinding.ActivityRecorderBinding;
import com.example.shortvideod.design.Song;
import com.example.shortvideod.design.Sticker;
import com.example.shortvideod.design.StickerView;
import com.example.shortvideod.filter.VideoFilter;
import com.example.shortvideod.popupbuilder.PopupBuilder;
import com.example.shortvideod.util.AnimationUtil;
import com.example.shortvideod.util.Const;
import com.example.shortvideod.util.IntentUtil;
import com.example.shortvideod.util.TempUtil;
import com.example.shortvideod.util.TextFormatUtil;
import com.example.shortvideod.util.VideoUtil;
import com.example.shortvideod.workers.FileDownloadWorker;
import com.example.shortvideod.workers.MergeVideosWorker2;
import com.example.shortvideod.workers.VideoSpeedWorker2;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.slider.Slider;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.munon.turboimageview.TurboImageView;
import com.otaliastudios.cameraview.CameraException;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraOptions;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.VideoResult;
import com.otaliastudios.cameraview.controls.Flash;
import com.otaliastudios.cameraview.controls.Mode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.hoang8f.android.segmented.SegmentedGroup;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by rahul on 28-11-2022
 */
public class RecorderActivity extends BaseActivity{
    public static final String EXTRA_AUDIO = "audio";
    public static final String EXTRA_SONG = "song";
    private static final String TAG = "RecorderActivity";
    private GlFilter GFilter = new GlFilter();
    ActivityRecorderBinding binding;
    private GlFilter filter = new GlFilter();
    StickerView mCurrentView;
    /* access modifiers changed from: private */
    Handler mHandler = new Handler(Looper.getMainLooper());
    /* access modifiers changed from: private */
    public MediaPlayer mMediaPlayer;
    /* access modifiers changed from: private */
    public RecorderActivityViewModel mModel;
    /* access modifiers changed from: private */
    public KProgressHUD mProgress;
    /* access modifiers changed from: private */
    public YoYo.YoYoString mPulse;
    /* access modifiers changed from: private */
    public final Runnable mStopper = new Runnable() {
        @Override
        public void run() {

        }
    };
    /* access modifiers changed from: private */
    public ArrayList<View> mViews = new ArrayList<>();
    File merged;
    File outputPathOverlay;
    int timeInSeconds = 0;

    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(-1);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(TAG, "Received request: " + requestCode + ", result: " + resultCode + ".");
        if (requestCode == 100 && resultCode == -1 && data != null) {
            submitUpload(data.getData());
        } else if (requestCode == 60605 && resultCode == -1 && data != null) {
            setupSong((Song) data.getParcelableExtra("song"), (Uri) data.getParcelableExtra("audio"));
        } else if (requestCode == 60607 && resultCode == -1 && data != null) {
            Sticker stickerDummy = (Sticker) data.getParcelableExtra(StickerPickerActivity.EXTRA_STICKER);
            Log.d(TAG, "onActivityResult: " + stickerDummy.getImage());
            downloadSticker(stickerDummy);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void downloadSticker(Sticker stickerDummy) {
        File stickers = new File(getFilesDir(), "stickers");
        if (!stickers.exists() && !stickers.mkdirs()) {
            Log.w(TAG, "Could not create directory at " + stickers);
        }
        String extension = stickerDummy.getImage().substring(stickerDummy.getImage().lastIndexOf(".") + 1);
        File image = new File(stickers, stickerDummy.getId() + extension);
        if (image.exists()) {
            addSticker(image);
            return;
        }
        KProgressHUD progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(getString(R.string.progress_title)).setCancellable(false).show();
        WorkRequest request = ((OneTimeWorkRequest.Builder) new OneTimeWorkRequest.Builder(FileDownloadWorker.class).setInputData(new Data.Builder().putString("input", stickerDummy.getImage()).putString("output", image.getAbsolutePath()).build())).build();
        WorkManager wm = WorkManager.getInstance(this);
        wm.enqueue(request);
        //wm.getWorkInfoByIdLiveData(request.getId()).observe(this, new RecorderActivity$$ExternalSyntheticLambda12(this, progress, image));
        wm.getWorkInfoByIdLiveData(request.getId()).observe(this, workInfo -> {
            processWorkerInfo(progress, image, workInfo);
        });
    }

    public void processWorkerInfo(KProgressHUD progress, File image, WorkInfo info2) {
        if (info2.getState() == WorkInfo.State.CANCELLED || info2.getState() == WorkInfo.State.FAILED || info2.getState() == WorkInfo.State.SUCCEEDED) {
            progress.dismiss();
        }
        if (info2.getState() == WorkInfo.State.SUCCEEDED) {
            addSticker(image);
        }
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                this.mMediaPlayer.stop();
            }
            this.mMediaPlayer.release();
            this.mMediaPlayer = null;
        }
        for (RecordSegment segment : this.mModel.segments) {
            File file = new File(segment.file);
            if (!file.delete()) {
                Log.v(TAG, "Could not delete record segment file: " + file);
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void addSticker(File file) {
        ((TurboImageView) findViewById(R.id.stickerTurbo)).addObject((Context) this, BitmapFactory.decodeFile(file.getAbsolutePath()));
        findViewById(R.id.remove).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = (ActivityRecorderBinding) DataBindingUtil.setContentView(this, R.layout.activity_recorder);
        this.mModel = (RecorderActivityViewModel) new ViewModelProvider(this).get(RecorderActivityViewModel.class);
        Song songDummy = (Song) getIntent().getParcelableExtra("song");
        Uri audio = (Uri) getIntent().getParcelableExtra("audio");
        if (audio != null) {
            setupSong(songDummy, audio);
        }

        FilterRecordAdapter adapter = new FilterRecordAdapter(RecorderActivity.this);
        binding.filters.setAdapter(adapter);

        this.binding.camera.setLifecycleOwner(this);
        this.binding.camera.setMode(Mode.VIDEO);
        this.binding.close.setOnClickListener(view -> confirmClose());
        binding.flip.setOnClickListener(v -> toggleFacing());
        binding.flash.setOnClickListener(v -> setFlash());
        binding.done.setOnClickListener(v -> commitVideoRecordings());
        //binding.viewsticker.setOnClickListener(v -> openStickerView());
        /*this.binding.viewsticker.setOnClickListener(new RecorderActivity$$ExternalSyntheticLambda25(this));*/
        SegmentedGroup speeds = (SegmentedGroup) findViewById(R.id.speeds);
        View speed = findViewById(R.id.speed);
        //speed.setOnClickListener(new RecorderActivity$$ExternalSyntheticLambda5(this, speeds));
        speed.setOnClickListener(v -> {
            openSpeedView(speeds, v);
        });
        speed.setVisibility(Build.VERSION.SDK_INT >= 23 ? View.VISIBLE : View.GONE);
        RadioButton speed05x = (RadioButton) findViewById(R.id.speed05x);
        RadioButton speed075x = (RadioButton) findViewById(R.id.speed075x);
        RadioButton speed1x = (RadioButton) findViewById(R.id.speed1x);
        RadioButton speed15x = (RadioButton) findViewById(R.id.speed15x);
        RadioButton speed2x = (RadioButton) findViewById(R.id.speed2x);
        speed05x.setChecked(this.mModel.speed == 0.5f);
        speed075x.setChecked(this.mModel.speed == 0.75f);
        speed1x.setChecked(this.mModel.speed == 1.0f);
        speed15x.setChecked(this.mModel.speed == 1.5f);
        speed2x.setChecked(this.mModel.speed == 2.0f);
        //RecorderActivity$$ExternalSyntheticLambda9 recorderActivity$$ExternalSyntheticLambda9 = r0;
        RadioButton radioButton = speed15x;
        RadioButton radioButton2 = speed1x;

        this.binding.filter.setOnClickListener(v -> openFilterOptions());
        TurboImageView stickers = (TurboImageView) findViewById(R.id.stickerTurbo);
        //this.binding.camera.setOnTouchListener(new RecorderActivity$$ExternalSyntheticLambda8(stickers));
        View remove = findViewById(R.id.remove);
        remove.setOnClickListener(v -> removeStickers(stickers, remove));
        findViewById(R.id.sticker).setOnClickListener(v -> openStickerView());
        View sticker = findViewById(R.id.sticker);
        sticker.setVisibility(getResources().getBoolean(R.bool.stickers_enabled) ? View.VISIBLE : View.GONE);
        View sheet = findViewById(R.id.timer_sheet);
        BottomSheetBehavior<View> bsb = BottomSheetBehavior.from(sheet);
        ((ImageView) sheet.findViewById(R.id.btnClose)).setOnClickListener(v -> bsb.setState(BottomSheetBehavior.STATE_COLLAPSED));
        //((ImageView) sheet.findViewById(R.id.btnDone)).setOnClickListener(new RecorderActivity$$ExternalSyntheticLambda2(this, bsb));
        ((ImageView) sheet.findViewById(R.id.btnDone)).setOnClickListener(v -> startTimerFromBsb(bsb));
        this.binding.timer.setOnClickListener(v -> openSetTimerBsb(bsb));
        final TextView maximum = (TextView) findViewById(R.id.maximum);
        TurboImageView turboImageView = stickers;
        findViewById(R.id.sound).setOnClickListener(v -> openSoundPicker());
        final Slider selection = (Slider) findViewById(R.id.selection);
        View view = remove;
        selection.setLabelFormatter(String::valueOf);
        View upload = findViewById(R.id.upload);
        View view2 = sticker;
        upload.setOnClickListener(v -> chooseVideoForUpload());
        bsb.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            public void onSlide(View v, float offset) {
            }

            public void onStateChanged(View v, int state) {
                if (state == 3) {
                    long max = TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(Const.MAX_DURATION - RecorderActivity.this.mModel.recorded()));
                    selection.setValue(0.0f);
                    selection.setValueTo((float) max);
                    selection.setValue((float) max);
                    maximum.setText(TextFormatUtil.toMMSS(max));
                }
            }
        });
        final SegmentedProgressBar segments = (SegmentedProgressBar) findViewById(R.id.segments);
        Slider slider = selection;
        View view3 = upload;
        segments.enableAutoProgressView(Const.MAX_DURATION);
        segments.setDividerColor(ViewCompat.MEASURED_STATE_MASK);
        segments.setDividerEnabled(true);
        segments.setDividerWidth(2.0f);
        segments.setListener(new ProgressBarListener() {
            @Override
            public void TimeinMill(long l) {
                binding.filters.setVisibility(View.GONE);
                if (binding.camera.isTakingVideo()) {
                    stopRecording();
                    return;
                }
                binding.filters.setVisibility(View.GONE);
                speeds.setVisibility(View.GONE);
                startRecording();
            }
        });
        segments.setShader(new int[]{ContextCompat.getColor(this, R.color.pink_main), ContextCompat.getColor(this, R.color.pink)});

        binding.camera.addCameraListener(new CameraListener() {
            @Override
            public void onCameraOpened(@NonNull CameraOptions options) {
                super.onCameraOpened(options);
            }

            @Override
            public void onCameraClosed() {
                super.onCameraClosed();
            }

            @Override
            public void onCameraError(@NonNull CameraException exception) {
                super.onCameraError(exception);
            }

            @Override
            public void onPictureTaken(@NonNull PictureResult result) {
                super.onPictureTaken(result);
                result.toBitmap(bitmap -> {
                    if (bitmap != null) {
                        FilterRecordAdapter adapter = new FilterRecordAdapter(RecorderActivity.this);
                        adapter.setListener(new FilterAdapter.OnFilterSelectListener(){
                            @Override
                            public void onSelectFilter(VideoFilter filter) {

                            }
                        });
                        RecorderActivity.this.binding.filters.setAdapter(adapter);
                        RecorderActivity.this.binding.filters.setVisibility(View.VISIBLE);
                    }
                    RecorderActivity.this.mProgress.dismiss();
                });
            }

            @Override
            public void onVideoTaken(@NonNull VideoResult result) {
                super.onVideoTaken(result);
            }

            @Override
            public void onOrientationChanged(int orientation) {
                super.onOrientationChanged(orientation);
            }

            @Override
            public void onAutoFocusStart(@NonNull PointF point) {
                super.onAutoFocusStart(point);
            }

            @Override
            public void onAutoFocusEnd(boolean successful, @NonNull PointF point) {
                super.onAutoFocusEnd(successful, point);
            }

            @Override
            public void onZoomChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {
                super.onZoomChanged(newValue, bounds, fingers);
            }

            @Override
            public void onExposureCorrectionChanged(float newValue, @NonNull float[] bounds, @Nullable PointF[] fingers) {
                super.onExposureCorrectionChanged(newValue, bounds, fingers);
            }

            @Override
            public void onVideoRecordingStart() {
                super.onVideoRecordingStart();
                Log.v(RecorderActivity.TAG, "Video recording has started.");
                segments.resume();
                if (RecorderActivity.this.mMediaPlayer != null) {
                    if (Build.VERSION.SDK_INT >= 23) {
                        float speed = 1.0f;
                        if (RecorderActivity.this.mModel.speed == 0.5f) {
                            speed = 2.0f;
                        } else if (RecorderActivity.this.mModel.speed == 0.75f) {
                            speed = 1.5f;
                        } else if (RecorderActivity.this.mModel.speed == 1.5f) {
                            speed = 0.75f;
                        } else if (RecorderActivity.this.mModel.speed == 2.0f) {
                            speed = 0.5f;
                        }
                        PlaybackParams params = new PlaybackParams();
                        params.setSpeed(speed);
                        RecorderActivity.this.mMediaPlayer.setPlaybackParams(params);
                    }
                    RecorderActivity.this.mMediaPlayer.start();
                }
                YoYo.YoYoString unused = RecorderActivity.this.mPulse = YoYo.with(Techniques.Pulse).repeat(-1).playOn(RecorderActivity.this.binding.record);
                RecorderActivity.this.binding.record.setSelected(true);
                toggleVisibility(false);
            }

            @Override
            public void onVideoRecordingEnd() {
                super.onVideoRecordingEnd();
                Log.v(RecorderActivity.TAG, "Video recording has ended.");
                segments.pause();
                segments.addDivider();
                mHandler.removeCallbacks(RecorderActivity.this.mStopper);
                RecorderActivity.this.mHandler.postDelayed(RecorderActivity.this::processCurrentRecording, 500);
                if (RecorderActivity.this.mMediaPlayer != null) {
                    RecorderActivity.this.mMediaPlayer.pause();
                }
                RecorderActivity.this.mPulse.stop();
                RecorderActivity.this.binding.record.setSelected(false);
                RecorderActivity.this.toggleVisibility(true);
            }
        });

        this.binding.record.setOnClickListener(v -> toggleVideoRecord(speeds, v));

    }

    private void openSoundPicker() {
        if (this.mModel.segments.isEmpty()) {
            startActivityForResult(new Intent(this, SongPickerActivity.class), 60605);
        } else if (this.mModel.audio == null) {
            Toast.makeText(this, R.string.message_song_select, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.message_song_change, Toast.LENGTH_SHORT).show();
        }
    }

    @AfterPermissionGranted(200)
    private void chooseVideoForUpload() {
        IntentUtil.startChooser((Activity) this, 100, MimeTypes.VIDEO_MP4);
    }

    private void startTimerFromBsb(BottomSheetBehavior<View> bsb) {
        bsb.setState(BottomSheetBehavior.STATE_COLLAPSED);
        startTimer();
    }

    private void openSetTimerBsb(BottomSheetBehavior<View> bsb) {
        if (this.binding.camera.isTakingVideo()) {
            Toast.makeText(this, R.string.recorder_error_in_progress, Toast.LENGTH_SHORT).show();
        } else {
            bsb.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void openFilterOptions() {
        if (this.binding.camera.isTakingVideo()) {
            Toast.makeText(this, R.string.recorder_error_in_progress, Toast.LENGTH_SHORT).show();
        } else if (this.binding.filters.getVisibility() == View.VISIBLE) {
            this.binding.filters.setAdapter((RecyclerView.Adapter) null);
            this.binding.filters.setVisibility(View.GONE);
        } else {
            if (this.binding.filters.getVisibility() == View.GONE) {
                this.binding.filters.setVisibility(View.VISIBLE);
            }
            this.mProgress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(getString(R.string.progress_title)).setCancellable(false).show();
            this.binding.camera.takePictureSnapshot();
        }
    }

    private void removeStickers(TurboImageView stickers, View remove){
        stickers.removeSelectedObject();
        if (stickers.getObjectCount() <= 0) {
            remove.setVisibility(View.GONE);
        }
    }

    private void commitVideoRecordings(){
        if (this.binding.camera.isTakingVideo()) {
            Toast.makeText(this, R.string.recorder_error_in_progress, Toast.LENGTH_SHORT).show();
        } else if (this.mModel.segments.isEmpty()) {
            Toast.makeText(this, R.string.recorder_error_no_clips, Toast.LENGTH_SHORT).show();
        } else {
            commitRecordings(this.mModel.segments, this.mModel.audio);
        }
    }

    private void commitRecordings(List<RecordSegment> segments, Uri audio) {
        this.timeInSeconds = 0;
        this.mProgress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(getString(R.string.progress_title)).setCancellable(false).show();
        List<String> videos = new ArrayList<>();
        for (RecordSegment segment : segments) {
            videos.add(segment.file);
        }
        this.merged = TempUtil.createNewFile((Context) this, ".mp4");
        Log.d(TAG, "commitRecordings: first merged" + this.merged.getAbsolutePath());
        OneTimeWorkRequest request = (OneTimeWorkRequest) ((OneTimeWorkRequest.Builder) new OneTimeWorkRequest.Builder(MergeVideosWorker2.class).setInputData(new Data.Builder().putStringArray(MergeVideosWorker2.KEY_INPUTS, (String[]) videos.toArray(new String[0])).putString("output", this.merged.getAbsolutePath()).build())).build();
        WorkManager wm = WorkManager.getInstance(this);
        wm.enqueue((WorkRequest) request);
        //wm.getWorkInfoByIdLiveData(request.getId()).observe(this, new RecorderActivity$$ExternalSyntheticLambda10(this, audio));
        wm.getWorkInfoByIdLiveData(request.getId()).observe(this, workInfo -> mergeVideo(workInfo, audio));
        Log.d(TAG, "commitRecordings: ");
    }

    private void mergeVideo( WorkInfo info2, Uri audio){
        if (info2.getState() == WorkInfo.State.CANCELLED || info2.getState() == WorkInfo.State.FAILED || info2.getState() == WorkInfo.State.SUCCEEDED) {
            Log.d(TAG, "commitRecordings: ended");
            this.mProgress.dismiss();
        }
        if (info2.getState() == WorkInfo.State.SUCCEEDED) {
            Log.d(TAG, "commitRecordings: success");
            if (audio != null) {
                proceedToVolume(this.merged, new File(audio.getPath()));
            } else {
                proceedToFilter(this.merged);
            }
        }
    }

    private void proceedToFilter(File video) {
        Log.d(TAG, "Proceeding to filter screen with " + video);
        Intent intent = new Intent(this, FilterActivity.class);
        intent.putExtra("song", this.mModel.song);
        intent.putExtra("video", video.getAbsolutePath());
        Log.d(TAG, "proceedToFilter: " + video.getAbsolutePath());
        startActivity(intent);
        finish();
    }

    private void proceedToVolume(File video, File audio) {
        Log.v(TAG, "Proceeding to volume screen with " + video + "; " + audio);
        Intent intent = new Intent(this, VolumeActivity.class);
        intent.putExtra("song", this.mModel.song);
        intent.putExtra("video", video.getAbsolutePath());
        intent.putExtra("audio", audio.getAbsolutePath());
        startActivity(intent);
        finish();
    }

    public void processCurrentRecording() {
        if (this.mModel.video != null) {
            long duration = VideoUtil.getDuration(this, Uri.fromFile(this.mModel.video));
            if (this.mModel.speed != 1.0f) {
                applyVideoSpeed(this.mModel.video, this.mModel.speed, duration);
            } else {
                RecordSegment segment = new RecordSegment();
                segment.file = this.mModel.video.getAbsolutePath();
                segment.duration = duration;
                this.mModel.segments.add(segment);
            }
        }
        this.mModel.video = null;
    }

    private void applyVideoSpeed(File file, float speed, long duration) {
        File output = TempUtil.createNewFile((Context) this, ".mp4");
        this.mProgress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(getString(R.string.progress_title)).setCancellable(false).setCancellable(false).show();
        OneTimeWorkRequest request = (OneTimeWorkRequest) ((OneTimeWorkRequest.Builder) new OneTimeWorkRequest.Builder(VideoSpeedWorker2.class).setInputData(new Data.Builder().putString("input", file.getAbsolutePath()).putString("output", output.getAbsolutePath()).putFloat(VideoSpeedWorker2.KEY_SPEED, speed).build())).build();
        WorkManager wm = WorkManager.getInstance(this);
        wm.enqueue((WorkRequest) request);
        wm.getWorkInfoByIdLiveData(request.getId()).observe(this, workInfo -> videoSpeedWork(workInfo, output, duration));
    }

    public void videoSpeedWork( WorkInfo info2, File output, long duration) {
        if (info2.getState() == WorkInfo.State.CANCELLED || info2.getState() == WorkInfo.State.FAILED || info2.getState() == WorkInfo.State.SUCCEEDED) {
            this.mProgress.dismiss();
        }
        if (info2.getState() == WorkInfo.State.SUCCEEDED) {
            RecordSegment segment = new RecordSegment();
            segment.file = output.getAbsolutePath();
            segment.duration = duration;
            this.mModel.segments.add(segment);
        }
    }

    private void toggleFacing() {
        if (this.binding.camera.isTakingVideo()) {
            Toast.makeText(this, R.string.recorder_error_in_progress, Toast.LENGTH_SHORT).show();
        } else {
            this.binding.camera.toggleFacing();
        }
    }

    private void setFlash(){
        if (this.binding.camera.isTakingVideo()) {
            Toast.makeText(this, R.string.recorder_error_in_progress, Toast.LENGTH_SHORT).show();
        } else {
            this.binding.camera.setFlash(this.binding.camera.getFlash() == Flash.OFF ? Flash.TORCH : Flash.OFF);
        }
    }

    private void openStickerView(){
        startActivityForResult(new Intent(this, StickerPickerActivity.class), Const.REQUEST_CODE_PICK_STICKER);
        /*StickerView stickerView = this.mCurrentView;
        if (stickerView != null) {
            stickerView.setInEdit(false);
        }*/
    }

    private void openSpeedView(SegmentedGroup speeds, View view){
        int i = View.VISIBLE;
        if (this.binding.camera.isTakingVideo()) {
            Toast.makeText(this, R.string.recorder_error_in_progress, Toast.LENGTH_SHORT).show();
            return;
        }
        if (speeds.getVisibility() == View.VISIBLE) {
            i = View.GONE;
        }
        speeds.setVisibility(i);
    }

    private void toggleVideoRecord(SegmentedGroup speeds, View view){
        this.binding.filters.setVisibility(View.GONE);
        if (binding.camera.isTakingVideo()) {
            stopRecording();
            return;
        }
        this.binding.filters.setVisibility(View.GONE);
        speeds.setVisibility(View.GONE);
        startRecording();
    }

    /* access modifiers changed from: private */
    public void startRecording() {
        Log.d(TAG, "startRecording: ");
        this.binding.filters.setVisibility(View.GONE);
        long recorded = this.mModel.recorded();
        if (recorded >= Const.MAX_DURATION) {
            Toast.makeText(this, R.string.recorder_error_maxed_out, Toast.LENGTH_SHORT).show();
            return;
        }
        this.mModel.video = TempUtil.createNewFile((Context) this, ".mp4");
        this.binding.camera.takeVideoSnapshot(this.mModel.video, (int) (Const.MAX_DURATION - recorded));
    }

    private void startTimer() {
        View countdown = findViewById(R.id.countdown);
        TextView count = (TextView) findViewById(R.id.count);
        count.setText((CharSequence) null);
        final TextView textView = count;
        final View view = countdown;
        final long value = (long) ((Slider) findViewById(R.id.selection)).getValue();
        CountDownTimer timer = new CountDownTimer(3000, 1000) {
            public void onTick(long remaining) {
                //RecorderActivity.this.mHandler.post(new RecorderActivity$3$$ExternalSyntheticLambda1(textView, remaining));
            }

            public void onFinish() {
                //RecorderActivity.this.mHandler.post(new RecorderActivity$3$$ExternalSyntheticLambda0(view));
                RecorderActivity.this.startRecording();
                RecorderActivity.this.mHandler.postDelayed(RecorderActivity.this.mStopper, value);
            }
        };
        countdown.setOnClickListener(v -> stopTimer(timer, countdown));
        countdown.setVisibility(View.VISIBLE);
        timer.start();
    }

    private void stopTimer(CountDownTimer timer, View countdown) {
        timer.cancel();
        countdown.setVisibility(View.GONE);
    }

    public void stopRecording() {
        Log.d(TAG, "stopRecording: ");
        this.binding.camera.stopVideo();
    }

    private void confirmClose() {
        new PopupBuilder(this).showReliteDiscardPopup("Discard Entire video ?", "If you go back now, you will lose all the clips added to your video", "Discard Video", "Cancel", new PopupBuilder.OnPopupClickListner() {
            @Override
            public void onClickCountinue() {
                onBackPressed();
            }
        });
    }

    private void setupSong(Song songDummy, Uri file) {
        Log.d(TAG, "setupSong:  file" + file.toString());
        MediaPlayer create = MediaPlayer.create(this, file);
        this.mMediaPlayer = create;
        create.setOnCompletionListener(mediaPlayer -> {
            if (this.mMediaPlayer != null) {
                this.mMediaPlayer = null;
            }
        });
        TextView sound = (TextView) findViewById(R.id.sound);
        if (songDummy != null) {
            sound.setText(songDummy.getTitle());
            this.mModel.song = songDummy.getId();
        } else {
            sound.setText(getString(R.string.audio_from_clip));
        }
        this.mModel.audio = file;
    }

    private void submitUpload(Uri uri) {
        File copy = TempUtil.createCopy(this, uri, ".mp4");
        Intent intent = new Intent(this, TrimmerActivity.class);
        if (this.mModel.audio != null) {
            intent.putExtra("audio", this.mModel.audio.getPath());
        }
        intent.putExtra("song", this.mModel.song);
        intent.putExtra("video", copy.getAbsolutePath());
        startActivity(intent);
        finish();
    }

    /* access modifiers changed from: private */
    public void toggleVisibility(boolean show) {
        if (getResources().getBoolean(R.bool.clutter_free_recording_enabled)) {
            AnimationUtil.toggleVisibilityToTop(findViewById(R.id.top), show);
            AnimationUtil.toggleVisibilityToLeft(findViewById(R.id.right), show);
            AnimationUtil.toggleVisibilityToBottom(findViewById(R.id.upload), show);
            AnimationUtil.toggleVisibilityToBottom(findViewById(R.id.done), show);
        }
    }

    private void addStickerView(File file) {
        final StickerView stickerView = new StickerView(this);
        stickerView.setBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
        stickerView.setOperationListener(new StickerView.OperationListener() {
            public void onDeleteClick() {
                RecorderActivity.this.mViews.remove(stickerView);
                RecorderActivity.this.binding.viewsticker.removeView(stickerView);
            }

            public void onEdit(StickerView stickerView) {
                RecorderActivity.this.mCurrentView.setInEdit(false);
                RecorderActivity.this.mCurrentView = stickerView;
                RecorderActivity.this.mCurrentView.setInEdit(true);
            }

            public void onTop(StickerView stickerView) {
                int position = RecorderActivity.this.mViews.indexOf(stickerView);
                if (position != RecorderActivity.this.mViews.size() - 1) {
                    RecorderActivity.this.mViews.add(RecorderActivity.this.mViews.size(), (StickerView) RecorderActivity.this.mViews.remove(position));
                }
            }
        });
        this.binding.viewsticker.addView(stickerView, new RelativeLayout.LayoutParams(-1, -1));
        this.mViews.add(stickerView);
        setCurrentEdit(stickerView);
    }

    private void setCurrentEdit(StickerView stickerView) {
        StickerView stickerView2 = this.mCurrentView;
        if (stickerView2 != null) {
            stickerView2.setInEdit(false);
        }
        this.mCurrentView = stickerView;
        stickerView.setInEdit(true);
    }

    public static File getDirPathWithFolder(Context context) {
        File mediaStorageDir = new File(context.getFilesDir().getAbsolutePath(), Const.TEMP_FOLDER_NAME);
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }
        return mediaStorageDir;
    }

    public static class RecorderActivityViewModel extends ViewModel {
        public Uri audio;
        public List<RecordSegment> segments = new ArrayList();
        public String song = "";
        public float speed = 1.0f;
        public File video;

        public long recorded() {
            long recorded = 0;
            for (RecordSegment segment : this.segments) {
                recorded += segment.duration;
            }
            return recorded;
        }
    }

    private static class RecordSegment {
        public long duration;
        public String file;

        private RecordSegment() {
        }
    }
}
