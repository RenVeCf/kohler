package com.hiar.sdk;

import android.opengl.GLES20;
import android.opengl.Matrix;

import com.hiar.sdk.renderer.HSRenderer;
import com.hiar.sdk.utils.FileUtils;
import com.hiar.sdk.utils.OpenglUtil;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

//画矩形框
public class Border {
	private int mProgram;// 自定义渲染管线着色器程序id
	private int muMVPMatrixHandle;// 总变换矩阵引用
	private int maPositionHandle; // 顶点位置属性引用
	private String mVertexShader;// 顶点着色器代码脚本
	private String mFragmentShader;// 片元着色器代码脚本

	private FloatBuffer mVertexBuffer;// 顶点坐标数据缓冲
	private int vCount = 0;
	public static final float UNIT_SIZE = 0.5f;

	public Border() {
		// 初始化顶点坐标与着色数据
		initVertexData();
		// 初始化shader
		initShader();
	}

	// 初始化顶点坐标与着色数据的方法
	public void initVertexData() {
		// 顶点坐标数据的初始化================begin============================
		vCount = 4;

		float vertices[] = new float[] {
				// 上面
				-UNIT_SIZE, UNIT_SIZE, 0, -UNIT_SIZE, -UNIT_SIZE, 0, UNIT_SIZE, -UNIT_SIZE, 0, UNIT_SIZE, UNIT_SIZE,
				0, };

		// 创建顶点坐标数据缓冲
		// vertices.length*4是因为一个整数四个字节
		ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
		vbb.order(ByteOrder.nativeOrder());// 设置字节顺序
		mVertexBuffer = vbb.asFloatBuffer();// 转换为Float型缓冲
		mVertexBuffer.put(vertices);// 向缓冲区中放入顶点坐标数据
		mVertexBuffer.position(0);// 设置缓冲区起始位置

	}

	// 初始化shader
	public void initShader() {
		// 加载顶点着色器的脚本内容
		mVertexShader = FileUtils.loadFromAssetsFile("2DShaders" + File.separator + "BORDER_VERTEX.sh",
				HiARSDKForSuningActivity.context.getResources());
		// 加载片元着色器的脚本内容
		mFragmentShader = FileUtils.loadFromAssetsFile("2DShaders" + File.separator + "BORDER_FRAG.sh",
				HiARSDKForSuningActivity.context.getResources());
		// 基于顶点着色器与片元着色器创建程序
		mProgram = OpenglUtil.createProgramFromShaderSrc(mVertexShader, mFragmentShader);
		// 获取程序中顶点位置属性引用id
		maPositionHandle = GLES20.glGetAttribLocation(mProgram, "aPosition");
		// 获取程序中顶点颜色属性引用id
		// 获取程序中总变换矩阵引用id
		muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
	}

	public void drawSelf() {
		GLES20.glEnable(GLES20.GL_BLEND);
		GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
		GLES20.glUseProgram(mProgram);
		// 将最终变换矩阵传入shader程序
		GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, getFinalMatrix(), 0);

		// 为画笔指定顶点位置数据
		GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 3 * 4, mVertexBuffer);
		// 允许顶点位置数据数组
		GLES20.glEnableVertexAttribArray(maPositionHandle);
		GLES20.glLineWidth(3);
		// 绘制立方体
		GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, vCount);
		GLES20.glDisable(GLES20.GL_BLEND);
	}

	// 获取具体物体的总变换矩阵
	public float[] mMVPMatrix = new float[16];
	public float width = 1.0f;
	public float height = 1.0f;
	public float[] parentMVMatirx = new float[16];

	// add by mfy
	public float[] mPlaneX;
	public float[] mPlaneY;
	public float[] mPlaneZ;

	public int mPlaneXNum = 0;
	public int mPlaneYNum = 0;
	public int mPlaneZNum = 0;

	public void setParentMVMatirx(float[] matrix) {
		parentMVMatirx = matrix;
	}

	public void setWidth(float w) {
		width = w;
	}

	public void setHeight(float h) {
		height = h;
	}

	public float[] getFinalMatrix() {
		Matrix.setIdentityM(mMVPMatrix, 0);
		Matrix.scaleM(mMVPMatrix, 0, width, height, 1);// 设置框的大小等同图片大小
		Matrix.multiplyMM(mMVPMatrix, 0, parentMVMatirx, 0, mMVPMatrix, 0);
		Matrix.multiplyMM(mMVPMatrix, 0, HSRenderer.projectionMatrix, 0, mMVPMatrix, 0);
		return mMVPMatrix;
	}

}
