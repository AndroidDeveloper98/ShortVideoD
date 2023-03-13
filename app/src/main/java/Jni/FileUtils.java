package Jni;

import android.util.Log;

import java.io.File;

public class FileUtils {
    public static File makeFilePath(String str, String str2) {
        makeRootDirectory(str);
        File file = null;
        try {
            File file2 = new File(str + str2);
            try {
                if (file2.exists()) {
                    return file2;
                }
                file2.createNewFile();
                return file2;
            } catch (Exception e) {
                e = e;
                file = file2;
                e.printStackTrace();
                return file;
            }
        } catch (Exception e2) {
            return file;
        }
    }

    public static void makeRootDirectory(String str) {
        try {
            File file = new File(str);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }
}
