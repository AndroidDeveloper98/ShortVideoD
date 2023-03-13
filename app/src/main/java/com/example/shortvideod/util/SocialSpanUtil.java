package com.example.shortvideod.util;

import android.widget.TextView;
import com.example.shortvideod.util.SocialSpan;
import java.util.Objects;
import java.util.regex.Pattern;

public final class SocialSpanUtil {
    private static final Pattern PATTERN_HASHTAG = Pattern.compile("#\\w+");
    private static final Pattern PATTERN_MENTION = Pattern.compile("@\\w[\\w.]+\\w");
    private static final Pattern PATTERN_URL = Pattern.compile("(?:(?:https?)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?");

    public interface OnSocialLinkClickListener {
        void onSocialHashtagClick(String str);

        void onSocialMentionClick(String str);

        void onSocialUrlClick(String str);
    }

    public static void apply(TextView into, CharSequence text, OnSocialLinkClickListener listener) {
        SocialSpan span = new SocialSpan();
        if (listener != null) {
            Pattern pattern = PATTERN_HASHTAG;
            Objects.requireNonNull(listener);
            span.match(new SocialSpan.SocialSpanPattern(pattern, (SocialSpan.OnSpanStyleListener) null, listener::onSocialHashtagClick));
        } else {
            span.match(new SocialSpan.SocialSpanPattern(PATTERN_HASHTAG, (SocialSpan.OnSpanStyleListener) null, (SocialSpan.OnSpanClickListener) null));
        }
        if (listener != null) {
            Pattern pattern2 = PATTERN_MENTION;
            Objects.requireNonNull(listener);
            span.match(new SocialSpan.SocialSpanPattern(pattern2, (SocialSpan.OnSpanStyleListener) null, listener::onSocialMentionClick));
        } else {
            span.match(new SocialSpan.SocialSpanPattern(PATTERN_MENTION, (SocialSpan.OnSpanStyleListener) null, (SocialSpan.OnSpanClickListener) null));
        }
        if (listener != null) {
            Pattern pattern3 = PATTERN_URL;
            Objects.requireNonNull(listener);
            span.match(new SocialSpan.SocialSpanPattern(pattern3, (SocialSpan.OnSpanStyleListener) null, listener::onSocialUrlClick));
        } else {
            span.match(new SocialSpan.SocialSpanPattern(PATTERN_URL, (SocialSpan.OnSpanStyleListener) null, (SocialSpan.OnSpanClickListener) null));
        }
        span.apply(into, text);
    }
}
