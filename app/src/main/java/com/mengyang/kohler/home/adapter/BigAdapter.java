package com.mengyang.kohler.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;

import java.util.ArrayList;

/**
 * Created by use012 on 2017/5/11.
 */

public class BigAdapter extends PagerAdapter {
    private  ArrayList<String> mImageViewUrl;
    private Context mContext;

    public BigAdapter(Context mContext,  ArrayList<String> mImageViewUrl) {
        this.mImageViewUrl = mImageViewUrl;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mImageViewUrl.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(App.getContext()).load(mImageViewUrl.get(position))
                .apply(new RequestOptions().placeholder(R.mipmap.oval)).into(imageView);

//        imageView.setBackgroundResource(mImageViewUrl[position]);
        
/*
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnImageClickListener != null) {
                    //                  接口实例化后的而对象，调用重写后的方法
                    mOnImageClickListener.onClick(v,position);
                }
            }
        });
*/
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }


    /**
     * 按钮点击事件对应的接口
     */
    private OnImageClickListener mOnImageClickListener;
    public interface OnImageClickListener{
        public void onClick(View view, int position);
    }

    /**
     *按钮点击事件需要的方法
     */
    public void SetImageOnclick(OnImageClickListener OnImageClickListener){
        this.mOnImageClickListener=OnImageClickListener;
    }

    /**
     * 触摸事件对应的接口
     */
    private OnImageTouchListener mOnImageTouchListener;
    public interface OnImageTouchListener{
        public void onImageTouch(View v, MotionEvent event);
    }
    /**
     *按钮点击事件需要的方法
     */
    public void SetImageTouchListen(OnImageTouchListener onImageTouchListener){
        this.mOnImageTouchListener=onImageTouchListener;
    }
}
