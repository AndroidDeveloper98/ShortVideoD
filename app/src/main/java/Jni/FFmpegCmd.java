package Jni;


import com.example.shortvideod.workers.OnEditorListener;

public class FFmpegCmd {
    private static long duration;
    private static OnEditorListener listener;

    public static native int exec(int i, String[] strArr);

    public static native void exit();

    static {
        System.loadLibrary("avutil");
        System.loadLibrary("avcodec");
        System.loadLibrary("swresample");
        System.loadLibrary("avformat");
        System.loadLibrary("swscale");
        System.loadLibrary("avfilter");
        System.loadLibrary("avdevice");
        System.loadLibrary("ffmpeg");
        System.loadLibrary("postproc");
    }

    public static void onExecuted(int i) {
        OnEditorListener onEditorListener = listener;
        if (onEditorListener == null) {
            return;
        }
        if (i == 0) {
            onEditorListener.onProgress(1.0f);
            listener.onSuccess();
            return;
        }
        onEditorListener.onFailure();
    }

    public static void onProgress(float f) {
        OnEditorListener onEditorListener = listener;
        if (onEditorListener != null) {
            long j = duration;
            if (j != 0) {
                onEditorListener.onProgress((f / ((float) (j / 1000000))) * 0.95f);
            }
        }
    }

    public static void exec(String[] strArr, long j, OnEditorListener onEditorListener) {
        listener = onEditorListener;
        duration = j;
        exec(strArr.length, strArr);
    }
}
