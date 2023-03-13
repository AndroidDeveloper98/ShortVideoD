package com.example.shortvideod.workers;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.Size;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;
import com.daasuu.mp4compose.VideoFormatMimeType;
import com.daasuu.mp4compose.composer.Mp4Composer;
import com.daasuu.mp4compose.filter.GlBilateralFilter;
import com.daasuu.mp4compose.filter.GlBrightnessFilter;
import com.daasuu.mp4compose.filter.GlCGAColorspaceFilter;
import com.daasuu.mp4compose.filter.GlContrastFilter;
import com.daasuu.mp4compose.filter.GlCrosshatchFilter;
import com.daasuu.mp4compose.filter.GlFilter;
import com.daasuu.mp4compose.filter.GlFilterGroup;
import com.daasuu.mp4compose.filter.GlGammaFilter;
import com.daasuu.mp4compose.filter.GlGaussianBlurFilter;
import com.daasuu.mp4compose.filter.GlGrayScaleFilter;
import com.daasuu.mp4compose.filter.GlHalftoneFilter;
import com.daasuu.mp4compose.filter.GlHazeFilter;
import com.daasuu.mp4compose.filter.GlHighlightShadowFilter;
import com.daasuu.mp4compose.filter.GlHueFilter;
import com.daasuu.mp4compose.filter.GlInvertFilter;
import com.daasuu.mp4compose.filter.GlLookUpTableFilter;
import com.daasuu.mp4compose.filter.GlLuminanceFilter;
import com.daasuu.mp4compose.filter.GlLuminanceThresholdFilter;
import com.daasuu.mp4compose.filter.GlMonochromeFilter;
import com.daasuu.mp4compose.filter.GlOpacityFilter;
import com.daasuu.mp4compose.filter.GlPixelationFilter;
import com.daasuu.mp4compose.filter.GlPosterizeFilter;
import com.daasuu.mp4compose.filter.GlRGBFilter;
import com.daasuu.mp4compose.filter.GlSaturationFilter;
import com.daasuu.mp4compose.filter.GlSepiaFilter;
import com.daasuu.mp4compose.filter.GlSharpenFilter;
import com.daasuu.mp4compose.filter.GlSolarizeFilter;
import com.daasuu.mp4compose.filter.GlToneCurveFilter;
import com.daasuu.mp4compose.filter.GlVibranceFilter;
import com.daasuu.mp4compose.filter.GlVignetteFilter;
import com.example.shortvideod.R;
import com.example.shortvideod.effect.FilterType;
import com.example.shortvideod.util.Const;
import com.example.shortvideod.util.VideoUtil;
import com.google.common.util.concurrent.ListenableFuture;
import java.io.File;
import java.io.IOException;

public class VideoFilterWorker extends ListenableWorker {
    public static final String KEY_FILTER = "filter";
    public static final String KEY_INPUT = "input";
    public static final String KEY_OUTPUT = "output";
    private static final String TAG = "VideoFilterWorker";
    Context context;

    public VideoFilterWorker(Context context2, WorkerParameters params) {
        super(context2, params);
        this.context = context2;
    }

    public ListenableFuture<ListenableWorker.Result> startWork() {
        //return CallbackToFutureAdapter.getFuture(new VideoFilterWorker$$ExternalSyntheticLambda0(this, new File(getInputData().getString("input")), new File(getInputData().getString("output"))));
        return CallbackToFutureAdapter.getFuture(completer -> {
            doActualWork(new File(getInputData().getString("input")), new File(getInputData().getString("output")), completer);
            return null;
        });
    }

    /* renamed from: lambda$startWork$0$com-example-shortvideod-workers-VideoFilterWorker */
    public /* synthetic */ Object mo650xc7b85060(File input, File output, CallbackToFutureAdapter.Completer completer) throws Exception {
        doActualWork(input, output, completer);
        return null;
    }

    private void doActualWork(File input, final File output, final CallbackToFutureAdapter.Completer<ListenableWorker.Result> completer) {
        Size size = VideoUtil.getDimensions(input.getAbsolutePath());
        int width = size.getWidth();
        int height = size.getHeight();
        if (width > 960 || height > 960) {
            if (width > height) {
                height = (height * Const.MAX_RESOLUTION) / width;
                width = Const.MAX_RESOLUTION;
            } else {
                width = (width * Const.MAX_RESOLUTION) / height;
                height = Const.MAX_RESOLUTION;
            }
        }
        if (width % 2 != 0) {
            width++;
        }
        if (height % 2 != 0) {
            height++;
        }
        Log.v(TAG, "Original: " + width + "x" + height + "px; scaled: " + width + "x" + height + "px");
        Mp4Composer composer = new Mp4Composer(input.getAbsolutePath(), output.getAbsolutePath());
        composer.videoBitrate((int) (((double) width) * 2.1d * ((double) height)));
        composer.size(width, height);
        FilterType filter = FilterType.valueOf(getInputData().getString("filter"));
        StringBuilder sb = new StringBuilder();
        sb.append("doActualWork: filter ");
        sb.append(filter.name());
        Log.d(TAG, sb.toString());
        switch (C00312.$SwitchMap$com$example$shortvideod$effect$FilterType[filter.ordinal()]) {
            case 1:
                composer.filter(new GlFilter());
                break;
            case 2:
                Log.d(TAG, "doActualWork: blur");
                composer.filter(new GlBilateralFilter());
                break;
            case 3:
                GlBrightnessFilter glBrightnessFilter = new GlBrightnessFilter();
                glBrightnessFilter.setBrightness(0.2f);
                composer.filter(glBrightnessFilter);
                break;
            case 4:
                composer.filter(new GlCGAColorspaceFilter());
                break;
            case 5:
                GlContrastFilter glContrastFilter = new GlContrastFilter();
                glContrastFilter.setContrast(2.5f);
                composer.filter(glContrastFilter);
                break;
            case 6:
                composer.filter(new GlCrosshatchFilter());
                break;
            case 7:
                composer.filter(new GlFilterGroup(new GlSepiaFilter(), new GlVignetteFilter()));
                break;
            case 8:
                GlGammaFilter glGammaFilter = new GlGammaFilter();
                glGammaFilter.setGamma(2.0f);
                composer.filter(glGammaFilter);
                break;
            case 9:
                composer.filter(new GlGaussianBlurFilter());
                break;
            case 10:
                composer.filter(new GlGrayScaleFilter());
                break;
            case 11:
                composer.filter(new GlHalftoneFilter());
                break;
            case 12:
                GlHazeFilter glHazeFilter = new GlHazeFilter();
                glHazeFilter.setSlope(-0.5f);
                composer.filter(glHazeFilter);
                break;
            case 13:
                composer.filter(new GlHighlightShadowFilter());
                break;
            case 14:
                composer.filter(new GlHueFilter());
                break;
            case 15:
                composer.filter(new GlInvertFilter());
                break;
            case 16:
                composer.filter(new GlLookUpTableFilter(BitmapFactory.decodeResource(this.context.getResources(), R.drawable.lookup_sample)));
                break;
            case 17:
                composer.filter(new GlLuminanceFilter());
                break;
            case 18:
                composer.filter(new GlLuminanceThresholdFilter());
                break;
            case 19:
                composer.filter(new GlMonochromeFilter());
                break;
            case 20:
                composer.filter(new GlOpacityFilter());
                break;
            case 21:
                composer.filter(new GlPixelationFilter());
                break;
            case 22:
                composer.filter(new GlPosterizeFilter());
                break;
            case 23:
                GlRGBFilter glRGBFilter = new GlRGBFilter();
                glRGBFilter.setRed(0.0f);
                composer.filter(glRGBFilter);
                break;
            case 24:
                composer.filter(new GlSaturationFilter());
                break;
            case 25:
                composer.filter(new GlSepiaFilter());
                break;
            case 26:
                GlSharpenFilter glSharpenFilter = new GlSharpenFilter();
                glSharpenFilter.setSharpness(4.0f);
                composer.filter(glSharpenFilter);
                break;
            case 27:
                composer.filter(new GlSolarizeFilter());
                break;
            case 28:
                try {
                    composer.filter(new GlToneCurveFilter(this.context.getAssets().open("acv/tone_cuver_sample.acv")));
                } catch (IOException e) {
                    Log.e("FilterType", "Error");
                }
                composer.filter(new GlFilter());
                break;
            case 29:
                GlVibranceFilter glVibranceFilter = new GlVibranceFilter();
                glVibranceFilter.setVibrance(3.0f);
                composer.filter(glVibranceFilter);
                break;
            case 30:
                composer.filter(new GlVignetteFilter());
                break;
            default:
                composer.filter(new GlFilter());
                break;
        }
        composer.listener(new Mp4Composer.Listener() {
            public void onProgress(double progress) {
            }

            public void onCompleted() {
                Log.d(VideoFilterWorker.TAG, "MP4 composition has finished.");
                completer.set(ListenableWorker.Result.success());
            }

            public void onCanceled() {
                Log.d(VideoFilterWorker.TAG, "MP4 composition was cancelled.");
                completer.setCancelled();
                if (!output.delete()) {
                    Log.w(VideoFilterWorker.TAG, "Could not delete failed output file: " + output);
                }
            }

            public void onFailed(Exception e) {
                Log.d(VideoFilterWorker.TAG, "MP4 composition failed with error.", e);
                completer.setException(e);
                if (!output.delete()) {
                    Log.w(VideoFilterWorker.TAG, "Could not delete failed output file: " + output);
                }
            }
        });
        composer.videoFormatMimeType(VideoFormatMimeType.AVC);
        composer.start();
    }

    /* renamed from: com.example.shortvideod.workers.VideoFilterWorker$2 */
    static /* synthetic */ class C00312 {
        static final /* synthetic */ int[] $SwitchMap$com$example$shortvideod$effect$FilterType;

        static {
            int[] iArr = new int[FilterType.values().length];
            $SwitchMap$com$example$shortvideod$effect$FilterType = iArr;
            try {
                iArr[FilterType.DEFAULT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.BILATERAL_BLUR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.BRIGHTNESS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.CGA_COLORSPACE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.CONTRAST.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.CROSSHATCH.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.FILTER_GROUP_SAMPLE.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.GAMMA.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.GAUSSIAN_FILTER.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.GRAY_SCALE.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.HALFTONE.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.HAZE.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.HIGHLIGHT_SHADOW.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.HUE.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.INVERT.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.LOOK_UP_TABLE_SAMPLE.ordinal()] = 16;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.LUMINANCE.ordinal()] = 17;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.LUMINANCE_THRESHOLD.ordinal()] = 18;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.MONOCHROME.ordinal()] = 19;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.OPACITY.ordinal()] = 20;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.PIXELATION.ordinal()] = 21;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.POSTERIZE.ordinal()] = 22;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.RGB.ordinal()] = 23;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.SATURATION.ordinal()] = 24;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.SEPIA.ordinal()] = 25;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.SHARP.ordinal()] = 26;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.SOLARIZE.ordinal()] = 27;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.TONE_CURVE_SAMPLE.ordinal()] = 28;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.VIBRANCE.ordinal()] = 29;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$com$example$shortvideod$effect$FilterType[FilterType.VIGNETTE.ordinal()] = 30;
            } catch (NoSuchFieldError e30) {
            }
        }
    }
}
