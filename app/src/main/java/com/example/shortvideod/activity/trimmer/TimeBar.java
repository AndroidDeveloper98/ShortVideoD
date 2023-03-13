package com.example.shortvideod.activity.trimmer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.shortvideod.R;

public class TimeBar extends View {
    private static final int SCRUBBER_PADDING_IN_DP = 20;
    private static final int TEXT_SIZE_IN_DP = 14;
    private static final int V_PADDING_IN_DP = 30;
    protected int mCurrentTime;
    protected final Listener mListener;
    protected final Rect mPlayedBar = new Rect();
    protected final Paint mPlayedPaint;
    protected final Rect mProgressBar = new Rect();
    protected final Paint mProgressPaint;
    protected final Bitmap mScrubber;
    protected int mScrubberCorrection;
    protected int mScrubberLeft;
    protected int mScrubberPadding;
    protected int mScrubberTop;
    protected boolean mScrubbing;
    protected boolean mShowScrubber = true;
    protected boolean mShowTimes = true;
    protected final Rect mTimeBounds;
    protected final Paint mTimeTextPaint;
    protected int mTotalTime;
    protected int mVPaddingInPx;

    public static interface Listener {
        void onScrubbingEnd(int i, int i2, int i3);

        void onScrubbingMove(int i);

        void onScrubbingStart();
    }

    public TimeBar(Context context, Listener listener) {
        super(context);
        this.mListener = listener;
        Paint paint = new Paint();
        this.mProgressPaint = paint;
        paint.setColor(-8355712);
        Paint paint2 = new Paint();
        this.mPlayedPaint = paint2;
        paint2.setColor(-1);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Paint paint3 = new Paint(1);
        this.mTimeTextPaint = paint3;
        paint3.setColor(-3223858);
        paint3.setTextSize(metrics.density * 14.0f);
        paint3.setTextAlign(Paint.Align.CENTER);
        Rect rect = new Rect();
        this.mTimeBounds = rect;
        paint3.getTextBounds("0:00:00", 0, 7, rect);
        this.mScrubber = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.scrubber_knob), 100, 100, true);
        this.mScrubberPadding = (int) (metrics.density * 20.0f);
        this.mVPaddingInPx = (int) (metrics.density * 30.0f);
    }

    private void update() {
        this.mPlayedBar.set(this.mProgressBar);
        if (this.mTotalTime > 0) {
            Rect rect = this.mPlayedBar;
            rect.right = rect.left + ((int) ((((long) this.mProgressBar.width()) * ((long) this.mCurrentTime)) / ((long) this.mTotalTime)));
        } else {
            this.mPlayedBar.right = this.mProgressBar.left;
        }
        if (!this.mScrubbing) {
            this.mScrubberLeft = this.mPlayedBar.right - (this.mScrubber.getWidth() / 2);
        }
        invalidate();
    }

    public int getPreferredHeight() {
        return this.mTimeBounds.height() + this.mVPaddingInPx + this.mScrubberPadding;
    }

    public int getBarHeight() {
        return this.mTimeBounds.height() + this.mVPaddingInPx;
    }

    public void setTime(int currentTime, int totalTime, int trimStartTime, int trimEndTime) {
        if (this.mCurrentTime != currentTime || this.mTotalTime != totalTime) {
            this.mCurrentTime = currentTime;
            this.mTotalTime = totalTime;
            update();
        }
    }

    private boolean inScrubber(float x, float y) {
        int scrubberRight = this.mScrubberLeft + this.mScrubber.getWidth();
        int scrubberBottom = this.mScrubberTop + this.mScrubber.getHeight();
        int i = this.mScrubberLeft;
        int i2 = this.mScrubberPadding;
        return ((float) (i - i2)) < x && x < ((float) (scrubberRight + i2)) && ((float) (this.mScrubberTop - i2)) < y && y < ((float) (i2 + scrubberBottom));
    }

    private void clampScrubber() {
        int half = this.mScrubber.getWidth() / 2;
        this.mScrubberLeft = Math.min(this.mProgressBar.right - half, Math.max(this.mProgressBar.left - half, this.mScrubberLeft));
    }

    private int getScrubberTime() {
        return (int) ((((long) ((this.mScrubberLeft + (this.mScrubber.getWidth() / 2)) - this.mProgressBar.left)) * ((long) this.mTotalTime)) / ((long) this.mProgressBar.width()));
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
            int progressY = (this.mScrubberPadding + h) / 2;
            this.mScrubberTop = (progressY - (this.mScrubber.getHeight() / 2)) + 1;
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
        if (this.mShowScrubber) {
            canvas.drawBitmap(this.mScrubber, (float) this.mScrubberLeft, (float) this.mScrubberTop, (Paint) null);
        }
        if (this.mShowTimes) {
            canvas.drawText(stringForTime((long) this.mCurrentTime), (float) ((this.mTimeBounds.width() / 2) + getPaddingLeft()), (float) (this.mTimeBounds.height() + (this.mVPaddingInPx / 2) + this.mScrubberPadding + 1), this.mTimeTextPaint);
            canvas.drawText(stringForTime((long) this.mTotalTime), (float) ((getWidth() - getPaddingRight()) - (this.mTimeBounds.width() / 2)), (float) (this.mTimeBounds.height() + (this.mVPaddingInPx / 2) + this.mScrubberPadding + 1), this.mTimeTextPaint);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0042, code lost:
        r6.mScrubberLeft = r0 - r6.mScrubberCorrection;
        clampScrubber();
        r1 = getScrubberTime();
        r6.mCurrentTime = r1;
        r6.mListener.onScrubbingMove(r1);
        invalidate();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0059, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.mShowScrubber
            r1 = 0
            if (r0 == 0) goto L_0x005a
            float r0 = r7.getX()
            int r0 = (int) r0
            float r2 = r7.getY()
            int r2 = (int) r2
            int r3 = r7.getAction()
            r4 = 1
            switch(r3) {
                case 0: goto L_0x0024;
                case 1: goto L_0x0018;
                case 2: goto L_0x0042;
                case 3: goto L_0x0018;
                default: goto L_0x0017;
            }
        L_0x0017:
            goto L_0x005a
        L_0x0018:
            com.example.shortvideod.activity.trimmer.TimeBar$Listener r3 = r6.mListener
            int r5 = r6.getScrubberTime()
            r3.onScrubbingEnd(r5, r1, r1)
            r6.mScrubbing = r1
            return r4
        L_0x0024:
            float r1 = (float) r0
            float r3 = (float) r2
            boolean r1 = r6.inScrubber(r1, r3)
            if (r1 == 0) goto L_0x0031
            int r1 = r6.mScrubberLeft
            int r1 = r0 - r1
            goto L_0x0039
        L_0x0031:
            android.graphics.Bitmap r1 = r6.mScrubber
            int r1 = r1.getWidth()
            int r1 = r1 / 2
        L_0x0039:
            r6.mScrubberCorrection = r1
            r6.mScrubbing = r4
            com.example.shortvideod.activity.trimmer.TimeBar$Listener r1 = r6.mListener
            r1.onScrubbingStart()
        L_0x0042:
            int r1 = r6.mScrubberCorrection
            int r1 = r0 - r1
            r6.mScrubberLeft = r1
            r6.clampScrubber()
            int r1 = r6.getScrubberTime()
            r6.mCurrentTime = r1
            com.example.shortvideod.activity.trimmer.TimeBar$Listener r3 = r6.mListener
            r3.onScrubbingMove(r1)
            r6.invalidate()
            return r4
        L_0x005a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.example.shortvideod.activity.trimmer.TimeBar.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* access modifiers changed from: protected */
    public String stringForTime(long millis) {
        int totalSeconds = ((int) millis) / 1000;
        int seconds = totalSeconds % 60;
        int minutes = (totalSeconds / 60) % 60;
        int hours = totalSeconds / 3600;
        if (hours > 0) {
            return String.format("%d:%02d:%02d", new Object[]{Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds)});
        }
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(minutes), Integer.valueOf(seconds)});
    }

    public void setSeekable(boolean canSeek) {
        this.mShowScrubber = canSeek;
    }
}