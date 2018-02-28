package com.kohler.arscan.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * Created by chenchen on 18-1-19.
 */

public class JumpImageView extends AppCompatImageView {

    private AnimatorSet upAnimator;//上升的动画集合
    private AnimatorSet downAnimator;//下降的动画集合
    private boolean move = false;//当前有没有执行动画
    private Runnable runnable;
    private int jumpDistance = 50;//跳动的距离
    private int time = 500;//跳动一个周期的时间，0.5秒

    public JumpImageView(Context context) {
        super(context);
        initData();
    }

    public JumpImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public JumpImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        runnable = new Runnable() {
            @Override
            public void run() {
                move = true;
                //先执行上升动画
                rise();
            }
        };
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        startAnimate(0);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
//        stopAnimate();
    }

    //开始执行动画，从runnable开始，然后rise和freeFall互调
    public void startAnimate(int delay) {
        if (!move) {
            if (downAnimator != null && downAnimator.isRunning()) {
                return;
            }
            this.removeCallbacks(runnable);
            if (delay > 0) {
                this.postDelayed(runnable, delay);
            } else {
                this.post(runnable);
            }
        }
    }

    //停止动画,停止所有动画和监听
    public void stopAnimate() {
        if (upAnimator != null) {
            if (upAnimator.isRunning()) {
                upAnimator.cancel();
            }
            upAnimator.removeAllListeners();

            for (Animator animator : upAnimator.getChildAnimations()) {
                animator.removeAllListeners();
            }
            upAnimator = null;
        }

        if (downAnimator != null) {
            if (downAnimator.isRunning()) {
                downAnimator.cancel();
            }
            downAnimator.removeAllListeners();
            for (Animator animator : downAnimator.getChildAnimations()) {
                animator.removeAllListeners();
            }
            downAnimator = null;
        }
    }

    private void rise() {
        if (upAnimator == null) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationY", jumpDistance, 0);
            upAnimator = new AnimatorSet();
            upAnimator.playTogether(objectAnimator);
            upAnimator.setDuration(time);
            upAnimator.setInterpolator(new DecelerateInterpolator(1.2f));
            upAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (move) {
                        fail();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        upAnimator.start();
    }

    private void fail() {
        if (downAnimator == null) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "translationY", 0, jumpDistance);
            downAnimator = new AnimatorSet();
            downAnimator.playTogether(objectAnimator);
            downAnimator.setDuration(time);
            downAnimator.setInterpolator(new AccelerateInterpolator(1.2f));
            downAnimator.addListener(new Animator.AnimatorListener() {

                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    if (move) {
                        rise();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
        downAnimator.start();
    }

}
