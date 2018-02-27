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
        ScaleGestureDetector.OnScaleGestureListener, ViewTreeObserver.OnGlobalLayoutListener {

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

    /**
     * 捕获用户多指触控时缩放的比例
     */
    private ScaleGestureDetector mScaleGestureDetector;

    // **********自由移动的变量***********
    /**
     * 记录上一次多点触控的数量
     */
    private int mLastPointerCount;

    private float mLastX;
    private float mLastY;

    private int mTouchSlop;
    private boolean isCanDrag;

    private boolean isCheckLeftAndRight;
    private boolean isCheckTopAndBottom;

    // *********双击放大与缩小*********
    private GestureDetector mGestureDetector;

    private boolean isAutoScale;

    public ColourImageBaseLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawables = (LayerDrawable) getDrawable();

        init(context);
    }

    private void init(Context context) {
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        mGestureDetector = new GestureDetector(context,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onDoubleTap(MotionEvent e) {

                        LogManager.e(TAG, "onDoubleTap");
                        //
                        //                        if (isAutoScale) {
                        //                            return true;
                        //                        }
                        //
                        //                        float alert_cancel = e.getX();
                        //                        float y = e.getY();
                        //
                        //                        if (getScale() < mMidScale) {
                        //                            postDelayed(new AutoScaleRunnable(mMidScale, alert_cancel, y), 16);
                        //                            isAutoScale = true;
                        //                        } else {
                        //                            postDelayed(new AutoScaleRunnable(mInitScale, alert_cancel, y), 16);
                        //                            isAutoScale = true;
                        //                        }
                        return true;
                    }
                });
    }

    /**
     * 获取当前图片的缩放值
     *
     * @return
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

    private int selected;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //        if (mGestureDetector.onTouchEvent(event)) {
        //            return true;
        //        }
        //
        //        mScaleGestureDetector.onTouchEvent(event);

        Message msg = new Message();
        msg.what = 0;
        handler.sendMessage(msg);

        final float downX = event.getX();
        final float downY = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            resetState();
            Drawable drawable = findDrawable(downX, downY);
            if (drawable != null) {
                drawable.setState(SELECTED_STATE_SET);


                msg = new Message();
                msg.what = 1;
                msg.arg1 = selected;
                handler.sendMessageDelayed(msg, 100);

                LogManager.e(TAG, "ACTION_DOWN");

                if (isAutoScale) {
                    return true;
                }

                if (getScale() < mMidScale) {
                    postDelayed(new AutoScaleRunnable(mMidScale, downX, downY), 16);
                    isAutoScale = true;
                }
                //                else {
                //                    postDelayed(new AutoScaleRunnable(mInitScale, downX, downY), 16);
                //                    isAutoScale = true;
                //                }
            }
        }

        float x = 0;
        float y = 0;
        // 拿到多点触控的数量
        int pointerCount = event.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }
        mLastPointerCount = pointerCount;
        RectF rectF = getMatrixRectF();
        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }

                if (isCanDrag) {
                    if (getDrawable() != null) {
                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        // 如果宽度小于控件宽度，不允许横向移动
                        if (rectF.width() < getWidth()) {
                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        // 如果高度小于控件高度，不允许纵向移动
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);

                        checkBorderWhenTranslate();

                        setImageMatrix(mScaleMatrix);
                    }
                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mLastPointerCount = 0;
                break;

            default:
                break;
        }

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
        Drawable drawable = null;
        Bitmap bitmap = null;
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

    /**
     * 当移动时进行边界检查
     */
    private void checkBorderWhenTranslate() {
        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int heigth = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < heigth && isCheckTopAndBottom) {
            deltaY = heigth - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 判断是否是move
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {
        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    // 缩放区间时initScale maxScale
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scale = getScale();
        float scaleFactor = detector.getScaleFactor();

        if (getDrawable() == null) {
            return true;
        }

        // 缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f)
                || (scale > mInitScale && scaleFactor < 1.0f)) {
            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }

            if (scale * scaleFactor > mMaxScale) {
                scale = mMaxScale / scale;
            }

            // 缩放
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    detector.getFocusX(), detector.getFocusY());

            checkBorderAndCenterWhenScale();

            setImageMatrix(mScaleMatrix);
        }

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {

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

            /**
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

        private final float BIGGER = 1.07f;
        private final float SMALL = 0.93f;

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
            }
            if (getScale() > mTargetScale) {
                tmpScale = SMALL;
            }
        }

        @Override
        public void run() {
            LogManager.e(TAG, "run");
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

        /**
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
     *
     * @return
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
}
