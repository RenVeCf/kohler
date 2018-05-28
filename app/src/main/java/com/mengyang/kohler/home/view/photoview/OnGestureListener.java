package com.mengyang.kohler.home.view.photoview;

/**
 * Created by liusong on 2018/5/28.
 */

interface OnGestureListener {

    void onDrag(float dx, float dy);

    void onFling(float startX, float startY, float velocityX,
                 float velocityY);

    void onScale(float scaleFactor, float focusX, float focusY);

}
