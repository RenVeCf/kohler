package com.hiar.sdk.renderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

import com.hiar.sdk.GamePlay;
import com.hiar.sdk.HSARToolkit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.IntBuffer;

public class HSCameraRenderer implements Renderer {

	/*
	 * ViewMatrix describe the pose of the camera ProjectionMatrix describe how
	 * to project the gl space
	 */
	public static float[] projectionMatrix = null;

	public static int screenWidth, screenHeight;
	public static boolean isGamePlayRunning = false;
	
	private boolean isShaderInitialed = false;
	private boolean isShot = false;

	private CameraRenderer cameraRenderer;

	public HSCameraRenderer(Context context) {
		cameraRenderer = new CameraRenderer(context);
	}

	@Override
	public void onSurfaceCreated(GL10 unused, EGLConfig config) {
		unused.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
	}

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		HSRenderer.screenWidth = width;
		HSRenderer.screenHeight = height;

		cameraRenderer.onSurfaceChanged(width, height);
		// TODO: remove this hack
		GLES20.glDisable(GLES20.GL_CULL_FACE);

	}

	public void setPreviewFrameSize(int realWidth, int realHeight) {
		cameraRenderer.setPreviewFrameSize(realWidth, realHeight);
	}

	@Override
	public void onDrawFrame(GL10 unused) {
		if (false == isShaderInitialed) {
			cameraRenderer.initCameraRendering(unused);
			isShaderInitialed = true;
		}

		GLES20.glClearColor(0, 0, 0, 1);
		GLES20.glDepthMask(true);
		GLES20.glColorMask(true, true, true, true);
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		
		renderWidgets(HSARToolkit.getInstance(), unused);

		if(HSARToolkit.getInstance().mState.pose != null) {
			try {
				if(isShot == false && HSARToolkit.getInstance().mSuningState.getScene().getBoolean("recoImg")) {
					HSARToolkit.getInstance().mSuningState.recoImg =
							createBitmapFromGLSurface(0,0,HSRenderer.screenWidth,HSRenderer.screenHeight,unused);
					isShot = true;
				}
			} catch (Exception exception) {

			}
		}
		else {
			isShot = false;
		}
		
		GLES20.glFinish();
	}

	boolean isGPInitialed = false;
	
	// 渲染所有需要渲染的Widget
	private void renderWidgets(HSARToolkit hsarToolkit, GL10 gl) {
		hsarToolkit.frameLock.lock();

		if (hsarToolkit.mState.bIsReady) {
			cameraRenderer.onRender(gl, hsarToolkit);
		}

		hsarToolkit.frameLock.unlock();
	}
	
	private void checkGlError(String op) {  
	    int error;  
	    while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {  
	        Log.e("","***** " + op + ": glError " + error, null);  
	        throw new RuntimeException(op + ": glError " + error);  
	    }  
	}

	private Bitmap createBitmapFromGLSurface(int x, int y, int w, int h, GL10 gl)
			throws OutOfMemoryError {
		int bitmapBuffer[] = new int[w * h];
		int bitmapSource[] = new int[w * h];
		IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
		intBuffer.position(0);

		try {
			gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, intBuffer);
			int offset1, offset2;
			for (int i = 0; i < h; i++) {
				offset1 = i * w;
				offset2 = (h - i - 1) * w;
				for (int j = 0; j < w; j++) {
					int texturePixel = bitmapBuffer[offset1 + j];
					int blue = (texturePixel >> 16) & 0xff;
					int red = (texturePixel << 16) & 0x00ff0000;
					int pixel = (texturePixel & 0xff00ff00) | red | blue;
					bitmapSource[offset2 + j] = pixel;
				}
			}
		} catch (GLException e) {
			return null;
		}

		return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
	}
}
