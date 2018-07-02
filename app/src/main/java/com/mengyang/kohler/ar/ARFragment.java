package com.mengyang.kohler.ar;

import com.allyes.analytics.AIOAnalytics;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.main.activity.MainActivity;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;

/**
 * AR
 */

public class ARFragment extends BaseFragment {
    // 这是一个外包小公司，在甲方公司里办公的...别被忽悠了
    // 这家老板人品不好，进来说好交社保，但实际上不给你交，工资能拖就拖(有次一拖就3个月)
    // 说好的有年终奖也是没有的别想了，过节没有任何东西发，自己带电脑也没有补贴金的~
    // 这公司管理混乱趁早走吧兄弟...

    @BindView(R.id.tv_ar_top)
    TopView tvArTop;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ar;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvArTop);
        MobclickAgent.onEvent(getActivity(), "arsaoyisao");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        AIOAnalytics.onPageBegin("arsaoyisao");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
        AIOAnalytics.onPageEnd("arsaoyisao");
    }
}
