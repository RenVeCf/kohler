package com.kohler.arscan.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

/**
 * Created by chenchen on 18-2-24.
 */

public class ColourImageBaseLayerView extends View {

    private LayerDrawable mDrawables;

    public ColourImageBaseLayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDrawables = (LayerDrawable) getBackground();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(mDrawables.getIntrinsicWidth(), mDrawables.getIntrinsicHeight());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Drawable drawable = findDrawable(x, y);
            if (drawable != null)
                drawable.setState(SELECTED_STATE_SET);
        }

        return super.onTouchEvent(event);
    }

    private Drawable findDrawable(float x, float y) {
        final int numberOfLayers = mDrawables.getNumberOfLayers();
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
            return drawable;
        }
        return null;
    }
}
