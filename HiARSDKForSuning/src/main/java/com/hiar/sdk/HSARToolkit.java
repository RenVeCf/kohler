
package com.hiar.sdk;

import java.util.concurrent.locks.ReentrantLock;

import com.hiar.sdk.renderer.HSRenderer;

import android.opengl.GLSurfaceView;

public class HSARToolkit {

	public boolean isUpdateFrame = true;

	public ReentrantLock frameLock = new ReentrantLock();

	public State mState = new State();

	public HiARSDKForSuningState mSuningState;
	public HiARSDKForSuningActivity mSuningActivity;
	public HSRenderer hsarRenderer;
	public GLSurfaceView glSurfaceView;

	private static HSARToolkit instance = null;

	public static synchronized HSARToolkit getInstance() {
		if (instance == null) {
			instance = new HSARToolkit();
		}
		return instance;
	}

	public HSARToolkit() {
	}

	public void startFrame() {
		isUpdateFrame = true;
	}

	public void stopFrame() {
		isUpdateFrame = false;
	}

	public boolean isUpdateFrame() {
		return isUpdateFrame;
	}

}
