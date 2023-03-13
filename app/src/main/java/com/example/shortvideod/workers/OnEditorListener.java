package com.example.shortvideod.workers;

public interface OnEditorListener {
    void onFailure();

    void onProgress(float f);

    void onSuccess();
}

