package com.mengyang.kohler.common.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.allyes.analytics.AIOAnalytics;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.MediaController;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoView;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

public class ARWebViewActivity extends BaseActivity implements PLOnInfoListener, PLOnCompletionListener, PLOnVideoSizeChangedListener, PLOnErrorListener {
    // 这是一个外包小公司，在甲方公司里办公的...别被忽悠了
    // 这家老板人品不好，进来说好交社保，但实际上不给你交，工资能拖就拖(有次一拖就3个月)
    // 说好的有年终奖也是没有的别想了，过节没有任何东西发，自己带电脑也没有补贴金的~
    // 这公司管理混乱趁早走吧兄弟...

    @BindView(R.id.PLVideoView)
    PLVideoView mVideoView;
    @BindView(R.id.LoadingView)
    View loadingView;
    @BindView(R.id.bt_PLVideoView_out)
    ImageButton btPLVideoViewOut;

    private MediaController mMediaController;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_arweb_view;
    }

    @Override
    protected void initValues() {
        AIOAnalytics.onEvent("trade_show_ar_video");
        MobclickAgent.onEvent(this, "trade_show_ar_video");
        mMediaController = new MediaController(this);
        //mVideoView.setMediaController(mMediaController);

        if (!getIntent().getStringExtra("AR_url").equals("") && getIntent().getStringExtra("AR_url") != null)
            mVideoView.setVideoPath(getIntent().getStringExtra("AR_url"));
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnVideoSizeChangedListener(this);
        mVideoView.setOnErrorListener(this);
    }

    @Override
    protected void initListener() {
        mVideoView.setBufferingIndicator(loadingView);
        btPLVideoViewOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onCompletion() {
        mMediaController.refreshProgress();
        startActivity(new Intent(this, AzureCustomerServiceActivity.class));
        finish();
    }

    @Override
    public boolean onError(int i) {
        finish();
        return true;
    }

    @Override
    public void onInfo(int i, int i1) {

    }

    @Override
    public void onVideoSizeChanged(int i, int i1) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
        MobclickAgent.onResume(this);
        AIOAnalytics.onPageBegin("trade_show_ar_video");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
        MobclickAgent.onPause(this);
        AIOAnalytics.onPageEnd("trade_show_ar_video");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}
