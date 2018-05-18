package com.mengyang.kohler.home.activity;

import com.allyes.analytics.AIOAnalytics;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

public class KbisActivity extends BaseActivity {

    @BindView(R.id.tv_kbis_top)
    TopView tvKbisTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_kbis;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvKbisTop);
        MobclickAgent.onEvent(KbisActivity.this, "shanghai_chu_wei_zhan");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        AIOAnalytics.onPageBegin("shanghai_chu_wei_zhan");
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        AIOAnalytics.onPageEnd("shanghai_chu_wei_zhan");
    }
}
