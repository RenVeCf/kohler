package com.hiar.sdk.camera;

import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;

import com.hiar.sdk.utils.OpenglUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class CameraParameters {

    private static Method getSupportedPreviewFormats = null;
    private static Method getSupportedPreviewSizes = null;

    static {
        initCompatibility();
    }

    private static void initCompatibility() {
        // separate exception handling is needed, in case on method is
        // supported, the other no  t
        try {
            getSupportedPreviewSizes = Parameters.class.getMethod("getSupportedPreviewSizes", (Class[]) null);
            /* success, this is a newer device */
        } catch (NoSuchMethodException nsme) {
            /* failure, must be older device */
        }
        try {
            getSupportedPreviewFormats = Parameters.class.getMethod("getSupportedPreviewFormats", (Class[]) null);
            /* success, this is a newer device */
        } catch (NoSuchMethodException nsme) {
			/* failure, must be older device */
        }
    }

    @SuppressWarnings("unchecked")
    public static void setCameraParameters(Camera camera, int width, int height) {
        // reduce preview frame size for performance reasons
        if (getSupportedPreviewSizes != null) {
            Parameters params = camera.getParameters();
            // since SDK 5 web can query the available preview sizes
            // let's choose the smallest available preview size
            // params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            List<Size> sizes;
            try {
                Object supportedFormats = getSupportedPreviewSizes.invoke(params, (Object[]) null);
                if (supportedFormats instanceof List<?>) {
                    sizes = (List<Camera.Size>) supportedFormats;// params.getSupportedPreviewSizes();
                    Size optimalSize = OpenglUtil.getOptimalPreviewSize(sizes, 16, 9);
                    Size currentSize = params.getPreviewSize();
                    if (!(optimalSize.height == currentSize.height && optimalSize.width == currentSize.width)) {
                        // the optimal size was not set, yet. so let's do so now
                        Log.d("AndAR", "'query preview sizes' available, setting size to: " + width + " x " + height);
                        params.setPreviewSize(optimalSize.width, optimalSize.height);
                        try {
                            List<String> focusModes = params.getSupportedFocusModes();
                            if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
                                params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                            camera.setParameters(params);
                        } catch (RuntimeException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            // we don't have any information about available previewsizes...
            Parameters params = camera.getParameters();
            Size currentSize = params.getPreviewSize();
            if (!(160 == currentSize.height && 240 == currentSize.width)) {
                // try to set the preview size to this fixed value
                params.setPreviewSize(240, 160);
                try {
                    List<String> focusModes = params.getSupportedFocusModes();
                    if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
                        params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                    camera.setParameters(params);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }

        // now set the pixel format of the preview frames:
        if (getSupportedPreviewFormats != null) {
            Parameters params = camera.getParameters();
            // we may query the available pixelformats in newer SDk versions
            List<Integer> supportedFormats;
            try {
                supportedFormats = (List<Integer>) getSupportedPreviewFormats.invoke(params, (Object[]) null);
                if (supportedFormats != null) {
                    int format = CameraPreviewHandler.getBestSupportedFormat(supportedFormats);
                    if (format != -1) {
                        params.setPreviewFormat(format);
                        try {
                            List<String> focusModes = params.getSupportedFocusModes();
                            if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
                                params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                            camera.setParameters(params);
                        } catch (RuntimeException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                    }
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } else {
            Parameters params = camera.getParameters();
            if (params.getPreviewFormat() != ImageFormat.NV21) {
                // try to set the preview format, if it was not YCbCr_420
                // already
                params.setPreviewFormat(ImageFormat.NV21);
                try {
                    List<String> focusModes = params.getSupportedFocusModes();
                    if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
                        params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                    camera.setParameters(params);
                } catch (RuntimeException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
