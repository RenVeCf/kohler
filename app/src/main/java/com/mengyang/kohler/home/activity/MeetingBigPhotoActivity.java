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
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    private int mNum;
    private String mUrl = "";
    private int mId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_big_photo;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvMeetingBigPhotoTop);
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

    @OnClick({R.id.iv_meeting_big_photo_left, R.id.iv_meeting_big_photo_right, R.id.bt_meeting_big_photo_vote})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_meeting_big_photo_left:
                break;
            case R.id.iv_meeting_big_photo_right:
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
                        .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                            @Override
                            public void onSuccess(BasicResponse response) {
                                mNum += 1;
                            }
                        });
                break;
        }
    }
}