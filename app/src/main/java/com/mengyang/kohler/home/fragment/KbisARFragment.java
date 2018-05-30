package com.mengyang.kohler.home.fragment;

import android.content.Intent;

import com.allyes.analytics.AIOAnalytics;
import com.deepano.kohlortest.UnityPlayerActivity;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.umeng.analytics.MobclickAgent;

public class KbisARFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_ar;
    }

    @Override
    protected void initValues() {
        //        MobclickAgent.onEvent(getActivity(), "arjieshuo");
        //        AIOAnalytics.onEvent("arjieshuo");
        //        Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
        //        intent.putExtra("flag", "9");
        //        startActivity(intent);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //TODO now it's visible to user
            Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
            intent.putExtra("flag", "9");
            startActivity(intent);
        } else {
            //TODO now it's invisible to user
        }

    }
}
