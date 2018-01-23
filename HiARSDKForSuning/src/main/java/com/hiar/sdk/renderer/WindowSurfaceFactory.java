package com.hiar.sdk.renderer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

import com.hiar.sdk.GamePlay;
import com.hiar.sdk.HiARSDKForSuningActivity;

import android.opengl.GLSurfaceView;

public class WindowSurfaceFactory implements GLSurfaceView.EGLWindowSurfaceFactory{
	
	@Override
	public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display,
			EGLConfig config, Object nativeWindow) {
		// TODO Auto-generated method stub
//		EGLSurface result = null;
//		int[] attrib_list = {EGL14.EGL_RENDER_BUFFER, EGL14.EGL_BACK_BUFFER, EGL14.EGL_NONE }; 
//		try {
//	        result = egl.eglCreateWindowSurface(display, config, nativeWindow, attrib_list);
//	        GamePlay.updateEGL(display, null, result, config);
//	    } catch (IllegalArgumentException e) {
//	    }
		return null;
	}

	@Override
	public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
		// TODO Auto-generated method stub
		egl.eglDestroySurface(display, surface);
	}

}
