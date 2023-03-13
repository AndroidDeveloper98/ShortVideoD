package com.example.shortvideod.design;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import androidx.core.view.MotionEventCompat;

import com.example.shortvideod.R;

public class StickerView extends ImageView {
    private static final float BITMAP_SCALE = 0.7f;
    private static final String TAG = "StickerView";
    private float MAX_SCALE = 1.2f;
    private float MIN_SCALE = 0.5f;
    private Bitmap deleteBitmap;
    private int deleteBitmapHeight;
    private int deleteBitmapWidth;

    /* renamed from: dm */
    private DisplayMetrics f3dm;
    private Rect dst_delete;
    private Rect dst_flipV;
    private Rect dst_resize;
    private Rect dst_top;
    private Bitmap flipVBitmap;
    private int flipVBitmapHeight;
    private int flipVBitmapWidth;
    private double halfDiagonalLength;
    private boolean isHorizonMirror = false;
    private boolean isInEdit = true;
    private boolean isInResize = false;
    private boolean isInSide;
    private boolean isPointerDown = false;
    private float lastLength;
    private float lastRotateDegree;
    private float lastX;
    private float lastY;
    private Paint localPaint;
    private Bitmap mBitmap;
    private int mScreenHeight;
    private int mScreenwidth;
    private Matrix matrix = new Matrix();
    private PointF mid = new PointF();
    private float oldDis;
    private OperationListener operationListener;
    private float oringinWidth = 0.0f;
    private final float pointerLimitDis = 20.0f;
    private final float pointerZoomCoeff = 0.09f;
    private Bitmap resizeBitmap;
    private int resizeBitmapHeight;
    private int resizeBitmapWidth;
    private final long stickerId = 0;
    private Bitmap topBitmap;
    private int topBitmapHeight;
    private int topBitmapWidth;

    public interface OperationListener {
        void onDeleteClick();

        void onEdit(StickerView stickerView);

        void onTop(StickerView stickerView);
    }

    public StickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickerView(Context context) {
        super(context);
        init();
    }

    public StickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.dst_delete = new Rect();
        this.dst_resize = new Rect();
        this.dst_flipV = new Rect();
        this.dst_top = new Rect();
        Paint paint = new Paint();
        this.localPaint = paint;
        paint.setColor(getResources().getColor(R.color.white));
        this.localPaint.setAntiAlias(true);
        this.localPaint.setDither(true);
        this.localPaint.setStyle(Paint.Style.STROKE);
        this.localPaint.setStrokeWidth(2.0f);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.f3dm = displayMetrics;
        this.mScreenwidth = displayMetrics.widthPixels;
        this.mScreenHeight = this.f3dm.heightPixels;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        Canvas canvas2 = canvas;
        if (this.mBitmap != null) {
            float[] arrayOfFloat = new float[9];
            this.matrix.getValues(arrayOfFloat);
            float f1 = (arrayOfFloat[0] * 0.0f) + (arrayOfFloat[1] * 0.0f) + arrayOfFloat[2];
            float f2 = arrayOfFloat[5] + (arrayOfFloat[3] * 0.0f) + (arrayOfFloat[4] * 0.0f);
            float f3 = arrayOfFloat[2] + (arrayOfFloat[0] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat[1] * 0.0f);
            float f4 = arrayOfFloat[5] + (arrayOfFloat[3] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat[4] * 0.0f);
            float f5 = (arrayOfFloat[0] * 0.0f) + (arrayOfFloat[1] * ((float) this.mBitmap.getHeight())) + arrayOfFloat[2];
            float f6 = (arrayOfFloat[3] * 0.0f) + (arrayOfFloat[4] * ((float) this.mBitmap.getHeight())) + arrayOfFloat[5];
            float f7 = (arrayOfFloat[0] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat[1] * ((float) this.mBitmap.getHeight())) + arrayOfFloat[2];
            float f8 = (arrayOfFloat[3] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat[4] * ((float) this.mBitmap.getHeight())) + arrayOfFloat[5];
            canvas.save();
            canvas2.drawBitmap(this.mBitmap, this.matrix, (Paint) null);
            this.dst_delete.left = (int) (f1 - ((float) (this.deleteBitmapWidth / 2)));
            this.dst_delete.right = (int) (((float) (this.deleteBitmapWidth / 2)) + f1);
            this.dst_delete.top = (int) (f2 - ((float) (this.deleteBitmapHeight / 2)));
            this.dst_delete.bottom = (int) (((float) (this.deleteBitmapHeight / 2)) + f2);
            this.dst_resize.left = (int) (f7 - ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.right = (int) (f7 + ((float) (this.resizeBitmapWidth / 2)));
            this.dst_resize.top = (int) (f8 - ((float) (this.resizeBitmapHeight / 2)));
            this.dst_resize.bottom = (int) (((float) (this.resizeBitmapHeight / 2)) + f8);
            this.dst_top.left = (int) (f3 - ((float) (this.flipVBitmapWidth / 2)));
            this.dst_top.right = (int) (((float) (this.flipVBitmapWidth / 2)) + f3);
            this.dst_top.top = (int) (f4 - ((float) (this.flipVBitmapHeight / 2)));
            this.dst_top.bottom = (int) (((float) (this.flipVBitmapHeight / 2)) + f4);
            this.dst_flipV.left = (int) (f5 - ((float) (this.topBitmapWidth / 2)));
            this.dst_flipV.right = (int) (((float) (this.topBitmapWidth / 2)) + f5);
            this.dst_flipV.top = (int) (f6 - ((float) (this.topBitmapHeight / 2)));
            this.dst_flipV.bottom = (int) (f6 + ((float) (this.topBitmapHeight / 2)));
            if (this.isInEdit) {
                Canvas canvas3 = canvas;
                canvas3.drawLine(f1, f2, f3, f4, this.localPaint);
                float f = f7;
                float f9 = f8;
                canvas3.drawLine(f3, f4, f, f9, this.localPaint);
                float f10 = f5;
                float f11 = f6;
                canvas3.drawLine(f10, f11, f, f9, this.localPaint);
                canvas3.drawLine(f10, f11, f1, f2, this.localPaint);
                canvas2.drawBitmap(this.deleteBitmap, (Rect) null, this.dst_delete, (Paint) null);
                canvas2.drawBitmap(this.resizeBitmap, (Rect) null, this.dst_resize, (Paint) null);
                canvas2.drawBitmap(this.flipVBitmap, (Rect) null, this.dst_flipV, (Paint) null);
            }
            canvas.restore();
        }
    }

    public void setImageResource(int resId) {
        setBitmap(BitmapFactory.decodeResource(getResources(), resId));
    }

    public void setBitmap(Bitmap bitmap) {
        this.matrix.reset();
        this.mBitmap = bitmap;
        setDiagonalLength();
        initBitmaps();
        int w = this.mBitmap.getWidth();
        int h = this.mBitmap.getHeight();
        this.oringinWidth = (float) w;
        float initScale = (this.MIN_SCALE + this.MAX_SCALE) / 2.0f;
        this.matrix.postScale(initScale, initScale, (float) (w / 2), (float) (h / 2));
        Matrix matrix2 = this.matrix;
        int i = this.mScreenwidth;
        matrix2.postTranslate((float) ((i / 2) - (w / 2)), (float) ((i / 2) - (h / 2)));
        invalidate();
    }

    private void setDiagonalLength() {
        this.halfDiagonalLength = Math.hypot((double) this.mBitmap.getWidth(), (double) this.mBitmap.getHeight()) / 2.0d;
    }

    private void initBitmaps() {
        if (this.mBitmap.getWidth() >= this.mBitmap.getHeight()) {
            float minWidth = (float) (this.mScreenwidth / 8);
            if (((float) this.mBitmap.getWidth()) < minWidth) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (minWidth * 1.0f) / ((float) this.mBitmap.getWidth());
            }
            int width = this.mBitmap.getWidth();
            int i = this.mScreenwidth;
            if (width > i) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (((float) i) * 1.0f) / ((float) this.mBitmap.getWidth());
            }
        } else {
            float minHeight = (float) (this.mScreenwidth / 8);
            if (((float) this.mBitmap.getHeight()) < minHeight) {
                this.MIN_SCALE = 1.0f;
            } else {
                this.MIN_SCALE = (minHeight * 1.0f) / ((float) this.mBitmap.getHeight());
            }
            int height = this.mBitmap.getHeight();
            int i2 = this.mScreenwidth;
            if (height > i2) {
                this.MAX_SCALE = 1.0f;
            } else {
                this.MAX_SCALE = (((float) i2) * 1.0f) / ((float) this.mBitmap.getHeight());
            }
        }
        this.topBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_editicon);
        this.deleteBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_deleteicon);
        this.flipVBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_flipicon);
        this.resizeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_resize);
        this.deleteBitmapWidth = (int) (((float) this.deleteBitmap.getWidth()) * 0.7f);
        this.deleteBitmapHeight = (int) (((float) this.deleteBitmap.getHeight()) * 0.7f);
        this.resizeBitmapWidth = (int) (((float) this.resizeBitmap.getWidth()) * 0.7f);
        this.resizeBitmapHeight = (int) (((float) this.resizeBitmap.getHeight()) * 0.7f);
        this.flipVBitmapWidth = (int) (((float) this.flipVBitmap.getWidth()) * 0.7f);
        this.flipVBitmapHeight = (int) (((float) this.flipVBitmap.getHeight()) * 0.7f);
        this.topBitmapWidth = (int) (((float) this.topBitmap.getWidth()) * 0.7f);
        this.topBitmapHeight = (int) (((float) this.topBitmap.getHeight()) * 0.7f);
    }

    public boolean onTouchEvent(MotionEvent event) {
        OperationListener operationListener2;
        float scale;
        boolean handled = true;
        switch (MotionEventCompat.getActionMasked(event)) {
            case 0:
                if (!isInButton(event, this.dst_delete)) {
                    if (!isInResize(event)) {
                        if (!isInButton(event, this.dst_flipV)) {
                            if (!isInButton(event, this.dst_top)) {
                                if (!isInBitmap(event)) {
                                    handled = false;
                                    break;
                                } else {
                                    this.isInSide = true;
                                    this.lastX = event.getX(0);
                                    this.lastY = event.getY(0);
                                    break;
                                }
                            } else {
                                bringToFront();
                                OperationListener operationListener3 = this.operationListener;
                                if (operationListener3 != null) {
                                    operationListener3.onTop(this);
                                    break;
                                }
                            }
                        } else {
                            PointF localPointF = new PointF();
                            midDiagonalPoint(localPointF);
                            this.matrix.postScale(-1.0f, 1.0f, localPointF.x, localPointF.y);
                            this.isHorizonMirror = true ^ this.isHorizonMirror;
                            invalidate();
                            break;
                        }
                    } else {
                        this.isInResize = true;
                        this.lastRotateDegree = rotationToStartPoint(event);
                        midPointToStartPoint(event);
                        this.lastLength = diagonalLength(event);
                        break;
                    }
                } else {
                    OperationListener operationListener4 = this.operationListener;
                    if (operationListener4 != null) {
                        operationListener4.onDeleteClick();
                        break;
                    }
                }
                break;
            case 1:
            case 3:
                this.isInResize = false;
                this.isInSide = false;
                this.isPointerDown = false;
                break;
            case 2:
                if (!this.isPointerDown) {
                    if (!this.isInResize) {
                        if (this.isInSide) {
                            float x = event.getX(0);
                            float y = event.getY(0);
                            this.matrix.postTranslate(x - this.lastX, y - this.lastY);
                            this.lastX = x;
                            this.lastY = y;
                            invalidate();
                            break;
                        }
                    } else {
                        this.matrix.postRotate((rotationToStartPoint(event) - this.lastRotateDegree) * 2.0f, this.mid.x, this.mid.y);
                        this.lastRotateDegree = rotationToStartPoint(event);
                        float scale2 = diagonalLength(event) / this.lastLength;
                        if ((((double) diagonalLength(event)) / this.halfDiagonalLength > ((double) this.MIN_SCALE) || scale2 >= 1.0f) && (((double) diagonalLength(event)) / this.halfDiagonalLength < ((double) this.MAX_SCALE) || scale2 <= 1.0f)) {
                            this.lastLength = diagonalLength(event);
                        } else {
                            scale2 = 1.0f;
                            if (!isInResize(event)) {
                                this.isInResize = false;
                            }
                        }
                        this.matrix.postScale(scale2, scale2, this.mid.x, this.mid.y);
                        invalidate();
                        break;
                    }
                } else {
                    float disNew = spacing(event);
                    if (disNew == 0.0f || disNew < 20.0f) {
                        scale = 1.0f;
                    } else {
                        scale = (((disNew / this.oldDis) - 1.0f) * 0.09f) + 1.0f;
                    }
                    float scaleTemp = (((float) Math.abs(this.dst_flipV.left - this.dst_resize.left)) * scale) / this.oringinWidth;
                    if ((scaleTemp > this.MIN_SCALE || scale >= 1.0f) && (scaleTemp < this.MAX_SCALE || scale <= 1.0f)) {
                        this.lastLength = diagonalLength(event);
                    } else {
                        scale = 1.0f;
                    }
                    this.matrix.postScale(scale, scale, this.mid.x, this.mid.y);
                    invalidate();
                    break;
                }
                break;
            case 5:
                if (spacing(event) > 20.0f) {
                    this.oldDis = spacing(event);
                    this.isPointerDown = true;
                    midPointToStartPoint(event);
                } else {
                    this.isPointerDown = false;
                }
                this.isInSide = false;
                this.isInResize = false;
                break;
        }
        if (handled && (operationListener2 = this.operationListener) != null) {
            operationListener2.onEdit(this);
        }
        return handled;
    }

    public StickerPropertyModel calculate(StickerPropertyModel model) {
        StickerPropertyModel stickerPropertyModel = model;
        float[] v = new float[9];
        this.matrix.getValues(v);
        float tx = v[2];
        float ty = v[5];
        Log.d(TAG, "tx : " + tx + " ty : " + ty);
        float scalex = v[0];
        float skewy = v[3];
        float rScale = (float) Math.sqrt((double) ((scalex * scalex) + (skewy * skewy)));
        Log.d(TAG, "rScale : " + rScale);
        float rAngle = (float) Math.round(Math.atan2((double) v[1], (double) v[0]) * 57.29577951308232d);
        Log.d(TAG, "rAngle : " + rAngle);
        PointF localPointF = new PointF();
        midDiagonalPoint(localPointF);
        Log.d(TAG, " width  : " + (((float) this.mBitmap.getWidth()) * rScale) + " height " + (((float) this.mBitmap.getHeight()) * rScale));
        float minX = localPointF.x;
        float minY = localPointF.y;
        Log.d(TAG, "midX : " + minX + " midY : " + minY);
        PointF pointF = localPointF;
        stickerPropertyModel.setDegree((float) Math.toRadians((double) rAngle));
        stickerPropertyModel.setScaling((((float) this.mBitmap.getWidth()) * rScale) / ((float) this.mScreenwidth));
        stickerPropertyModel.setxLocation(minX / ((float) this.mScreenwidth));
        stickerPropertyModel.setyLocation(minY / ((float) this.mScreenwidth));
        float f = tx;
        stickerPropertyModel.setStickerId(this.stickerId);
        if (this.isHorizonMirror) {
            stickerPropertyModel.setHorizonMirror(1);
        } else {
            stickerPropertyModel.setHorizonMirror(2);
        }
        return stickerPropertyModel;
    }

    private boolean isInBitmap(MotionEvent event) {
        MotionEvent motionEvent = event;
        float[] arrayOfFloat1 = new float[9];
        this.matrix.getValues(arrayOfFloat1);
        float f1 = (arrayOfFloat1[0] * 0.0f) + (arrayOfFloat1[1] * 0.0f) + arrayOfFloat1[2];
        float f2 = (arrayOfFloat1[3] * 0.0f) + (arrayOfFloat1[4] * 0.0f) + arrayOfFloat1[5];
        float f3 = (arrayOfFloat1[0] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat1[1] * 0.0f) + arrayOfFloat1[2];
        float f4 = (arrayOfFloat1[3] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat1[4] * 0.0f) + arrayOfFloat1[5];
        float f5 = (arrayOfFloat1[0] * 0.0f) + (arrayOfFloat1[1] * ((float) this.mBitmap.getHeight())) + arrayOfFloat1[2];
        float f6 = (arrayOfFloat1[3] * 0.0f) + (arrayOfFloat1[4] * ((float) this.mBitmap.getHeight())) + arrayOfFloat1[5];
        float f7 = (arrayOfFloat1[0] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat1[1] * ((float) this.mBitmap.getHeight())) + arrayOfFloat1[2];
        return pointInRect(new float[]{f1, f3, f7, f5}, new float[]{f2, f4, (arrayOfFloat1[3] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat1[4] * ((float) this.mBitmap.getHeight())) + arrayOfFloat1[5], f6}, motionEvent.getX(0), motionEvent.getY(0));
    }

    private boolean pointInRect(float[] xRange, float[] yRange, float x, float y) {
        double a1 = Math.hypot((double) (xRange[0] - xRange[1]), (double) (yRange[0] - yRange[1]));
        double a2 = Math.hypot((double) (xRange[1] - xRange[2]), (double) (yRange[1] - yRange[2]));
        double a3 = Math.hypot((double) (xRange[3] - xRange[2]), (double) (yRange[3] - yRange[2]));
        double a4 = Math.hypot((double) (xRange[0] - xRange[3]), (double) (yRange[0] - yRange[3]));
        double b1 = Math.hypot((double) (x - xRange[0]), (double) (y - yRange[0]));
        double a12 = a1;
        double b2 = Math.hypot((double) (x - xRange[1]), (double) (y - yRange[1]));
        double b3 = Math.hypot((double) (x - xRange[2]), (double) (y - yRange[2]));
        double b4 = Math.hypot((double) (x - xRange[3]), (double) (y - yRange[3]));
        double u1 = ((a12 + b1) + b2) / 2.0d;
        double u2 = ((a2 + b2) + b3) / 2.0d;
        double u3 = ((a3 + b3) + b4) / 2.0d;
        double u4 = ((a4 + b4) + b1) / 2.0d;
        return Math.abs((a12 * a2) - (((Math.sqrt((((u1 - a12) * u1) * (u1 - b1)) * (u1 - b2)) + Math.sqrt((((u2 - a2) * u2) * (u2 - b2)) * (u2 - b3))) + Math.sqrt((((u3 - a3) * u3) * (u3 - b3)) * (u3 - b4))) + Math.sqrt((((u4 - a4) * u4) * (u4 - b4)) * (u4 - b1)))) < 0.5d;
    }

    private boolean isInButton(MotionEvent event, Rect rect) {
        return event.getX(0) >= ((float) rect.left) && event.getX(0) <= ((float) rect.right) && event.getY(0) >= ((float) rect.top) && event.getY(0) <= ((float) rect.bottom);
    }

    private boolean isInResize(MotionEvent event) {
        return event.getX(0) >= ((float) (this.dst_resize.left + -20)) && event.getX(0) <= ((float) (this.dst_resize.right + 20)) && event.getY(0) >= ((float) (this.dst_resize.top + -20)) && event.getY(0) <= ((float) (this.dst_resize.bottom + 20));
    }

    private void midPointToStartPoint(MotionEvent event) {
        float[] arrayOfFloat = new float[9];
        this.matrix.getValues(arrayOfFloat);
        this.mid.set((event.getX(0) + (((arrayOfFloat[0] * 0.0f) + (arrayOfFloat[1] * 0.0f)) + arrayOfFloat[2])) / 2.0f, (event.getY(0) + (((arrayOfFloat[3] * 0.0f) + (arrayOfFloat[4] * 0.0f)) + arrayOfFloat[5])) / 2.0f);
    }

    private void midDiagonalPoint(PointF paramPointF) {
        float[] arrayOfFloat = new float[9];
        this.matrix.getValues(arrayOfFloat);
        float f1 = (arrayOfFloat[0] * 0.0f) + (arrayOfFloat[1] * 0.0f) + arrayOfFloat[2];
        float f2 = (arrayOfFloat[3] * 0.0f) + (arrayOfFloat[4] * 0.0f) + arrayOfFloat[5];
        paramPointF.set((f1 + (((arrayOfFloat[0] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat[1] * ((float) this.mBitmap.getHeight()))) + arrayOfFloat[2])) / 2.0f, (f2 + (((arrayOfFloat[3] * ((float) this.mBitmap.getWidth())) + (arrayOfFloat[4] * ((float) this.mBitmap.getHeight()))) + arrayOfFloat[5])) / 2.0f);
    }

    private float rotationToStartPoint(MotionEvent event) {
        float[] arrayOfFloat = new float[9];
        this.matrix.getValues(arrayOfFloat);
        float x = (arrayOfFloat[0] * 0.0f) + (arrayOfFloat[1] * 0.0f) + arrayOfFloat[2];
        return (float) Math.toDegrees(Math.atan2((double) (event.getY(0) - (((arrayOfFloat[3] * 0.0f) + (arrayOfFloat[4] * 0.0f)) + arrayOfFloat[5])), (double) (event.getX(0) - x)));
    }

    private float diagonalLength(MotionEvent event) {
        return (float) Math.hypot((double) (event.getX(0) - this.mid.x), (double) (event.getY(0) - this.mid.y));
    }

    private float spacing(MotionEvent event) {
        if (event.getPointerCount() != 2) {
            return 0.0f;
        }
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt((double) ((x * x) + (y * y)));
    }

    public void setOperationListener(OperationListener operationListener2) {
        this.operationListener = operationListener2;
    }

    public void setInEdit(boolean isInEdit2) {
        this.isInEdit = isInEdit2;
        invalidate();
    }
}
