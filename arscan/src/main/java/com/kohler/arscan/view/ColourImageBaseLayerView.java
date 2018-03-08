package com.kohler.arscan.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.kohler.arscan.R;
import com.kohler.arscan.ShowActivity;
import com.kohler.arscan.util.LogManager;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Handler;

/**
 * Created by chenchen on 18-2-24.
 */

public class ColourImageBaseLayerView extends android.support.v7.widget.AppCompatImageView implements View.OnTouchListener,
        ViewTreeObserver.OnGlobalLayoutListener {

    private final static String TAG = ColourImageBaseLayerView.class.getSimpleName();

    private LayerDrawable mDrawables;

    private boolean mOnce;
    /**
     * 初始化时缩放的值
     */
    private float mInitScale;

    /**
     * 双击放大值到达的值
     */
    private float mMidScale;

    /**
     * 放大的最大值
     */
    private float mMaxScale;

    private Matrix mScaleMatrix;

    // *********单击放大*********
    private GestureDetector mGestureDetector;

    private boolean isAutoScale;

    public ColourImageBaseLayerView(Context context) {
        this(context, null);
    }

    public ColourImageBaseLayerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColourImageBaseLayerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init(context);
    }

    private int selected;
    private float downX;
    private float downY;

    private void init(Context context) {
        //获取layer-drawable
        mDrawables = (LayerDrawable) getDrawable();

        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.FIT_XY);
        setOnTouchListener(this);
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent event) {
                        downX = event.getX();
                        downY = event.getY();

                        Drawable drawable = findDrawable(downX, downY);
                        if (drawable != null) {
                            //清空选择状态，并隐藏icon
                            resetState();
                            Message msg = new Message();
                            msg.what = 0;
                            handler.sendMessage(msg);

                            drawable.setState(SELECTED_STATE_SET);

                            msg = new Message();
                            msg.what = 1;
                            msg.arg1 = selected;
                            handler.sendMessageDelayed(msg, 100);

                            if (isAutoScale) {
                                return true;
                            }

                            if (getScale() < mMidScale) {
                                postDelayed(new AutoScaleRunnable(mMidScale, downX, downY), 16);
                                isAutoScale = true;
                            }
                        }
                        return true;
                    }
                });
    }

    /**
     * 获取当前图片的缩放值
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mDrawables.getIntrinsicWidth(), mDrawables.getIntrinsicHeight());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

        return true;
    }

    private void resetState() {
        int numberOfLayers = mDrawables.getNumberOfLayers();
        for (int i = 0; i < numberOfLayers; i++) {
            Drawable drawable = mDrawables.getDrawable(i);
            //            LogManager.e(TAG, "state: " + Arrays.toString(drawable.getState()));
            drawable.setState(EMPTY_STATE_SET);
            //            LogManager.e(TAG, "state: " + Arrays.toString(drawable.getState()));
        }
    }

    private Drawable findDrawable(float x, float y) {
        final int numberOfLayers = mDrawables.getNumberOfLayers();
        LogManager.e(TAG, "num: " + numberOfLayers);
        Drawable drawable;
        Bitmap bitmap;
        for (int i = numberOfLayers - 1; i >= 0; i--) {
            drawable = mDrawables.getDrawable(i);
            Drawable current = drawable.getCurrent();
            bitmap = ((BitmapDrawable) current).getBitmap();
            try {
                int pixel = bitmap.getPixel((int) x, (int) y);
                if (pixel == Color.TRANSPARENT) {
                    continue;
                }
            } catch (Exception e) {
                continue;
            }
            LogManager.e(TAG, "selected: " + i);
            selected = i;
            return drawable;
        }
        return null;
    }

    @Override
    public void onGlobalLayout() {
        if (!mOnce) {
            // 得到控件的宽和高
            int width = getWidth();
            int height = getHeight();

            // 得到我们的图片，以及宽和高
            Drawable drawable = getDrawable();
            if (drawable == null) {
                return;
            }
            int dh = drawable.getIntrinsicHeight();
            int dw = drawable.getIntrinsicWidth();

            float scale = 1.0f;

            // 图片的宽度大于控件的宽度，图片的高度小于空间的高度，我们将其缩小
            if (dw > width && dh < height) {
                scale = width * 1.0f / dw;
            }

            // 图片的宽度小于控件的宽度，图片的高度大于空间的高度，我们将其缩小
            if (dh > height && dw < width) {
                scale = height * 1.0f / dh;
            }

            // 缩小值
            if (dw > width && dh > height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            // 放大值
            if (dw < width && dh < height) {
                scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
            }

            /*
             * 得到了初始化时缩放的比例
             */
            mInitScale = scale;
            mMaxScale = mInitScale * 4;
            mMidScale = mInitScale * 2;

            // 将图片移动至控件的中间
            int dx = getWidth() / 2 - dw / 2;
            int dy = getHeight() / 2 - dh / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2,
                    height / 2);

            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }
    }

    /**
     * 注册OnGlobalLayoutListener这个接口
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 取消OnGlobalLayoutListener这个接口
     */
    @SuppressWarnings("deprecation")
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 自动放大与缩小
     */
    private class AutoScaleRunnable implements Runnable {
        /**
         * 缩放的目标值
         */
        private float mTargetScale;
        // 缩放的中心点
        private float x;
        private float y;

        private final float BIGGER = 1.5f;
        private final float SMALL = 0.5f;

        private float tmpScale;

        /**
         * @param mTargetScale
         * @param x
         * @param y
         */
        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
                setScaleType(ScaleType.MATRIX);
            }
            if (getScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            //进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();

            if ((tmpScale > 1.0f && currentScale < mTargetScale) || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                //这个方法是重新调用run()方法
                postDelayed(this, 16);
            } else {
                //设置为我们的目标值
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);

                isAutoScale = false;
            }

            if (tmpScale == SMALL) {
                setScaleType(ScaleType.FIT_XY);
            }
        }
    }

    /**
     * 在缩放的时候进行边界以及我们的位置的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        // 缩放时进行边界检测，防止出现白边
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        /*
         * 如果宽度或高度小于空间的宽或者高，则让其居中
         */
        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2f;
        }

        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2f;
        }

        mScaleMatrix.postTranslate(deltaX, deltaY);
    }

    /**
     * 获得图片放大缩小以后的宽和高，以及left，right，top，bottom
     */
    private RectF getMatrixRectF() {
        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();
        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }
        return rectF;
    }

    private android.os.Handler handler;

    public void initPop(android.os.Handler handler) {
        this.handler = handler;
    }

    public void reset() {
        resetState();
        postDelayed(new AutoScaleRunnable(mInitScale, downX, downY), 0);
        isAutoScale = true;

        Message msg = new Message();
        msg.what = 2;
        handler.sendMessage(msg);
    }
}
