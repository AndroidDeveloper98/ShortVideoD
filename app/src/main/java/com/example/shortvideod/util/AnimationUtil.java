package com.example.shortvideod.util;

import android.view.View;

public final class AnimationUtil {
    public static void toggleVisibilityToBottom(View view, boolean show) {
        view.setAlpha(show ? 0.0f : 1.0f);
        if (show) {
            view.animate().translationY(0.0f).alpha(1.0f);
        } else {
            view.animate().translationY((float) view.getHeight()).alpha(0.0f);
        }
    }

    public static void toggleVisibilityToLeft(View view, boolean show) {
        view.setAlpha(show ? 0.0f : 1.0f);
        if (show) {
            view.animate().translationX(0.0f).alpha(1.0f);
        } else {
            view.animate().translationX((float) (-view.getWidth())).alpha(0.0f);
        }
    }

    public static void toggleVisibilityToRight(View view, boolean show) {
        view.setAlpha(show ? 0.0f : 1.0f);
        if (show) {
            view.animate().translationX(0.0f).alpha(1.0f);
        } else {
            view.animate().translationX((float) view.getWidth()).alpha(0.0f);
        }
    }

    public static void toggleVisibilityToTop(View view, boolean show) {
        view.setAlpha(show ? 0.0f : 1.0f);
        if (show) {
            view.animate().translationY(0.0f).alpha(1.0f);
        } else {
            view.animate().translationY((float) (-view.getHeight())).alpha(0.0f);
        }
    }
}
