package com.hiar.sdk.camera;

import android.annotation.SuppressLint;
import android.graphics.ImageFormat;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.hiar.sdk.GamePlay;
import com.hiar.sdk.HSARToolkit;
import com.hiar.sdk.core.HiarqCameraCalib;
import com.hiar.sdk.core.HiarqImageSize;
import com.hiar.sdk.core.HiarqMarkerInfo;
import com.hiar.sdk.core.HiarqOptions;
import com.hiar.sdk.core.HiarqTargetInfo;
import com.hiar.sdk.core.HiarqVersion;
import com.hiar.sdk.core.NativeInterface;
import com.hiar.sdk.renderer.HSCameraRenderer;
import com.hiar.sdk.renderer.HSRenderer;
import com.hiar.sdk.utils.FilePath;
import com.hiar.sdk.utils.OpenglUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class CameraPreviewHandler implements PreviewCallback, AutoFocusCallback {

    private final static String TAG = CameraPreviewHandler.class.getSimpleName();

    private GLSurfaceView glSurfaceView; // Reference for GLSurfaceView of
    private GLSurfaceView cameraSurfaceView;
    private HSCameraRenderer hsarRenderer; // Reference for HSARRenderer of
    private HSARToolkit hsarToolkit; // Reference for HSARToolkit of

    private ProcessWorker processWorker = null; // Thead for processing camera
    // preview
    private AutoFocusHandler focusHandler = null; // Thead for auto focus
    private Camera cam = null; // Camera reference
    private CameraStatus camStatus;

    private int previewFrameWidth = 240;
    private int previewFrameHeight = 160;
    private TextView logText;

    /*
     * YUV420 format size
     */
    private int bwSize = (previewFrameWidth / 2) * (previewFrameHeight / 2);

    private Object processLock = new Object(); // Lock for processing camera
    // preview

    private boolean threadsRunning = true;

    public CameraPreviewHandler(GLSurfaceView glSurfaceView, GLSurfaceView cameraSurfaceView, HSCameraRenderer hsarRender, HSARToolkit hsarToolkit,
                                CameraStatus camStatus, TextView logView) {

        this.cameraSurfaceView = cameraSurfaceView;
        this.glSurfaceView = glSurfaceView;
        this.hsarRenderer = hsarRender;
        this.hsarToolkit = hsarToolkit;

        this.camStatus = camStatus;
        logText = logView;
    }

    /**
     * Returns the best pixel format of the list or -1 if none suites.
     *
     * @param listOfFormats
     * @return
     */
    public static int getBestSupportedFormat(List<Integer> listOfFormats) {

        int format = -1;
        for (Iterator<Integer> iterator = listOfFormats.iterator(); iterator.hasNext(); ) {
            Integer integer = iterator.next();

            if (integer.intValue() == ImageFormat.NV21) {
                format = ImageFormat.NV21;
                return format;
            }

        }
        return format;
    }

    public void init(Camera camera) {
        Parameters camParams = camera.getParameters();

        Size previewSize = camParams.getPreviewSize();
        previewFrameWidth = previewSize.width;
        previewFrameHeight = previewSize.height;

        bwSize = (previewFrameWidth / 2) * (previewFrameHeight / 2);
        if (null != hsarToolkit.mState.frameRenderBuffer) {
            hsarToolkit.mState.frameRenderBuffer.clear();
            hsarToolkit.mState.frameRenderBuffer = null;
        }
        hsarToolkit.mState.frameRenderBuffer = OpenglUtil.makeByteBuffer(bwSize * 6);

        hsarRenderer.setPreviewFrameSize(previewFrameWidth, previewFrameHeight);

        int pixelformat = camParams.getPreviewFormat();
        PixelFormat pixelinfo = new PixelFormat();
        PixelFormat.getPixelFormatInfo(pixelformat, pixelinfo);

        int bufSize = previewFrameWidth * previewFrameHeight * pixelinfo.bitsPerPixel / 8;

        for (int i = 0; i < 5; i++) {
            camera.addCallbackBuffer(new byte[bufSize]);
        }

        camStatus.previewing = true;

        initAlgorithm();
    }

    long hiar;
    long gallery;
//	HiarqLog loggerInitial = new HiarqLog("initialLog");
//	HiarqLog loggerRecog = null;

    // 算法初始化工作
    void initAlgorithm() {

        NativeInterface.loadNativeLibrary();
        try {
            int result = -1;
//			NativeInterface.hiarqRegisterLogCallback(loggerInitial);
            hiar = NativeInterface.hiarqCreate();

            HiarqOptions options = new HiarqOptions();
            options.trackingQuality = 5;
            options.recogQuality = 5;
            options.filterEnable = false;
            options.viewFinderEnable = true;
            options.viewFinderRect = new int[4];
            options.viewFinderRect[0] = 0;
            options.viewFinderRect[1] = 0;
            options.viewFinderRect[2] = previewFrameWidth;
            options.viewFinderRect[3] = previewFrameHeight;
            result = NativeInterface.hiarqSetOptions(hiar, options);

            gallery = NativeInterface.hiarqGetGallery(hiar);
            HiarqVersion[] version = new HiarqVersion[1];
            version[0] = new HiarqVersion();
            result = NativeInterface.hiarqGetAlgorithmVersion(version);
            HiarqCameraCalib cam = new HiarqCameraCalib();
            float[] mat = new float[9];
            cam.mat = mat;
            HiarqImageSize[] resolutions = new HiarqImageSize[1];
            resolutions[0] = new HiarqImageSize();
            resolutions[0].width = previewFrameWidth;
            resolutions[0].height = previewFrameHeight;
            Integer preferredResolutionIndex = Integer.valueOf(0);
            result = NativeInterface.hiarqGetPreferredCameraInfo(resolutions, preferredResolutionIndex, cam);
            result = NativeInterface.hiarqSetCameraInfo(hiar, resolutions[preferredResolutionIndex], cam);
            float[] proMatrix = new float[16];
            result = NativeInterface.hiarqGetGLProjectMatrix(cam, previewFrameWidth, previewFrameHeight, 10.0f, 5000.0f,
                    proMatrix);// 获取投影矩阵
            float screenRatio = glSurfaceView.getWidth() * 1.0f / glSurfaceView.getHeight();
            float bufferRatio = previewFrameHeight * 1.0f / previewFrameWidth;
            if (screenRatio > bufferRatio) {
                float bufferHeight = previewFrameWidth * glSurfaceView.getWidth() * 1.0f / previewFrameHeight;
                float scale = bufferHeight / glSurfaceView.getHeight() * 1.0f;
                proMatrix[0] *= scale;
                proMatrix[8] *= scale;
            } else if (screenRatio < bufferRatio) {
                float bufferWidth = previewFrameHeight * glSurfaceView.getHeight() * 1.0f / previewFrameWidth;
                float scale = bufferWidth / glSurfaceView.getWidth() * 1.0f;
                proMatrix[5] *= scale;
                proMatrix[9] *= scale;
            }
            HSRenderer.projectionMatrix = proMatrix;
            float[] rotateMatrix = new float[16];
            Matrix.setRotateM(rotateMatrix, 0, 270, 0, 0, 1);
            Matrix.multiplyMM(HSRenderer.projectionMatrix, 0, rotateMatrix, 0, HSRenderer.projectionMatrix, 0);

            GamePlay.setSurfaceSize(glSurfaceView.getWidth(), glSurfaceView.getHeight());
            GamePlay.setProjectionMatrix(HSRenderer.projectionMatrix);

            File f = new File(FilePath.getPublicKeyPath());
            File[] fileList = f.listFiles();
            HashSet<String> keys = HSARToolkit.getInstance().mSuningState.getKeySet();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isFile() && fileList[i].getName().endsWith(".db") &&
                        keys.contains(fileList[i].getName().split("\\.")[0])) {
                    result = NativeInterface.hiarqGetKeyVersion(FilePath.getPublicKeyPath() + fileList[i].getName(), version);
                    result = NativeInterface.hiarqAddMarker(gallery, fileList[i].getName(), FilePath.getPublicKeyPath() + fileList[i].getName());
                }
            }
            result = NativeInterface.hiarqRealizeGallery(gallery);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public enum TargetState {
        RECOGNIZED, TRACKED, NONE;
    }

    private TargetState state = TargetState.NONE;

    @Override
    public synchronized void onPreviewFrame(byte[] data, Camera camera) {

        if (data == null)
            return;
        if (cam == null)
            cam = camera;

        if (hsarToolkit.isUpdateFrame) {
            //刷新新的相机预览数据
            processWorker.nextFrame(data);
        }

        if (state == TargetState.NONE) {
            updataFrameRenderBuffer(data);
            cameraSurfaceView.requestRender();
            glSurfaceView.requestRender();
        }
        camera.addCallbackBuffer(data);
    }

    private void updataFrameRenderBuffer(byte[] data) {
        hsarToolkit.frameLock.lock();
        hsarToolkit.mState.frameRenderBuffer.position(0);
        if (data.length <= bwSize * 6) {
            hsarToolkit.mState.frameRenderBuffer.put(data);
        }
        hsarToolkit.mState.frameRenderBuffer.position(0);
        hsarToolkit.frameLock.unlock();
        hsarToolkit.mState.bIsReady = true;
    }

    boolean hasProjectMatrix = false;

    class ProcessWorker extends Thread {
        private byte[] curFrame;
        private boolean newFrame = false;

        public ProcessWorker() {
            setDaemon(true);
            start();
        }

        public void free() {
            curFrame = null;
        }

        private final List<Float> q = new ArrayList<Float>();
        private float tot = 0;
        private int MAXNUM = 100;

        public void insertTrackingTime(float t) {
            if (q.size() >= MAXNUM) {
                float ele = q.remove(q.size() - 1);
                tot -= ele;
            }

            q.add(0, t);
            tot += t;
        }

        public float getAverTrackingTime() {
            if (q.size() > 0) {
                return tot / q.size();
            }

            return 0;
        }

        public void clearTrackingTime() {
            q.clear();
            tot = 0;
        }

        public synchronized void run() {
            setName("ProcessWorker");
            HiarqTargetInfo[] target = new HiarqTargetInfo[1];
            target[0] = new HiarqTargetInfo();
            float[] pose = new float[12];
            target[0].pose = pose;
            HiarqMarkerInfo[] markInfo = new HiarqMarkerInfo[1];
            markInfo[0] = new HiarqMarkerInfo();
            while (threadsRunning) {

                while (!newFrame) {
                    try {
                        wait();// wait for next frame
                    } catch (InterruptedException e) {
                    }
                }
                newFrame = false;

                synchronized (processLock) {
                    try {
                        int result = -1;
                        if (state == TargetState.RECOGNIZED || state == TargetState.TRACKED) {
                            //已识别到的情况下进行跟踪
                            Log.e(TAG, "跟踪");
                            long pre = System.nanoTime();
                            result = NativeInterface.hiarqTrack(hiar, curFrame, target);// 跟踪
                            mHandler.sendEmptyMessage(Tracked);
                            long t = (System.nanoTime() - pre);
                            insertTrackingTime(t);
                            TrackTime = getAverTrackingTime();
                        } else {
                            //未识别到的情况下对相机数据进行识别
                            Log.e(TAG, "识别");
                            long preReco = System.nanoTime();
                            result = NativeInterface.hiarqRecognize(hiar, curFrame, target);// 识别
                            clearTrackingTime();
                            RecoTime = (System.nanoTime() - preReco);

                            // non-sense: just want to show that we support setting callbacks from different threads. compare with loggerInitial.
//							if( loggerRecog==null )
//							{
//								loggerRecog = new HiarqLog("recogLog");
//								NativeInterface.hiarqRegisterLogCallback(loggerRecog);
//							}
                        }

                        Log.e(TAG, "result: " + result);

                        if (result == 1) {
                            state = TargetState.values()[target[0].state];
                        } else {
                            state = TargetState.NONE;
                        }
                        int width = 0, height = 0;
                        if (state == TargetState.TRACKED) {
                            NativeInterface.hiarqGetMarkerInfo(gallery, target[0].markerIndex, markInfo);
                            width = markInfo[0].width;
                            height = markInfo[0].height;
                        } else if (state == TargetState.NONE) {
                            mHandler.sendEmptyMessage(Lost);
                        }
                        HSARToolkit.getInstance().mState.updateStateWithTrackableResult(width, height, target[0].pose, markInfo[0].name, state);
                        if (state == TargetState.TRACKED) {
                            updataFrameRenderBuffer(curFrame);
                            cameraSurfaceView.requestRender();
                            glSurfaceView.requestRender();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    yield();
                }
            }
        }

        void nextFrame(byte[] frame) {

            if (this.getState() == Thread.State.WAITING) {

                if (!hsarToolkit.isUpdateFrame)
                    return;

                // ok, we are ready for a new frame:
                curFrame = frame.clone();
                newFrame = true;
                // do the work:
                synchronized (this) {
                    this.notify();
                }
            } else {
                // ignore it
            }
        }
    }

    long RecoTime = 0;
    float TrackTime = 0;
    static int Tracked = 1;
    static int Lost = 2;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == Tracked) {
                String track = String.format("%5.1f ms", TrackTime / 1000000.0f);
                String reco = String.format("%5.1f ms", RecoTime / 1000000.0f);
                logText.setText("RecogTime: " + reco + "\n" + "TrackTime: " + track);
            } else if (msg.what == Lost) {
                logText.setText("");
            }
        }

    };

    public void startThreads() {
        threadsRunning = true;
        if (focusHandler == null) {
            focusHandler = new AutoFocusHandler();
            focusHandler.start();
        }
        if (processWorker == null) {
            this.processWorker = new ProcessWorker();
        }
    }

    public void pauseThreads() {
        threadsRunning = false;
    }

    public void stopThreads() {
        threadsRunning = false;

        if (focusHandler != null) {
            try {
                focusHandler.join();
            } catch (Exception exception) {

            }
        }
        if (processWorker != null) {
            try {
                processWorker.join(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
                processWorker.interrupt();
            }
            processWorker.free();
            processWorker = null;
        }

        cam = null;
    }

    class AutoFocusHandler extends Thread {

        private long lastScan;
        private static final int ENSURE_TIME = 3000;
        private static final int WAIT_TIME = 500;
        private boolean isFirstTime = true;
        private boolean visible = false;

        public AutoFocusHandler() {
        }

        @Override
        public synchronized void run() {
            super.run();
            setName("Autofocus handler");

            if (camStatus.previewing) {
                lastScan = System.currentTimeMillis();
            }
            while (threadsRunning) {
                try {
                    if (isFirstTime) {
                        wait(2000);
                        isFirstTime = false;
                    } else {
                        wait(WAIT_TIME);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                long currTime = System.currentTimeMillis();

                if (!visible && (currTime - lastScan) > ENSURE_TIME) {
                    if (camStatus.previewing && threadsRunning) {
                        performAutoFocus();
                        lastScan = currTime;
                    }
                }

                yield();
            }
        }

    }

    public void performAutoFocus() {
        if (cam != null) {
            cam.autoFocus(this);
        }
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera) {

        if (!success && cam != null) {
            Parameters camParams = cam.getParameters();
            List<String> focusModes = camParams.getSupportedFocusModes();
            if (focusModes.contains(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO))
                camParams.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
            cam.setParameters(camParams);
        }

    }

    public void UnrealizeGallery() {
        NativeInterface.hiarqUnrealizeGallery(gallery);
        NativeInterface.hiarqRemoveAllMarkers(gallery);
        NativeInterface.hiarqDestroy(hiar);
    }
}
