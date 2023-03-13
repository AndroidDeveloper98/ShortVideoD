package com.example.shortvideod.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class TextFormatUtil {
    private static final DateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    public static Date toDate(String input) {
        try {
            return mDateFormat.parse(input);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String toShortNumber(long count) {
        if (count < 1000) {
            return count + "";
        }
        int exp = (int) (Math.log((double) count) / Math.log(1000.0d));
        return String.format("%.1f %c", new Object[]{Double.valueOf(((double) count) / Math.pow(1000.0d, (double) exp)), Character.valueOf("kMGTPE".charAt(exp - 1))});
    }

    public static String toMMSS(long millis) {
        long mm = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis));
        long ss = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));
        return String.format(Locale.US, "%02d:%02d", new Object[]{Long.valueOf(mm), Long.valueOf(ss)});
    }
}