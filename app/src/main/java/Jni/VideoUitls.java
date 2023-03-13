package Jni;

import android.media.MediaExtractor;
import android.media.MediaFormat;

public class VideoUitls {
    private VideoUitls() {
    }

    public static long getDuration(String str) {
        try {
            MediaExtractor mediaExtractor = new MediaExtractor();
            mediaExtractor.setDataSource(str);
            int selectVideoTrack = TrackUtils.selectVideoTrack(mediaExtractor);
            if (selectVideoTrack == -1 && (selectVideoTrack = TrackUtils.selectAudioTrack(mediaExtractor)) == -1) {
                return 0;
            }
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(selectVideoTrack);
            long j = trackFormat.containsKey("durationUs") ? trackFormat.getLong("durationUs") : 0;
            mediaExtractor.release();
            return j;
        } catch (Exception unused) {
            return 0;
        }
    }
}
