package com.mengyang.kohler.home.adapter;

import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.mengyang.kohler.BaseActivity;

import java.util.List;

import uk.co.senab.photoview.PhotoView;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/16
 */

public class MyImageAdapter extends PagerAdapter {
    private List<String> imageUrls;
    private BaseActivity activity;
    private PictureLongClickListenner mPictureLongClickListenner;

    public MyImageAdapter(List<String> imageUrls, BaseActivity activity) {
        this.imageUrls = imageUrls;
        this.activity = activity;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final String url = imageUrls.get(position);

        PhotoView photoView = new PhotoView(activity);
        Glide.with(activity)
                .load(url)
                .into(photoView);
        container.addView(photoView);

        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mPictureLongClickListenner != null) {
                    mPictureLongClickListenner.onPictureLongClick(url);
                }
                return false;
            }
        });

        return photoView;
    }

    @Override
    public int getCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public interface PictureLongClickListenner{
        void onPictureLongClick(String url);
    }

    public void SetPictureLongClickListenner(PictureLongClickListenner pictureLongClickListenner) {
        mPictureLongClickListenner = pictureLongClickListenner;
    }
}
