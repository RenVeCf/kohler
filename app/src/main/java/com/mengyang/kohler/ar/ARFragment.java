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
