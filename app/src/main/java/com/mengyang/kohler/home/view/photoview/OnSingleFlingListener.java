package com.mengyang.kohler.home.view.photoview;

import android.view.MotionEvent;

/**
 * Created by MengYang on 2018/5/28.
 */
public interface OnSingleFlingListener {
    boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY);
}
