package com.mengyang.kohler.common.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.MediaController;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoView;

import butterknife.BindView;

public class ARWebViewActivity extends BaseActivity implements PLOnInfoListener, PLOnCompletionListener, PLOnVideoSizeChangedListener, PLOnErrorListener {

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
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);

        if (!getIntent().getStringExtra("AR_url").equals("") && getIntent().getStringExtra("AR_url") != null)
            mVideoView.setVideoPath(getIntent().getStringExtra("AR_url"));

        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_16_9);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_4_3);

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
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }
}
