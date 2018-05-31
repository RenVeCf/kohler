package com.mengyang.kohler.home.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.home.adapter.KbisPhotoAdapter;
import com.mengyang.kohler.module.bean.KbisBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class KbisInterviewFragment extends BaseFragment {

    @BindView(R.id.rl_kbis_interview_first)
    RelativeLayout rlKbisInterviewFirst;
    @BindView(R.id.ll_kbis_interview_second)
    LinearLayout llKbisInterviewSecond;
    @BindView(R.id.ll_kbis_interview_third)
    LinearLayout rlKbisInterviewThird;

    private List<KbisBean.TextListBean> mKbisInterviewBean = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_interview;
    }

    @Override
    protected void initValues() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        KbisBean data = (KbisBean) getArguments().getSerializable("data");
        mKbisInterviewBean.clear();
        mKbisInterviewBean.addAll(data.getTextList());
    }

    @OnClick({R.id.rl_kbis_interview_first, R.id.ll_kbis_interview_second, R.id.ll_kbis_interview_third})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_kbis_interview_first:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("h5url", mKbisInterviewBean.get(0).getH5Url()));
                break;
            case R.id.ll_kbis_interview_second:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("h5url", mKbisInterviewBean.get(1).getH5Url()));
                break;
            case R.id.ll_kbis_interview_third:
                startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("h5url", mKbisInterviewBean.get(2).getH5Url()));
                break;
        }
    }
}
