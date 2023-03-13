package Jni;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;

public class TrackUtils {
    private static final String TAG = "TrackUtils";

    public static int selectVideoTrack(MediaExtractor mediaExtractor) {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
            String string = trackFormat.getString("mime");
            if (string.startsWith("video/")) {
                Log.d(TAG, "Extractor selected track " + i + " (" + string + "): " + trackFormat);
                return i;
            }
        }
        return -1;
    }

    public static int selectAudioTrack(MediaExtractor mediaExtractor) {
        int trackCount = mediaExtractor.getTrackCount();
        for (int i = 0; i < trackCount; i++) {
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
            String string = trackFormat.getString("mime");
            if (string.startsWith("audio/")) {
                Log.d(TAG, "Extractor selected track " + i + " (" + string + "): " + trackFormat);
                return i;
            }
        }
        return -1;
    }
}
