package com.example.shortvideod.activity.trimmer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

import com.example.shortvideod.R;
import com.google.android.exoplayer2.extractor.ts.TsExtractor;
/*import com.example.shortvideod.activity.trimmer.TimeBar;
import com.example.shortvideod.demo.R;
import com.google.android.exoplayer2.extractor.p011ts.TsExtractor;*/

public class TrimTimeBar extends TimeBar {
    public static final int SCRUBBER_CURRENT = 2;
    public static final int SCRUBBER_END = 3;
    public static final int SCRUBBER_NONE = 0;
    public static final int SCRUBBER_START = 1;
    private int mPressedThumb = 0;
    private final Bitmap mTrimEndScrubber;
    private int mTrimEndScrubberLeft = 0;
    private int mTrimEndScrubberTop = 0;
    private int mTrimEndTime = 0;
    private final Bitmap mTrimStartScrubber;
    private int mTrimStartScrubberLeft = 0;
    private int mTrimStartScrubberTop = 0;
    private int mTrimStartTime = 0;

    public TrimTimeBar(Context context, TimeBar.Listener listener) {
        super(context, listener);
        Bitmap b1 = BitmapFactory.decodeResource(getResources(), R.drawable.text_select_handle_left);
        Bitmap b2 = BitmapFactory.decodeResource(getResources(), R.drawable.text_select_handle_right);
        this.mTrimStartScrubber = Bitmap.createScaledBitmap(b1, 100, TsExtractor.TS_STREAM_TYPE_HDMV_DTS, true);
        this.mTrimEndScrubber = Bitmap.createScaledBitmap(b2, 100, TsExtractor.TS_STREAM_TYPE_HDMV_DTS, true);
        this.mScrubberPadding = 0;
        this.mVPaddingInPx = (this.mVPaddingInPx * 3) / 2;
    }

    private int getBarPosFromTime(int time) {
        return this.mProgressBar.left + ((int) ((((long) this.mProgressBar.width()) * ((long) time)) / ((long) this.mTotalTime)));
    }

    private int trimStartScrubberTipOffset() {
        return (this.mTrimStartScrubber.getWidth() * 3) / 4;
    }

    private int trimEndScrubberTipOffset() {
        return this.mTrimEndScrubber.getWidth() / 4;
    }

    private void updatePlayedBarAndScrubberFromTime() {
        this.mPlayedBar.set(this.mProgressBar);
        if (this.mTotalTime > 0) {
            this.mPlayedBar.left = getBarPosFromTime(this.mTrimStartTime);
            this.mPlayedBar.right = getBarPosFromTime(this.mCurrentTime);
            if (!this.mScrubbing) {
                this.mScrubberLeft = this.mPlayedBar.right - (this.mScrubber.getWidth() / 2);
                this.mTrimStartScrubberLeft = this.mPlayedBar.left - trimStartScrubberTipOffset();
                this.mTrimEndScrubberLeft = getBarPosFromTime(this.mTrimEndTime) - trimEndScrubberTipOffset();
                return;
            }
            return;
        }
        this.mPlayedBar.right = this.mProgressBar.left;
        this.mScrubberLeft = this.mProgressBar.left - (this.mScrubber.getWidth() / 2);
        this.mTrimStartScrubberLeft = this.mProgressBar.left - trimStartScrubberTipOffset();
        this.mTrimEndScrubberLeft = this.mProgressBar.right - trimEndScrubberTipOffset();
    }

    private void initTrimTimeIfNeeded() {
        if (this.mTotalTime > 0 && this.mTrimEndTime == 0) {
            this.mTrimEndTime = this.mTotalTime;
        }
    }

    private void update() {
        initTrimTimeIfNeeded();
        updatePlayedBarAndScrubberFromTime();
        invalidate();
    }

    public void setTime(int currentTime, int totalTime, int trimStartTime, int trimEndTime) {
        if (this.mCurrentTime != currentTime || this.mTotalTime != totalTime || this.mTrimStartTime != trimStartTime || this.mTrimEndTime != trimEndTime) {
            this.mCurrentTime = currentTime;
            this.mTotalTime = totalTime;
            this.mTrimStartTime = trimStartTime;
            this.mTrimEndTime = trimEndTime;
            update();
        }
    }

    private int whichScrubber(float x, float y) {
        if (inScrubber(x, y, this.mTrimStartScrubberLeft, this.mTrimStartScrubberTop, this.mTrimStartScrubber)) {
            return 1;
        }
        if (inScrubber(x, y, this.mTrimEndScrubberLeft, this.mTrimEndScrubberTop, this.mTrimEndScrubber)) {
            return 3;
        }
        if (inScrubber(x, y, this.mScrubberLeft, this.mScrubberTop, this.mScrubber)) {
            return 2;
        }
        return 0;
    }

    private boolean inScrubber(float x, float y, int startX, int startY, Bitmap scrubber) {
        return ((float) startX) < x && x < ((float) (scrubber.getWidth() + startX)) && ((float) startY) < y && y < ((float) (scrubber.getHeight() + startY));
    }

    private int clampScrubber(int scrubberLeft, int offset, int lowerBound, int upperBound) {
        return Math.min(upperBound - offset, Math.max(lowerBound - offset, scrubberLeft));
    }

    private int getScrubberTime(int scrubberLeft, int offset) {
        return (int) ((((long) ((scrubberLeft + offset) - this.mProgressBar.left)) * ((long) this.mTotalTime)) / ((long) this.mProgressBar.width()));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        int w = r - l;
        int h = b - t;
        if (this.mShowTimes || this.mShowScrubber) {
            int margin = this.mScrubber.getWidth() / 3;
            if (this.mShowTimes) {
                margin += this.mTimeBounds.width();
            }
            int progressY = h / 4;
            this.mScrubberTop = (progressY - (this.mScrubber.getHeight() / 2)) + 1;
            this.mTrimStartScrubberTop = progressY;
            this.mTrimEndScrubberTop = progressY;
            this.mProgressBar.set(getPaddingLeft() + margin, progressY, (w - getPaddingRight()) - margin, progressY + 4);
        } else {
            this.mProgressBar.set(0, 0, w, h);
        }
        update();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        canvas.drawRect(this.mProgressBar, this.mProgressPaint);
        canvas.drawRect(this.mPlayedBar, this.mPlayedPaint);
        if (this.mShowTimes) {
            canvas.drawText(stringForTime((long) this.mCurrentTime), (float) ((this.mTimeBounds.width() / 2) + getPaddingLeft()), (float) ((this.mTimeBounds.height() / 2) + this.mTrimStartScrubberTop), this.mTimeTextPaint);
            canvas.drawText(stringForTime((long) this.mTotalTime), (float) ((getWidth() - getPaddingRight()) - (this.mTimeBounds.width() / 2)), (float) ((this.mTimeBounds.height() / 2) + this.mTrimStartScrubberTop), this.mTimeTextPaint);
        }
        if (this.mShowScrubber) {
            canvas.drawBitmap(this.mScrubber, (float) this.mScrubberLeft, (float) this.mScrubberTop, (Paint) null);
            canvas.drawBitmap(this.mTrimStartScrubber, (float) this.mTrimStartScrubberLeft, (float) this.mTrimStartScrubberTop, (Paint) null);
            canvas.drawBitmap(this.mTrimEndScrubber, (float) this.mTrimEndScrubberLeft, (float) this.mTrimEndScrubberTop, (Paint) null);
        }
    }

    private void updateTimeFromPos() {
        this.mCurrentTime = getScrubberTime(this.mScrubberLeft, this.mScrubber.getWidth() / 2);
        this.mTrimStartTime = getScrubberTime(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset());
        this.mTrimEndTime = getScrubberTime(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset());
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (this.mShowScrubber) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            switch (event.getAction()) {
                case 0:
                    int whichScrubber = whichScrubber((float) x, (float) y);
                    this.mPressedThumb = whichScrubber;
                    switch (whichScrubber) {
                        case 1:
                            this.mScrubbing = true;
                            this.mScrubberCorrection = x - this.mTrimStartScrubberLeft;
                            break;
                        case 2:
                            this.mScrubbing = true;
                            this.mScrubberCorrection = x - this.mScrubberLeft;
                            break;
                        case 3:
                            this.mScrubbing = true;
                            this.mScrubberCorrection = x - this.mTrimEndScrubberLeft;
                            break;
                    }
                    if (this.mScrubbing) {
                        this.mListener.onScrubbingStart();
                        return true;
                    }
                    break;
                case 1:
                case 3:
                    if (this.mScrubbing) {
                        int seekToTime = 0;
                        switch (this.mPressedThumb) {
                            case 1:
                                seekToTime = getScrubberTime(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset());
                                this.mScrubberLeft = (this.mTrimStartScrubberLeft + trimStartScrubberTipOffset()) - (this.mScrubber.getWidth() / 2);
                                break;
                            case 2:
                                seekToTime = getScrubberTime(this.mScrubberLeft, this.mScrubber.getWidth() / 2);
                                break;
                            case 3:
                                seekToTime = getScrubberTime(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset());
                                this.mScrubberLeft = (this.mTrimEndScrubberLeft + trimEndScrubberTipOffset()) - (this.mScrubber.getWidth() / 2);
                                break;
                        }
                        updateTimeFromPos();
                        this.mListener.onScrubbingEnd(seekToTime, getScrubberTime(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset()), getScrubberTime(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset()));
                        this.mScrubbing = false;
                        this.mPressedThumb = 0;
                        return true;
                    }
                    break;
                case 2:
                    if (this.mScrubbing) {
                        int seekToTime2 = -1;
                        int lowerBound = this.mTrimStartScrubberLeft + trimStartScrubberTipOffset();
                        int upperBound = this.mTrimEndScrubberLeft + trimEndScrubberTipOffset();
                        switch (this.mPressedThumb) {
                            case 1:
                                int i = x - this.mScrubberCorrection;
                                this.mTrimStartScrubberLeft = i;
                                int i2 = this.mTrimEndScrubberLeft;
                                if (i > i2) {
                                    this.mTrimStartScrubberLeft = i2;
                                }
                                int clampScrubber = clampScrubber(this.mTrimStartScrubberLeft, trimStartScrubberTipOffset(), this.mProgressBar.left, upperBound);
                                this.mTrimStartScrubberLeft = clampScrubber;
                                seekToTime2 = getScrubberTime(clampScrubber, trimStartScrubberTipOffset());
                                break;
                            case 2:
                                this.mScrubberLeft = x - this.mScrubberCorrection;
                                this.mScrubberLeft = clampScrubber(this.mScrubberLeft, this.mScrubber.getWidth() / 2, lowerBound, upperBound);
                                seekToTime2 = getScrubberTime(this.mScrubberLeft, this.mScrubber.getWidth() / 2);
                                break;
                            case 3:
                                this.mTrimEndScrubberLeft = x - this.mScrubberCorrection;
                                int clampScrubber2 = clampScrubber(this.mTrimEndScrubberLeft, trimEndScrubberTipOffset(), lowerBound, this.mProgressBar.right);
                                this.mTrimEndScrubberLeft = clampScrubber2;
                                seekToTime2 = getScrubberTime(clampScrubber2, trimEndScrubberTipOffset());
                                break;
                        }
                        updateTimeFromPos();
                        updatePlayedBarAndScrubberFromTime();
                        if (seekToTime2 != -1) {
                            this.mListener.onScrubbingMove(seekToTime2);
                        }
                        invalidate();
                        return true;
                    }
                    break;
            }
        }
        return false;
    }
}
