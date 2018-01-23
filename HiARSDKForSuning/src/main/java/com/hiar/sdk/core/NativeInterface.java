package com.hiar.sdk.core;

public class NativeInterface {

    public static boolean loadNativeLibrary() {

        try {
            System.loadLibrary("HiarQ");
            System.loadLibrary("HiarQ_jni");
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public static native int hiarqGetAlgorithmVersion(HiarqVersion[] version);

    public static native int hiarqGetKeyVersion(String keyFileName, HiarqVersion[] version);

    public static native int hiarqGetPreferredCameraInfo(HiarqImageSize[] resolutions, Integer preferredResolutionIndex, HiarqCameraCalib calib);

    public static native int hiarqGetGLProjectMatrix(HiarqCameraCalib calib, int width, int height, float nearPlane,
                                                     float farPlane, float[] projectMatrix);

    public static native int hiarqGetGLPose(float[] pose, float[] glPose);

    public static native int hiarqRegisterLogCallback(HiarqLog logger);

    public static native int hiarqCreate();

    public static native int hiarqDestroy(long hiar);

    public static native int hiarqSetOptions(long hiar, HiarqOptions options);

    public static native int hiarqSetCameraInfo(long hiar, HiarqImageSize resolution, HiarqCameraCalib calib);

    public static native int hiarqGetGallery(long hiar);

    public static native int hiarqIsGalleryRealized(int gallery, int realized);

    public static native int hiarqGetMarkerCount(int gallery);

    public static native int hiarqGetMarkerInfo(long gallery, int markerIndex, HiarqMarkerInfo[] markerInfo);

    public static native int hiarqAddMarker(long gallery, String markName, String keyFileName);

    public static native int hiarqRemoveMarker(long gallery, int markerIndex);

    public static native int hiarqRemoveAllMarkers(long gallery);

    public static native int hiarqRealizeGallery(long gallery);

    public static native int hiarqUnrealizeGallery(long gallery);

    public static native int hiarqRecognize(long hiar, byte[] image, HiarqTargetInfo[] target);

    public static native int hiarqTrack(long hiar, byte[] image, HiarqTargetInfo[] target);

}
