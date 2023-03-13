package com.example.shortvideod.util;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
/*import org.apache.commons.p010io.FileUtils;
import org.apache.commons.p010io.IOCase;
import org.apache.commons.p010io.IOUtils;
import org.apache.commons.p010io.filefilter.FileFilterUtils;
import org.apache.commons.p010io.filefilter.IOFileFilter;*/

public final class TempUtil {
    private static final long CLEANUP_CUTOFF = (System.currentTimeMillis() - TimeUnit.DAYS.toMillis(3));
    private static final String LOG_FILE_NAME = "agora-rtc.log";
    private static final String LOG_FOLDER_NAME = "log";
    private static final String TAG = "TempUtil";

    public static void cleanupStaleFiles(Context context) {
        Collection<File> stale = FileUtils.listFiles(context.getCacheDir(), FileFilterUtils.and(FileFilterUtils.fileFileFilter(), FileFilterUtils.ageFileFilter(CLEANUP_CUTOFF), FileFilterUtils.or(FileFilterUtils.suffixFileFilter(".gif", IOCase.INSENSITIVE), FileFilterUtils.suffixFileFilter(".png", IOCase.INSENSITIVE), FileFilterUtils.suffixFileFilter(".mp4", IOCase.INSENSITIVE))), (IOFileFilter) null);
        if (!stale.isEmpty()) {
            for (File file : stale) {
                FileUtils.deleteQuietly(file);
            }
        }
    }

    public static File createCopy(Context context, Uri uri, String suffix) {
        OutputStream os;
        File temp = createNewFile(context, suffix);
        try {
            InputStream is = context.getContentResolver().openInputStream(uri);
            try {
                os = new FileOutputStream(temp);
                IOUtils.copy(is, os);
                os.close();
                if (is != null) {
                    is.close();
                }
            } catch (Throwable th) {
                if (is != null) {
                    is.close();
                }
                throw th;
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not copy " + uri);
        } catch (Throwable th2) {
            th2.addSuppressed(th2);
        }
        return temp;
    }

    public static File createNewFile(Context context, String suffix) {
        return createNewFile(context.getCacheDir(), suffix);
    }

    public static File createNewFile(File directory, String suffix) {
        return new File(directory, UUID.randomUUID().toString() + suffix);
    }

    public static File createNewFile(String directory, String suffix) {
        return new File(directory, UUID.randomUUID().toString() + suffix);
    }

    public static String initializeLogFile(Context context) {
        File folder;
        if (Build.VERSION.SDK_INT >= 29) {
            folder = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), LOG_FOLDER_NAME);
        } else {
            File folder2 = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + context.getPackageName() + File.separator + LOG_FOLDER_NAME);
            if (folder2.exists() || folder2.mkdir()) {
                folder = folder2;
            } else {
                folder = null;
            }
        }
        if (folder == null || folder.exists() || folder.mkdir()) {
            return new File(folder, LOG_FILE_NAME).getAbsolutePath();
        }
        return "";
    }
}
