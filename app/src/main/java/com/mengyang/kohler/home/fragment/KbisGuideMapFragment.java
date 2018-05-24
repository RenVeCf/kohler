package com.mengyang.kohler.home.fragment;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 2018科勒上海厨卫展——导览图
 */

public class KbisGuideMapFragment extends BaseFragment implements OnPhotoTapListener {
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;
    @BindView(R.id.photo_view1)
    PhotoView mPhotoView1;
    @BindView(R.id.photo_view2)
    PhotoView mPhotoView2;
    @BindView(R.id.photo_view3)
    PhotoView mPhotoView3;
    @BindView(R.id.photo_view4)
    PhotoView mPhotoView4;
    @BindView(R.id.photo_view5)
    PhotoView mPhotoView5;

    private PhotoViewAttacher mAttacher;

    List<String> mYList = new ArrayList<>();
    List<String> mXList = new ArrayList<>();
    private PhotoViewAttacher mAttacher1;
    private PhotoViewAttacher mAttacher2;
    private PhotoViewAttacher mAttacher3;
    private PhotoViewAttacher mAttacher4;
    private PhotoViewAttacher mAttacher5;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_guide_map;
    }

    @Override
    protected void initValues() {
        mAttacher = new PhotoViewAttacher(mPhotoView);
        mAttacher1 = new PhotoViewAttacher(mPhotoView1);
        mAttacher2 = new PhotoViewAttacher(mPhotoView2);
        mAttacher3 = new PhotoViewAttacher(mPhotoView3);
        mAttacher4 = new PhotoViewAttacher(mPhotoView4);
        mAttacher5 = new PhotoViewAttacher(mPhotoView5);

        mAttacher.setOnPhotoTapListener(this);
        mAttacher1.setOnPhotoTapListener(this);
        mAttacher2.setOnPhotoTapListener(this);
        mAttacher3.setOnPhotoTapListener(this);
        mAttacher4.setOnPhotoTapListener(this);
        mAttacher5.setOnPhotoTapListener(this);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @Override
    public void onPhotoTap(ImageView view, float x, float y) {
        mYList.add(y + "");
        mXList.add(x + "");

        String yyy = mYList.toString();
        String xxx = mXList.toString();
        Log.i("123", "mYList = " + yyy);
        Log.i("123", "mXList = " + xxx);

        float scale = mPhotoView.getScale();

        if ((y >= 0.01 && y <= 0.50) && (x >= 0.20 && x <= 0.72)) {
            setAllGone(5, scale);

            Log.i("123789465", "左上");
        } else if ((y >= 0.27 && y <= 0.82) && (x >= 0.04 && x <= 0.61)){
            setAllGone(4, scale);

            Log.i("123789465", "左下");
        } else if ((y >= 0.30 && y <= 0.51) && (x >= 0.83 && x <= 0.99)){
            setAllGone(1, scale);

            Log.i("123789465", "右上");
        } else if ((y >= 0.49 && y <= 0.64) && (x >= 0.74 && x <= 0.90)){
            setAllGone(2, scale);

            Log.i("123789465", "右中");
        } else if ((y >= 0.64 && y <= 0.88) && (x >= 0.66 && x <= 0.88)){
            setAllGone(3, scale);

            Log.i("123789465", "右下");
        }
    }

    private void setAllGone(int order, float scale) {
        mPhotoView1.setVisibility(View.GONE);
        mPhotoView2.setVisibility(View.GONE);
        mPhotoView3.setVisibility(View.GONE);
        mPhotoView4.setVisibility(View.GONE);
        mPhotoView5.setVisibility(View.GONE);

        switch (order) {
            case 1:
                mPhotoView1.setVisibility(View.VISIBLE);
                mPhotoView1.setScale(scale);
                break;
            case 2:
                mPhotoView2.setVisibility(View.VISIBLE);
                mPhotoView2.setScale(scale);
                break;
            case 3:
                mPhotoView3.setVisibility(View.VISIBLE);
                mPhotoView3.setScale(scale);
                break;
            case 4:
                mPhotoView4.setVisibility(View.VISIBLE);
                mPhotoView4.setScale(scale);
                break;
            case 5:
                mPhotoView5.setVisibility(View.VISIBLE);
                mPhotoView5.setScale(scale);
                break;
            default:
                break;
        }
    }


    public void clearData(View view) {
        mYList.clear();
        mXList.clear();

        mPhotoView1.setVisibility(View.GONE);
        mPhotoView2.setVisibility(View.GONE);
        mPhotoView3.setVisibility(View.GONE);
        mPhotoView4.setVisibility(View.GONE);
        mPhotoView5.setVisibility(View.GONE);
    }
}
