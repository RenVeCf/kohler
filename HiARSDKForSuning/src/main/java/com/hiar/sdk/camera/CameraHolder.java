package com.hiar.sdk.camera;

import android.hardware.Camera.Parameters;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class CameraHolder {

    private static final String TAG = "CameraHolder";

    private android.hardware.Camera mCameraDevice;
    private final Handler mHandler;

    private long mKeepBeforeTime = 0; // Keep the Camera before this time.
    private int mUsers = 0; // number of open() - number of release()

    private Parameters mParameters;

    // Use a singleton.
    private static CameraHolder sHolder;

    public static synchronized CameraHolder instance() {
        if (sHolder == null) {
            sHolder = new CameraHolder();
        }
        return sHolder;
    }

    private static final int RELEASE_CAMERA = 1;

    private class MyHandler extends Handler {

        MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RELEASE_CAMERA:
                    releaseCamera();
                    break;
            }
        }
    }

    private CameraHolder() {
        HandlerThread ht = new HandlerThread("CameraHolder");
        ht.start();
        mHandler = new MyHandler(ht.getLooper());
    }

    public synchronized android.hardware.Camera open() {
        if (mUsers != 0)
            return null;
        if (mCameraDevice == null) {
            try {
                mCameraDevice = android.hardware.Camera.open();
            } catch (Exception e) {
                Log.e(TAG, "fail to connect Camera", e);
                return null;
            }
            mParameters = mCameraDevice.getParameters();
        } else {
            try {
                mCameraDevice.stopPreview();
                mCameraDevice.release();
                mCameraDevice = null;
                mCameraDevice = android.hardware.Camera.open();
            } catch (RuntimeException e) {
                Log.e(TAG, "reconnect failed.");
            }
            mCameraDevice.setParameters(mParameters);
        }
        ++mUsers;
        mHandler.removeMessages(RELEASE_CAMERA);
        mKeepBeforeTime = 0;
        return mCameraDevice;
    }

    /**
     * Tries to open the hardware camera. If the camera is being used or
     * unavailable then return {@code null}.
     */
    public synchronized android.hardware.Camera tryOpen() {
        return mUsers == 0 ? open() : null;
    }

    public synchronized void release() {
        --mUsers;
        mCameraDevice.stopPreview();
        releaseCamera();
    }

    private synchronized void releaseCamera() {
        if (!(mUsers == 0))
            return;
        if (mCameraDevice == null)
            return;
        long now = System.currentTimeMillis();
        if (now < mKeepBeforeTime) {
            mHandler.sendEmptyMessageDelayed(RELEASE_CAMERA, mKeepBeforeTime - now);
            return;
        }
        mCameraDevice.release();
        mCameraDevice = null;
    }

    public synchronized void keep() {
        if (!(mUsers == 1 || mUsers == 0))
            return;
        // Keep the camera instance for 3 seconds.
        mKeepBeforeTime = System.currentTimeMillis() + 3000;
    }

}
