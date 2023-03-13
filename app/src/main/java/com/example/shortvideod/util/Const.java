package com.example.shortvideod.util;

import android.content.Context;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Const {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final String CHATUSERIMAGE = "chatuserimage";
    public static final String CHATUSERNAME = "chatusername";
    public static String Currency = FirebaseAnalytics.Param.CURRENCY;
    public static String DATA = "data";
    public static String HASHTAG = "hashtag";
    public static String ISLOGIN = FirebaseAnalytics.Event.LOGIN;
    public static final int MAX_CHAR_HSHTAG = 10;
    public static final long MAX_DURATION = TimeUnit.SECONDS.toMillis(30);
    public static final int MAX_RESOLUTION = 960;
    public static final long MIN_DURATION = TimeUnit.SECONDS.toMillis(5);
    public static final int NOTIFICATION_DOWNLOAD = 60601;
    public static final int NOTIFICATION_GENERATE_PREVIEW = 60602;
    public static final int NOTIFICATION_UPLOAD = 60604;
    public static final int NOTIFICATION_UPLOAD_FAILED = 60605;
    public static final String REELSUSERIMAGE = "reelsuserimage";
    public static String REELSUSERNAME = "reelsusername";
    public static final int REQUEST_CODE_PERMISSIONS_UPLOAD = 200;
    public static final int REQUEST_CODE_PICK_SONG = 60605;
    public static final int REQUEST_CODE_PICK_SONG_FILE = 60606;
    public static final int REQUEST_CODE_PICK_STICKER = 60607;
    public static final int REQUEST_CODE_PICK_VIDEO = 100;
    public static final String TEMP_FOLDER_NAME = "TempFolder";
    public static String TestPurchase = "android.test.purchased";
    public static String USERIMAGE = "userimage";
    public static final String USERLIST = "userlist";
    public static String USERNAMELIST = "usernamelist";

    public static ArrayList<String> listFilterThumb(Context context) {
        ArrayList<String> typefaceArrayList = new ArrayList<>();
        try {
            String[] list = context.getAssets().list("filter");
            if (list != null) {
                if (list.length > 0) {
                    for (String file : list) {
                        typefaceArrayList.add("file:///android_asset/filter/" + file);
                    }
                }
                return typefaceArrayList;
            }
            throw new AssertionError();
        } catch (IOException e) {
            return null;
        }
    }

    public static ArrayList<String> listAssetFilterThumb(Context context) {
        ArrayList<String> typefaceArrayList = new ArrayList<>();
        try {
            String[] list = context.getAssets().list("filterthumb");
            if (list != null) {
                if (list.length > 0) {
                    for (String file : list) {
                        typefaceArrayList.add("file:///android_asset/filterthumb/" + file);
                    }
                }
                return typefaceArrayList;
            }
            throw new AssertionError();
        } catch (IOException e) {
            return null;
        }
    }
}
