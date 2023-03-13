package com.example.shortvideod.workers;

import android.content.Context;
import android.util.Log;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.example.shortvideod.util.VideoUtil;
import com.google.common.util.concurrent.ListenableFuture;
import com.otaliastudios.transcoder.Transcoder;
import com.otaliastudios.transcoder.TranscoderListener;
import com.otaliastudios.transcoder.TranscoderOptions;
import com.otaliastudios.transcoder.strategy.DefaultVideoStrategy;
import com.otaliastudios.transcoder.strategy.size.AspectRatioResizer;
import com.otaliastudios.transcoder.strategy.size.AtMostResizer;
import com.otaliastudios.transcoder.strategy.size.FractionResizer;
import java.io.File;

public class MergeVideosWorker2 extends ListenableWorker {
    public static final String KEY_INPUTS = "inputs";
    public static final String KEY_OUTPUT = "output";
    private static final String TAG = "MergeVideosWorker2";

    public MergeVideosWorker2(Context context, WorkerParameters params) {
        super(context, params);
    }

    public ListenableFuture<ListenableWorker.Result> startWork() {
        String[] paths = getInputData().getStringArray(KEY_INPUTS);
        File output = new File(getInputData().getString("output"));
        File[] files = new File[paths.length];
        for (int i = 0; i < paths.length; i++) {
            files[i] = new File(paths[i]);
        }
        //return CallbackToFutureAdapter.getFuture(new MergeVideosWorker2$$ExternalSyntheticLambda0(this, files, output));
        return CallbackToFutureAdapter.getFuture(completer -> {
            doActualWork(files, output, completer);
            return null;
        });
    }

    /* renamed from: lambda$startWork$0$com-example-shortvideod-workers-MergeVideosWorker2 */
    public /* synthetic */ Object mo643x5ddb1b55(File[] files, File output, CallbackToFutureAdapter.Completer completer) throws Exception {
        doActualWork(files, output, completer);
        return null;
    }

    private void doActualWork(final File[] inputs, final File output, final CallbackToFutureAdapter.Completer<ListenableWorker.Result> completer) {
        TranscoderOptions.Builder transcoder = Transcoder.into(output.getAbsolutePath());
        for (File input : inputs) {
            transcoder.addDataSource(VideoUtil.createDataSource(getApplicationContext(), input.getAbsolutePath()));
        }
        transcoder.setListener(new TranscoderListener() {
            public void onTranscodeProgress(double progress) {
            }

            public void onTranscodeCompleted(int code) {
                Log.d(MergeVideosWorker2.TAG, "Merging video files has finished.");
                completer.set(ListenableWorker.Result.success());
                for (File input : inputs) {
                    if (!input.delete()) {
                        Log.w(MergeVideosWorker2.TAG, "Could not delete input file: " + input);
                    }
                }
            }

            public void onTranscodeCanceled() {
                Log.d(MergeVideosWorker2.TAG, "Merging video files was cancelled.");
                completer.setCancelled();
                if (!output.delete()) {
                    Log.w(MergeVideosWorker2.TAG, "Could not delete failed output file: " + output);
                }
            }

            public void onTranscodeFailed(Throwable e) {
                Log.d(MergeVideosWorker2.TAG, "Merging video files failed with error.", e);
                completer.setException(e);
                if (!output.delete()) {
                    Log.w(MergeVideosWorker2.TAG, "Could not delete failed output file: " + output);
                }
            }
        });
        DefaultVideoStrategy build = new DefaultVideoStrategy.Builder().addResizer(new AspectRatioResizer(1.7777778f)).addResizer(new FractionResizer(0.5f)).addResizer(new AtMostResizer(1000)).build();
        DefaultVideoStrategy build2 = DefaultVideoStrategy.exact(720, 1280).bitRate(4000000).frameRate(40).keyFrameInterval(3.0f).build();
        DefaultVideoStrategy build3 = new DefaultVideoStrategy.Builder().addResizer(new FractionResizer(0.7f)).addResizer(new AtMostResizer(1000)).build();
        transcoder.setVideoTrackStrategy(new DefaultVideoStrategy.Builder(new AtMostResizer(Integer.MAX_VALUE)).build());
        transcoder.transcode();
    }
}
