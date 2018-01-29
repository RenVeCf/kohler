package com.mengyang.kohler.account.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.AccountSettingsActivity;
import com.mengyang.kohler.common.utils.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;
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
    @BindView(R.id.tv_account_browsing_no_login)
    TextView tvAccountBrowsingNoLogin;
    @BindView(R.id.rv_account_browsing)
    RecyclerView rvAccountBrowsing;
    private PopupWindow mAccountTitleNamePopupWindow;
    private View mPopLayout;
    //是否登录
    boolean is_Login = (boolean) SPUtil.get(App.getContext(), IConstants.IS_LOGIN, false);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvAccountTop);
        if (is_Login) {
            final Drawable DrawableLeftLike = getResources().getDrawable(R.mipmap.shouchan);
            final Drawable DrawableLeftQuery = getResources().getDrawable(R.mipmap.yuyue);
            tvAccountBrowsingNoLogin.setVisibility(View.GONE);
            rvAccountBrowsing.setVisibility(View.VISIBLE);
            btAccountLike.setTextColor(Color.BLACK);
            btAineQuery.setTextColor(Color.BLACK);
            btAccountLike.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftLike, null, null, null);
            btAineQuery.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftQuery, null, null, null);
            btAccountLike.setBackgroundResource(R.drawable.account_stroke_lgoin_bt_bg);
            btAineQuery.setBackgroundResource(R.drawable.account_stroke_lgoin_bt_bg);
        } else {
            final Drawable DrawableLeftLike = getResources().getDrawable(R.mipmap.icon_collection);
            final Drawable DrawableLeftQuery = getResources().getDrawable(R.mipmap.icon_inquiry);
            tvAccountBrowsingNoLogin.setVisibility(View.VISIBLE);
            rvAccountBrowsing.setVisibility(View.GONE);
            civAccountTitle.setImageResource(R.mipmap.oval);
            tvAccountName.setText(App.getContext().getResources().getString(R.string.login_or_register));
            btAccountLike.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftLike, null, null, null);
            btAineQuery.setCompoundDrawablesWithIntrinsicBounds(DrawableLeftQuery, null, null, null);
            btAccountLike.setTextColor(App.getContext().getResources().getColor(R.color.account_no_login_bt));
            btAineQuery.setTextColor(App.getContext().getResources().getColor(R.color.account_no_login_bt));
            btAccountLike.setBackgroundResource(R.drawable.account_stroke_no_lgoin_bt_bg);
            btAineQuery.setBackgroundResource(R.drawable.account_stroke_no_lgoin_bt_bg);
        }
        mAccountTitleNamePopupWindow = new PopupWindow(App.getContext());
        mAccountTitleNamePopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mAccountTitleNamePopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        mPopLayout = inflater.inflate(R.layout.popup_window_account_title_name, null);
        mAccountTitleNamePopupWindow.setContentView(mPopLayout);
        mAccountTitleNamePopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mAccountTitleNamePopupWindow.setOutsideTouchable(false);
        mAccountTitleNamePopupWindow.setFocusable(true);
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
                break;
            case R.id.tv_account_name:
                if (is_Login)
                    mAccountTitleNamePopupWindow.showAsDropDown(view, 0, 0);
                else

                break;
            case R.id.bt_account_like:
                break;
            case R.id.bt_account_query:
                break;
        }
    }
}
