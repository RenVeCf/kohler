package com.mengyang.kohler.home.activity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringListener;
import com.facebook.rebound.SpringSystem;
import com.facebook.rebound.SpringUtil;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.home.view.ImageInfoObj;
import com.mengyang.kohler.home.view.ImageWidgetInfoObj;

import butterknife.BindView;

public class ArtKohlerSelectBigImgActivity extends BaseActivity {

    @BindView(R.id.iv_art_kohler_select_big_img)
    ImageView ivArtKohlerSelectBigImg;
    private ImageInfoObj imageInfoObj;
    private ImageWidgetInfoObj imageWidgetInfoObj;

    // 屏幕宽度
    public float Width;
    //原图高
    private float y_img_h;
    // 屏幕高度
    public float Height;
    private float size, size_h, img_w, img_h;
    protected float to_x = 0;
    protected float to_y = 0;
    private float tx;
    private float ty;


    private final Spring spring = SpringSystem
            .create()
            .createSpring()
            .addListener(new ExampleSpringListener());

    @Override
    protected int getLayoutId() {
        return R.layout.activity_art_kohler_select_big_img;
    }

    @Override
    protected void initValues() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        Width = dm.widthPixels;
        Height = dm.heightPixels;

        imageInfoObj = (ImageInfoObj) getIntent().getSerializableExtra("imageInfoObj");
        imageWidgetInfoObj = (ImageWidgetInfoObj) getIntent().getSerializableExtra("imageWidgetInfoObj");
        Glide.with(App.getContext()).load(imageInfoObj.imageUrl).into(ivArtKohlerSelectBigImg);

        img_w = imageWidgetInfoObj.width;
        img_h = imageWidgetInfoObj.height - 300;
        size = Width / img_w;
        y_img_h = imageInfoObj.imageHeight * Width / imageInfoObj.imageWidth;
        size_h = y_img_h / img_h;
    }

    @Override
    protected void initListener() {
        ivArtKohlerSelectBigImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    private class ExampleSpringListener implements SpringListener {

        @Override
        public void onSpringUpdate(Spring spring) {
            double CurrentValue = spring.getCurrentValue();
            float mappedValue = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size);
            float mapy = (float) SpringUtil.mapValueFromRangeToRange(CurrentValue, 0, 1, 1, size_h);
            ivArtKohlerSelectBigImg.setScaleX(mappedValue);
            ivArtKohlerSelectBigImg.setScaleY(mapy);
            if (CurrentValue == 1) {
                //                showImageView.setVisibility(View.GONE);
            }
        }

        @Override
        public void onSpringAtRest(Spring spring) {

        }

        @Override
        public void onSpringActivate(Spring spring) {

        }

        @Override
        public void onSpringEndStateChange(Spring spring) {

        }
    }

    private void MoveView() {
        ObjectAnimator.ofFloat(ivArtKohlerSelectBigImg, "alpha", 0.8f).setDuration(0).start();
        ivArtKohlerSelectBigImg.setVisibility(View.VISIBLE);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(ivArtKohlerSelectBigImg, "translationX", tx).setDuration(200),
                ObjectAnimator.ofFloat(ivArtKohlerSelectBigImg, "translationY", ty).setDuration(200),
                ObjectAnimator.ofFloat(ivArtKohlerSelectBigImg, "alpha", 1).setDuration(200)

        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ivArtKohlerSelectBigImg.setScaleType(ImageView.ScaleType.FIT_XY);
                spring.setEndValue(1);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();

    }

    private void MoveBackView() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(ivArtKohlerSelectBigImg, "translationX", to_x).setDuration(200),
                ObjectAnimator.ofFloat(ivArtKohlerSelectBigImg, "translationY", to_y).setDuration(200)
        );
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();
    }
}
