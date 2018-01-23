package com.hiar.sdk.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

import com.hiar.sdk.HSARToolkit;
import com.hiar.sdk.utils.FileUtils;
import com.hiar.sdk.utils.OpenglUtil;

import java.io.File;
import java.nio.Buffer;

import javax.microedition.khronos.opengles.GL10;

public class CameraRenderer {

	/*
	 * camera parameters
	 */
	private static int screenWidth = 0;
	private static int screenHeight = 0;
	private int previewFrameWidth = 0;
	private int previewFrameHeight = 0;

	private Context mContext;

	/*
	 * camera shader handles
	 */
	private int cameraShaderID = 0;
	private int cameraVertexHandle = 0;
	private int cameraTexCoordHandle = 0;
	private int cameraYUniformHandle = 0;
	private int cameraUVUniformHandle = 0;
	private int cameraMVPMatrixHandle = 0;

	/*
	 * Video background texture ids
	 */
	private int cameraTextureYID;
	private int cameraTextureUVID;

	private boolean isTextureInitialized = false;
	private boolean isTextureCreated = false;

	private final int NUM_QUAD_INDEX = 6;

	private float quadVerticesArray[] = { -1.0f, -1.0f, 0.0f, 1.0f, -1.0f, 0.0f, 1.0f, 1.0f, 0.0f, -1.0f, 1.0f, 0.0f };

	private float quadTexCoordsArray[] = { 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f };

	private short quadIndicesArray[] = { 0, 1, 2, 2, 3, 0 };

	private Buffer quadVertices, quadTexCoords, quadIndices;

	public CameraRenderer(Context context) {
		mContext = context;
	}

	// Called when the surface changed size.
	public void onSurfaceChanged(int width, int height) {
		if (!isTextureCreated) {
			isTextureCreated = true;
		}

		screenWidth = width;
		screenHeight = height;

		surfaceChanged = true;
		if (previewSizeInit) {
			calculateTexCoords();
		}
	}

	private void calculateTexCoords() {
		/*
		 * Since the ratio of screen and preview is different, we should clip
		 * some part of the preview in order to get right
		 */
		float screenRatio = screenWidth * 1.0f / screenHeight;
		float bufferRatio;
		float scale;
		bufferRatio = previewFrameHeight * 1.0f / previewFrameWidth;
		if (screenRatio > bufferRatio) {
			float bufferHeight = previewFrameWidth * screenWidth * 1.0f / previewFrameHeight;
			scale = (1f - screenHeight * 1.0f / bufferHeight) / 2.0f;
			quadTexCoordsArray[0] = scale;
			quadTexCoordsArray[2] = 1f - scale;
			quadTexCoordsArray[4] = 1f - scale;
			quadTexCoordsArray[6] = scale;
		} else if (screenRatio < bufferRatio) {
			float bufferWidth = previewFrameHeight * screenHeight * 1.0f / previewFrameWidth;
			scale = (1f - screenWidth * 1.0f / bufferWidth) / 2.0f;
			quadTexCoordsArray[1] = 1f - scale;
			quadTexCoordsArray[3] = 1f - scale;
			quadTexCoordsArray[5] = scale;
			quadTexCoordsArray[7] = scale;
		}
		quadTexCoords = OpenglUtil.makeDoubleBuffer(quadTexCoordsArray);
	}

	private void initializeTexture(GL10 gl, HSARToolkit hsarToolkit) {
		
		int[] textureNames = new int[2];

		GLES20.glGenTextures(1, textureNames, 0);
		cameraTextureYID = textureNames[0];

		GLES20.glGenTextures(1, textureNames, 1);
		cameraTextureUVID = textureNames[1];
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, cameraTextureYID);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

		hsarToolkit.mState.frameRenderBuffer.position(0);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, previewFrameWidth, previewFrameHeight, 0,
				GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, hsarToolkit.mState.frameRenderBuffer);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, cameraTextureUVID);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

		hsarToolkit.mState.frameRenderBuffer.position(4 * (previewFrameWidth / 2) * (previewFrameHeight / 2));
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE_ALPHA, previewFrameWidth / 2,
				previewFrameHeight / 2, 0, GLES20.GL_LUMINANCE_ALPHA, GLES20.GL_UNSIGNED_BYTE,
				hsarToolkit.mState.frameRenderBuffer);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		isTextureInitialized = true;
		// GLES20.glDisable(GLES20.GL_TEXTURE_2D);
	}

	void initCameraRendering(GL10 gl) {
		// GLES20.glEnable(GLES20.GL_TEXTURE_2D);

		// GLES20.glDisable(GLES20.GL_TEXTURE_2D);

		/*
		 * Initial shader program for video background
		 */

		String SCANE_VERTEX_SHADER = FileUtils
				.loadFromAssetsFile("2DShaders" + File.separator + "SCANE_VERTEX_SHADER.sh", mContext.getResources());
		String SCANE_FRAGMENT_SHADER = FileUtils
				.loadFromAssetsFile("2DShaders" + File.separator + "SCANE_FRAGMENT_SHADER.sh", mContext.getResources());

		cameraShaderID = OpenglUtil.createProgramFromShaderSrc(SCANE_VERTEX_SHADER, SCANE_FRAGMENT_SHADER);
		cameraVertexHandle = GLES20.glGetAttribLocation(cameraShaderID, "vertexPosition");
		cameraTexCoordHandle = GLES20.glGetAttribLocation(cameraShaderID, "vertexTexCoord");
		cameraYUniformHandle = GLES20.glGetUniformLocation(cameraShaderID, "videoFrameY");
		cameraUVUniformHandle = GLES20.glGetUniformLocation(cameraShaderID, "videoFrameUV");
		cameraMVPMatrixHandle = GLES20.glGetUniformLocation(cameraShaderID, "modelViewProjectionMatrix");

		/*
		 * Log to ensure handles are correct
		 */
		/*
		 * videoBackgroundTexCoordHandle + "\n" +"videoFrameY: " +
		 * videoBackgroundYUniformHandle + "\n" +"videoFrameUV: " +
		 * videoBackgroundUVUniformHandle + "\n" );
		 */

		quadVertices = OpenglUtil.makeDoubleBuffer(quadVerticesArray);
		quadIndices = OpenglUtil.makeByteBuffer(quadIndicesArray);

		isTextureInitialized = false;
	}

	/*
	 * 
	 * To seperate video background drawing.
	 */
	public final void onRender(GL10 gl, HSARToolkit hsarToolkit) {
		// System.out.println("0123456 " + previewFrameWidth + " " +
		// previewFrameHeight);

		if (!isTextureInitialized) {
			initializeTexture(gl, hsarToolkit);
			return;
		}

		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, cameraTextureYID);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

		hsarToolkit.mState.frameRenderBuffer.position(0);
		GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, previewFrameWidth, previewFrameHeight,
				GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, hsarToolkit.mState.frameRenderBuffer);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		
		
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, cameraTextureUVID);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

		hsarToolkit.mState.frameRenderBuffer.position(4 * (previewFrameWidth / 2) * (previewFrameHeight / 2));
		GLES20.glTexSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, previewFrameWidth / 2, previewFrameHeight / 2,
				GLES20.GL_LUMINANCE_ALPHA, GLES20.GL_UNSIGNED_BYTE, hsarToolkit.mState.frameRenderBuffer);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

		float[] rotationMatrix = new float[16];
		Matrix.setIdentityM(rotationMatrix, 0);
		Matrix.setRotateM(rotationMatrix, 0, 270, 0, 0, 1);

		GLES20.glDepthFunc(GLES20.GL_LEQUAL);

		GLES20.glUseProgram(cameraShaderID);
		
		GLES20.glEnableVertexAttribArray(cameraVertexHandle);
		GLES20.glEnableVertexAttribArray(cameraTexCoordHandle);

		GLES20.glVertexAttribPointer(cameraVertexHandle, 3, GLES20.GL_FLOAT, false, 0, quadVertices);
		GLES20.glVertexAttribPointer(cameraTexCoordHandle, 2, GLES20.GL_FLOAT, false, 0, quadTexCoords);

		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, cameraTextureYID);
		GLES20.glUniform1i(cameraYUniformHandle, 0);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, cameraTextureUVID);
		GLES20.glUniform1i(cameraUVUniformHandle, 1);
		
		GLES20.glUniformMatrix4fv(cameraMVPMatrixHandle, 1, false, rotationMatrix, 0);

		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
		// TODO: glDrawElements fail with vbo ?
//		GLES20.glDrawElements(GLES20.GL_TRIANGLES, NUM_QUAD_INDEX, GLES20.GL_UNSIGNED_SHORT, quadIndices);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		
		GLES20.glDisable(GLES20.GL_BLEND);

		GLES20.glDisableVertexAttribArray(cameraVertexHandle);
		GLES20.glDisableVertexAttribArray(cameraTexCoordHandle);

		// GLES20.glDisable(GLES20.GL_TEXTURE_2D);

//		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
		GLES20.glUseProgram(0);
	}

	boolean surfaceChanged = false;
	boolean previewSizeInit = false;

	public void setPreviewFrameSize(int realWidth, int realHeight) {
		this.previewFrameWidth = realWidth;
		this.previewFrameHeight = realHeight;
		previewSizeInit = true;
		if (surfaceChanged)
			calculateTexCoords();
	}
	
	private void checkGlError(String op) {  
	    int error;  
	    while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {  
	        Log.e("","***** " + op + ": glError " + error, null);  
	        throw new RuntimeException(op + ": glError " + error);  
	    }  
	}  
}
