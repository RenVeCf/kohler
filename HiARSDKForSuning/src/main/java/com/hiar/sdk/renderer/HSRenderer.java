package com.hiar.sdk.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.hiar.sdk.GamePlay;
import com.hiar.sdk.HSARToolkit;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class HSRenderer implements Renderer {

    private final static String TAG = HSRenderer.class.getSimpleName();

    /*
     * ViewMatrix describe the pose of the camera ProjectionMatrix describe how
     * to project the gl space
     */
    public static float[] projectionMatrix = null;

    public static int screenWidth, screenHeight;
    public static boolean isGamePlayRunning = false;

    private boolean isShaderInitialed = false;

//	private CameraRenderer cameraRenderer;

    public HSRenderer(Context context) {
//		cameraRenderer = new CameraRenderer(context);
    }

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        unused.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        HSRenderer.screenWidth = width;
        HSRenderer.screenHeight = height;

//		cameraRenderer.onSurfaceChanged(width, height);
        // TODO: remove this hack
        GLES20.glDisable(GLES20.GL_CULL_FACE);
    }

    public void setPreviewFrameSize(int realWidth, int realHeight) {
    }

    @Override
    public void onDrawFrame(GL10 unused) {

        if (projectionMatrix != null && !isGamePlayRunning) {
            GamePlay.run();
            GamePlay.setGameDir(HSARToolkit.getInstance().mSuningState.getSceneDir());
            Log.i("rmy", "0000000000000000");
            GamePlay.setBackgroundMusic(HSARToolkit.getInstance().mSuningState.sceneAudioPath());
            Log.i("rmy", "1111111111111111");
            isGamePlayRunning = true;
        }

//        Log.e(TAG, "onDrawFrame() called with: unused = [" + unused + "]");

        GLES20.glClearColor(0, 0, 0, 0);
        GLES20.glDepthMask(true);
        GLES20.glColorMask(true, true, true, true);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        renderWidgets(HSARToolkit.getInstance(), unused);

        GLES20.glFinish();
    }

    boolean isGPInitialed = false;

    // 渲染所有需要渲染的Widget
    private void renderWidgets(HSARToolkit hsarToolkit, GL10 gl) {
        hsarToolkit.frameLock.lock();

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        if (isGamePlayRunning) {
            hsarToolkit.mState.renderWidgets(gl);
        }
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);

        hsarToolkit.frameLock.unlock();
    }

    private void checkGlError(String op) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("", "***** " + op + ": glError " + error, null);
            throw new RuntimeException(op + ": glError " + error);
        }
    }

}