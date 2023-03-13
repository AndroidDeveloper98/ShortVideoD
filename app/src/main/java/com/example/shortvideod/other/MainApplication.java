package com.example.shortvideod.other;

import android.app.Application;
import android.util.Log;

import com.example.shortvideod.providers.ExoPlayerProvider;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.vaibhavpandey.katora.Container;

import java.io.File;

public class MainApplication extends Application {
    public static SimpleCache simpleCache = null;
    LeastRecentlyUsedCacheEvictor leastRecentlyUsedCacheEvictor = null;
    Long exoPlayerCacheSize = (long) (90 * 1024 * 1024);
    ExoDatabaseProvider exoDatabaseProvider = null;
    Container comt = new Container();


    @Override
    public void onCreate() {
        super.onCreate();

        if (leastRecentlyUsedCacheEvictor == null) {
            leastRecentlyUsedCacheEvictor = new LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize);
        }


        if (simpleCache == null) {
            simpleCache = new SimpleCache(getCacheDir(), leastRecentlyUsedCacheEvictor, exoDatabaseProvider);
            if (simpleCache.getCacheSpace() >= 400207768) {
                //
            }
            Log.i("TAG", "onCreate: " + simpleCache.getCacheSpace());
        }

        comt.install(new ExoPlayerProvider(this));
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
