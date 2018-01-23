package com.hiar.sdk.renderer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import com.hiar.sdk.GamePlay;

import android.opengl.GLSurfaceView;
import android.util.Log;

public class ContextFactory implements GLSurfaceView.EGLContextFactory{
	
	private static void checkEglError(String prompt, EGL10 egl) { 
        int error; 
        while ((error = egl.eglGetError()) != EGL10.EGL_SUCCESS) { 
            Log.e("ContextFactory", String.format("%s: EGL error: 0x%x", prompt, error)); 
        } 
    }

	@Override
	public EGLContext createContext(EGL10 egl, EGLDisplay display,
			EGLConfig eglConfig) {
		// TODO Auto-generated method stub
//		checkEglError("Before eglCreateContext", egl); 
//        int[] attrib_list = {EGL14.EGL_CONTEXT_CLIENT_VERSION, 2, EGL14.EGL_NONE }; 
//        EGLContext context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list); 
//        checkEglError("After eglCreateContext", egl); 
//        GamePlay.updateEGL(display, context, null, eglConfig);
		return null;
	}

	@Override
	public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
		// TODO Auto-generated method stub
		egl.eglDestroyContext(display, context);
	}

}
