package com.mengyang.kohler.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.BigAdapter;
import com.mengyang.kohler.home.adapter.LiveRealTimeAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.LiveRealTimeBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 现场实时投票大图
 */

public class MeetingBigPhotoActivity extends BaseActivity {

    @BindView(R.id.tv_meeting_big_photo_top)
    TopView tvMeetingBigPhotoTop;
    @BindView(R.id.iv_meeting_big_photo)
    ImageView ivMeetingBigPhoto;
    @BindView(R.id.tv_meeting_big_photo_num)
    TextView tvMeetingBigPhotoNum;
    @BindView(R.id.iv_meeting_big_photo_left)
    ImageView ivMeetingBigPhotoLeft;
    @BindView(R.id.iv_meeting_big_photo_right)
    ImageView ivMeetingBigPhotoRight;
    @BindView(R.id.bt_meeting_big_photo_vote)
    Button btMeetingBigPhotoVote;
    @BindView(R.id.view_pager_big)
    ViewPager mViewPagerBig;
    private int mNum;
    private String mUrl = "";
    private int mId;

    List<LiveRealTimeBean.ResultListBean> mLiveRealTimeBean = new ArrayList<>();
    private ArrayList<String> mImageViewUrl = new ArrayList<>();
    private BigAdapter mBigAdapter;
    private int mCurrPosion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_big_photo;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMeetingBigPhotoTop);
//        mLiveRealTimeBean = (List<LiveRealTimeBean.ResultListBean>) getIntent().getSerializableExtra("data");
//        if (mLiveRealTimeBean != null) {
//            for (int i = 0; i < mLiveRealTimeBean.size(); i++) {
//                mImageViewUrl.add(mLiveRealTimeBean.get(i).getPicUrl());
//            }
//
//
//        }

        requestData();



//        tvMeetingBigPhotoNum.setText(mNum + "");
//        mId =

//        mUrl = getIntent().getStringExtra("url");
//        Glide.with(App.getContext()).load(mUrl).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into(ivMeetingBigPhoto);
//        mId = getIntent().getIntExtra("id", 0);
    }

    private void requestData() {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getMeetingLiveRealTime(stringMap)
                .compose(MeetingBigPhotoActivity.this.<BasicResponse<LiveRealTimeBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LiveRealTimeBean>>(MeetingBigPhotoActivity.this, true) {
                    @Override
                    public void onSuccess(BasicResponse<LiveRealTimeBean> response) {
                        if (response != null) {
                            mLiveRealTimeBean.clear();
                            mLiveRealTimeBean.addAll(response.getData().getResultList());

                            for (int i = 0; i < mLiveRealTimeBean.size(); i++) {
                                mImageViewUrl.add(mLiveRealTimeBean.get(i).getPicUrl());
                            }

                            mBigAdapter = new BigAdapter(MeetingBigPhotoActivity.this,mImageViewUrl);
                            mViewPagerBig.setAdapter(mBigAdapter);
                            mViewPagerBig.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                @Override
                                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                }

                                @Override
                                public void onPageSelected(int position) {
                                    mCurrPosion = position;
                                    mNum = mLiveRealTimeBean.get(position).getLikeCount();
                                    tvMeetingBigPhotoNum.setText(mNum + "");
                                }

                                @Override
                                public void onPageScrollStateChanged(int state) {

                                }
                            });
                        }
                    }
                });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_meeting_big_photo_left, R.id.iv_meeting_big_photo_right, R.id.bt_meeting_big_photo_vote})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_meeting_big_photo_left:
                break;
            case R.id.iv_meeting_big_photo_right:
                break;
            case R.id.bt_meeting_big_photo_vote:
                Map<String, String> stringMap = IdeaApi.getSign();
                stringMap.put("id", mLiveRealTimeBean.get(mCurrPosion).getId() + "");

                IdeaApi.getRequestLogin(stringMap);
                IdeaApi.getApiService()
                        .getMeetingLikePicture(stringMap)
                        .compose(this.<BasicResponse>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                            @Override
                            public void onSuccess(BasicResponse response) {
//                                mNum += 1;
                            }
                        });
                break;
            default:
                break;
        }
    }
}
