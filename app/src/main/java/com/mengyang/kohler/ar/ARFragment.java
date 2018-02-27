package com.mengyang.kohler.ar;

import android.content.Intent;

import com.gyf.barlibrary.ImmersionBar;
import com.kohler.arscan.UnityPlayerActivity;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

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

        Intent intent = new Intent(getContext(), UnityPlayerActivity.class);
        intent.putExtra("flag", "9");
        startActivity(intent);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
