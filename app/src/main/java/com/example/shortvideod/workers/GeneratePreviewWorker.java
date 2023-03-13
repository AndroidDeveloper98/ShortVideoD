package com.example.shortvideod.workers;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.ForegroundInfo;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.beak.gifmakerlib.GifMaker;
import com.example.shortvideod.R;

import java.io.File;


public class GeneratePreviewWorker extends Worker {

    public static final String KEY_INPUT = "input";
    public static final String KEY_PREVIEW = "preview";
    public static final String KEY_SCREENSHOT = "screenshot";
    private static final String TAG = "GeneratePreviewWorker";

    public GeneratePreviewWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    private ForegroundInfo createForegroundInfo(Context context) {
        String cancel = context.getString(R.string.cancel_button);
        PendingIntent intent = WorkManager.getInstance(context)
                .createCancelPendingIntent(getId());
        Notification notification =
                new NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
                        .setContentTitle(context.getString(R.string.notification_preview_title))
                        .setTicker(context.getString(R.string.notification_preview_title))
                        .setContentText(context.getString(R.string.notification_preview_description))
                        .setSmallIcon(R.drawable.ic_baseline_movie_filter_24)
                        .setOngoing(true)
                        .setOnlyAlertOnce(true)
                        .addAction(R.drawable.ic_baseline_close_24, cancel, intent)
                        .build();
        return new ForegroundInfo(1, notification);
    }

    @NonNull
    @Override
    @SuppressWarnings("ConstantConditions")
    public Result doWork() {
        setForegroundAsync(createForegroundInfo(getApplicationContext()));
        File input = new File(getInputData().getString(KEY_INPUT));
        File screenshot = new File(getInputData().getString(KEY_SCREENSHOT));
        File preview = new File(getInputData().getString(KEY_PREVIEW));
        boolean success = doActualWork(input, preview);
        if (!success && !input.delete()) {
            Log.w(TAG, "Could not delete input file: " + input);
        }

        if (!success && !screenshot.delete()) {
            Log.w(TAG, "Could not delete failed screenshot file: " + input);
        }

        if (!success && !preview.delete()) {
            Log.w(TAG, "Could not delete failed preview file: " + input);
        }

        return success ? Result.success() : Result.failure();
    }

    private boolean doActualWork(File input, File preview) {
        GifMaker gif = new GifMaker(2);
        return gif.makeGifFromVideo(
                input.getAbsolutePath(),
                1000,
                3000,
                250,
                preview.getAbsolutePath());
    }
}
