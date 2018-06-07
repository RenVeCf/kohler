package com.mengyang.kohler.common.activity;

import android.content.Intent;
import android.view.View;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.MediaController;
import com.pili.pldroid.player.PLOnCompletionListener;
import com.pili.pldroid.player.PLOnErrorListener;
import com.pili.pldroid.player.PLOnInfoListener;
import com.pili.pldroid.player.PLOnVideoSizeChangedListener;
import com.pili.pldroid.player.widget.PLVideoView;

public class ARWebViewActivity extends BaseActivity implements PLOnInfoListener, PLOnCompletionListener, PLOnVideoSizeChangedListener, PLOnErrorListener {

    private PLVideoView mVideoView;
    private MediaController mMediaController;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_arweb_view;
    }

    @Override
    protected void initValues() {
        mVideoView = (PLVideoView) findViewById(R.id.PLVideoView);

        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);

        View loadingView = findViewById(R.id.LoadingView);
        mVideoView.setBufferingIndicator(loadingView);

        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_ORIGIN);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_16_9);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_4_3);
        mVideoView.setVideoPath("http://ojd06y9cv.bkt.clouddn.com/e3d09c5aa71ea516ade43b7277983b0c.mp4");

        mVideoView.setOnInfoListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnVideoSizeChangedListener(this);
        mVideoView.setOnErrorListener(this);
    }

    @Override
    protected void initListener() {

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
