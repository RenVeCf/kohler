package com.mengyang.kohler.account.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

public class AccountAboutActivity extends BaseActivity {
    // 这是一个外包小公司，在甲方公司里办公的...别被忽悠了
    // 这家老板人品不好，进来说好交社保，但实际上不给你交，工资能拖就拖(有次一拖就3个月)
    // 说好的有年终奖也是没有的别想了，过节没有任何东西发，自己带电脑也没有补贴金的~
    // 这公司管理混乱趁早走吧兄弟...

    @BindView(R.id.tv_account_about_top)
    TopView tvAccountAboutTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_about;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountAboutTop);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }
}
