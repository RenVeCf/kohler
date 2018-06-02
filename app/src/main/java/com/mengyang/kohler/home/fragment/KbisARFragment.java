package com.mengyang.kohler.home.fragment;

import android.content.Intent;
import android.view.View;

import com.allyes.analytics.AIOAnalytics;
import com.deepano.kohlortest.UnityPlayerActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.home.activity.KbisActivity;
import com.umeng.analytics.MobclickAgent;

public class KbisARFragment extends BaseFragment {

    private static OnActivityPagerView mOnActivityPagerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_ar;
    }

    @Override
    protected void initValues() {
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    public static void setOnActivityPagerView(OnActivityPagerView onActivityPagerView) {
        mOnActivityPagerView = onActivityPagerView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //TODO now it's visible to user
            Intent intent = new Intent(App.getContext(), UnityPlayerActivity.class);
            intent.putExtra("flag", "9");
            startActivityForResult(intent, IConstants.AZURE_BACK_ONE);
        } else {
            //TODO now it's invisible to user
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 0) {
            switch (requestCode) {
                case IConstants.AZURE_BACK_ONE:
                    mOnActivityPagerView.onActivityPagerView();
                    break;
            }
        }
    }

    public interface OnActivityPagerView {
        // TODO: Update argument type and name
        void onActivityPagerView();
    }
}
