package com.mengyang.kohler.account.fragment;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.AccountSettingsActivity;
import com.mengyang.kohler.account.activity.UserMsgEditActivity;
import com.mengyang.kohler.common.view.CircleImageView;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 账户
 */

public class AccountFragment extends BaseFragment {

    @BindView(R.id.tv_account_top)
    TopView tvAccountTop;
    @BindView(R.id.iv_account_settings)
    ImageView ivAccountSettings;
    @BindView(R.id.civ_account_title)
    CircleImageView civAccountTitle;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.bt_account_like)
    Button btAccountLike;
    @BindView(R.id.bt_account_query)
    Button btAineQuery;
    @BindView(R.id.rv_account_browsing)
    RecyclerView rvAccountBrowsing;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvAccountTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_account_settings, R.id.civ_account_title, R.id.tv_account_name, R.id.bt_account_like, R.id.bt_account_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_account_settings:
                startActivity(new Intent(App.getContext(), AccountSettingsActivity.class));
                break;
            case R.id.civ_account_title:
                startActivity(new Intent(App.getContext(), UserMsgEditActivity.class));
                break;
            case R.id.tv_account_name:
                break;
            case R.id.bt_account_like:
                break;
            case R.id.bt_account_query:
                break;
        }
    }
}
