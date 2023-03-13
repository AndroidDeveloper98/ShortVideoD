package Jni;

public class ColorUtils {
    static {
        System.loadLibrary("colorutils");
    }

    public static native byte[] rgb2yuvfloat(byte[] bArr, int i, int i2, int i3);
}
