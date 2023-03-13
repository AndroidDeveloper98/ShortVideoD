package com.example.shortvideod.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.common.util.UriUtil;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.otaliastudios.transcoder.source.DataSource;
import com.otaliastudios.transcoder.source.FilePathDataSource;
import com.otaliastudios.transcoder.source.UriDataSource;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public final class VideoUtil {
    private static final String TAG = "VideoUtil";

    public static DataSource createDataSource(Context context, String file) {
        if (file.startsWith("content")) {
            return new UriDataSource(context, Uri.parse(file));
        }
        if (file.startsWith(UriUtil.LOCAL_FILE_SCHEME)) {
            file = Uri.parse(file).getPath();
        }
        return new FilePathDataSource(file);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0045, code lost:
        if (r2 == null) goto L_0x0048;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.util.Size getDimensions(java.lang.String r7) {
        /*
            r0 = 0
            r1 = 0
            r2 = 0
            android.media.MediaMetadataRetriever r3 = new android.media.MediaMetadataRetriever     // Catch:{ Exception -> 0x002d }
            r3.<init>()     // Catch:{ Exception -> 0x002d }
            r2 = r3
            r2.setDataSource(r7)     // Catch:{ Exception -> 0x002d }
            r3 = 18
            java.lang.String r3 = r2.extractMetadata(r3)     // Catch:{ Exception -> 0x002d }
            r4 = 19
            java.lang.String r4 = r2.extractMetadata(r4)     // Catch:{ Exception -> 0x002d }
            if (r3 == 0) goto L_0x0026
            if (r4 == 0) goto L_0x0026
            int r5 = java.lang.Integer.parseInt(r3)     // Catch:{ Exception -> 0x002d }
            r0 = r5
            int r5 = java.lang.Integer.parseInt(r4)     // Catch:{ Exception -> 0x002d }
            r1 = r5
        L_0x0026:
        L_0x0027:
            r2.release()
            goto L_0x0048
        L_0x002b:
            r3 = move-exception
            goto L_0x004e
        L_0x002d:
            r3 = move-exception
            java.lang.String r4 = "VideoUtil"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x002b }
            r5.<init>()     // Catch:{ all -> 0x002b }
            java.lang.String r6 = "Unable to extract thumbnail from "
            r5.append(r6)     // Catch:{ all -> 0x002b }
            r5.append(r7)     // Catch:{ all -> 0x002b }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x002b }
            android.util.Log.e(r4, r5, r3)     // Catch:{ all -> 0x002b }
            if (r2 == 0) goto L_0x0048
            goto L_0x0027
        L_0x0048:
            android.util.Size r3 = new android.util.Size
            r3.<init>(r0, r1)
            return r3
        L_0x004e:
            if (r2 == 0) goto L_0x0053
            r2.release()
        L_0x0053:
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.shortvideod.util.VideoUtil.getDimensions(java.lang.String):android.util.Size");
    }

    public static long getDuration(Context context, Uri uri) {
        MediaMetadataRetriever mmr = null;
        try {
            mmr = new MediaMetadataRetriever();
            if (TextUtils.equals(uri.getScheme(), UriUtil.LOCAL_FILE_SCHEME)) {
                mmr.setDataSource(uri.getPath());
            } else {
                mmr.setDataSource(context, uri);
            }
            String duration = mmr.extractMetadata(9);
            if (duration != null) {
                long parseLong = Long.parseLong(duration);
                mmr.release();
                return parseLong;
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to extract duration from " + uri, e);
            if (mmr == null) {
                return 0;
            }
        } catch (Throwable th) {
            if (mmr != null) {
                mmr.release();
            }
            throw th;
        }
        mmr.release();
        return 0;
    }

    public static Bitmap getFrameAtTime(String path, long micros) {
        MediaMetadataRetriever mmr = null;
        try {
            mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            String duration = mmr.extractMetadata(9);
            if (duration != null) {
                long millis = Long.parseLong(duration);
                if (micros > TimeUnit.MILLISECONDS.toMicros(millis)) {
                    Bitmap frameAtTime = mmr.getFrameAtTime(TimeUnit.MILLISECONDS.toMicros(millis));
                    mmr.release();
                    return frameAtTime;
                }
                Bitmap frameAtTime2 = mmr.getFrameAtTime(micros);
                mmr.release();
                return frameAtTime2;
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to extract thumbnail from " + path, e);
            if (mmr == null) {
                return null;
            }
        } catch (Throwable th) {
            if (mmr != null) {
                mmr.release();
            }
            throw th;
        }
        mmr.release();
        return null;
    }

    public static boolean hasTrack(String path, String type) throws IOException {
        for (Track track : MovieCreator.build(path).getTracks()) {
            if (TextUtils.equals(track.getHandler(), type)) {
                return true;
            }
        }
        return false;
    }

    public static double getFileSizeInMB(File file) {
        DecimalFormat df = new DecimalFormat("###.##");
        df.setMaximumFractionDigits(2);
        double fileSizeInBytes = (double) file.length();
        Log.d(TAG, "getFileSizeInbyte   " + fileSizeInBytes);
        double fileSizeInKB = fileSizeInBytes / 1024.0d;
        Log.d(TAG, "getFileSizeInKb   " + fileSizeInKB);
        double fileSizeInMB = fileSizeInKB / 1024.0d;
        Log.d(TAG, "getFileSizeInMb   " + fileSizeInMB);
        return Double.parseDouble(df.format(fileSizeInMB));
    }
}