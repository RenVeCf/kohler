package com.mengyang.kohler.home.activity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.LiveRealTimeBean;

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
    private int position;
    private int mNum;
    private String mUrl = "";
    private int mId;
    private int pageNum = 0;
    private int mTotalSize = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_big_photo;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMeetingBigPhotoTop);
        position = getIntent().getIntExtra("position", 0);
        mTotalSize = getIntent().getIntExtra("totalSize", 0);
        if (position == 0)
            ivMeetingBigPhotoLeft.setVisibility(View.GONE);
        else if ((mTotalSize - 1) == position)
            ivMeetingBigPhotoRight.setVisibility(View.GONE);

        mNum = getIntent().getIntExtra("num", 0);
        tvMeetingBigPhotoNum.setText(mNum + "");
        mUrl = getIntent().getStringExtra("url");
        Glide.with(App.getContext()).load(mUrl).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into(ivMeetingBigPhoto);
        mId = getIntent().getIntExtra("id", 0);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void getGigPhotoData() {
        Map<String, String> stringMap = IdeaApi.getSign();
        if ((position + "").length() != 1) {
            pageNum = (int) Math.floor(position / 10);
        }
        stringMap.put("pageNum", pageNum + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getMeetingLiveRealTime(stringMap)
                .compose(MeetingBigPhotoActivity.this.<BasicResponse<LiveRealTimeBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<LiveRealTimeBean>>(MeetingBigPhotoActivity.this, false) {
                    @Override
                    public void onSuccess(BasicResponse<LiveRealTimeBean> response) {
                        int i = position;
                        if (response.getData().getPageSize() > i) {
                            if (response.getData().getPageSize() == (i + 1)) {
                                ivMeetingBigPhotoRight.setVisibility(View.GONE);
                            }
                            if ((position + "").length() > 1) {
                                i = position % 10;
                            }
                            tvMeetingBigPhotoNum.setText(response.getData().getResultList().get(i).getLikeCount() + "");
                            Glide.with(App.getContext()).load(response.getData().getResultList().get(i).getPicUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into(ivMeetingBigPhoto);
                            mId = response.getData().getResultList().get(i).getId();
                        }
                    }
                });
    }

    @OnClick({R.id.iv_meeting_big_photo_left, R.id.iv_meeting_big_photo_right, R.id.bt_meeting_big_photo_vote})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_meeting_big_photo_left:
                position--;
                if (position == 0) {
                    ivMeetingBigPhotoLeft.setVisibility(View.GONE);
                } else if (position == 9) {
                    pageNum--;
                }
                getGigPhotoData();
                ivMeetingBigPhotoRight.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_meeting_big_photo_right:
                ivMeetingBigPhotoLeft.setVisibility(View.VISIBLE);
                position++;
                getGigPhotoData();
                break;
            case R.id.bt_meeting_big_photo_vote:
                Map<String, String> stringMap = IdeaApi.getSign();
                stringMap.put("id", mId + "");

                IdeaApi.getRequestLogin(stringMap);
                IdeaApi.getApiService()
                        .getMeetingLikePicture(stringMap)
                        .compose(this.<BasicResponse>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new DefaultObserver<BasicResponse>(this, false) {
                            @Override
                            public void onSuccess(BasicResponse response) {
                                mNum += 1;
                            }
                        });
                break;
            default:
                break;
        }
    }
}
