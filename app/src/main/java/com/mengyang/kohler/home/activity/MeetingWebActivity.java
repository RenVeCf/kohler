package com.mengyang.kohler.home.activity;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;

import butterknife.BindView;

public class MeetingWebActivity extends BaseActivity {

    @BindView(R.id.wv_meeting)
    WebView wvMeeting;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_meeting_web;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        wvMeeting.loadUrl(getIntent().getStringExtra("meeting_web"));
        WebSettings settings = wvMeeting.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
