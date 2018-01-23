package com.mengyang.kohler.ar;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

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
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
