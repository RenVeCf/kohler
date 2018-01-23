package com.hiar.sdk;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

import android.content.res.AssetManager;

public class GamePlay {
	
    static {
        System.loadLibrary("hiar");
    }
    
	static public native void initialGamePlay(AssetManager assetManager);
	static public native void setSurfaceSize(int width, int height);
	
	static public native void setProjectionMatrix(float[] projectionMatrix);
	static public native void run();
	static public native void frame(float[] pose);
	static public native void resume();
	static public native void pause();
	static public native void exit();
	
	// !!! set before game initialize
	static public native void setGameDir(String gameDir);
	static public native void setBackgroundMusic(String audioPath);
	static public native void addModel(String gpbPath, String animationPath, String bipPath, String nodeName);
	static public native void startModelMatrixAnimation(float[] startMatrix, float[] endMatrix, float startScale, float endScale, int frames);
	static public native void delModel(String nodeName);
	static public native void playClip(String clipName);
	static public native void stopClip(String clipName);
	
	static public native void addCatchModel(String gpbPath, String animationPath, String nodeName);
	static public native void startCatchModelMatrixAnimation(float[] startMatrix, float[] endMatrix, int frames);
	static public native void delCatchModel(String nodeName);
	static public native void playCatchClip(String clipName);
	static public native void stopCatchClip(String clipName);

	static public void stopAllClip() {
		stopClip("idle");
		stopClip("catching");
		stopClip("fail");
		stopClip("success");
	}

	static public void stopAllCatchClip() {

	}
	
}
