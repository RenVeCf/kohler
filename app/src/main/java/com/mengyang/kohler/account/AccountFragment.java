package com.mengyang.kohler.account;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.CircleImageView;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

public class AccountFragment extends BaseFragment {
    @BindView(R.id.tv_account_top)
    TopView tvAccountTop;
    @BindView(R.id.civ_account_title)
    CircleImageView civAccountTitle;
    @BindView(R.id.tv_account_name)
    TextView tvAccountName;
    @BindView(R.id.rb_message)
    RadioButton rbMessage;
    @BindView(R.id.rb_phone)
    RadioButton rbPhone;
    @BindView(R.id.rg_select)
    RadioGroup rgSelect;
    @BindView(R.id.rv_mine_browsing)
    RecyclerView rvMineBrowsing;
    @BindView(R.id.ll_mine_browsing)
    LinearLayout llMineBrowsing;
    @BindView(R.id.iv_top_settings)
    ImageView ivTopSettings;

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
}
