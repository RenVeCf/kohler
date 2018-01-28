package com.mengyang.kohler.whole_category.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.activity.ShopsListActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 商品详情
 */

public class CommodityDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_commodity_details_top)
    TopView tvCommodityDetailsTop;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;
    @BindView(R.id.tv_commodity_details_brand)
    TextView tvCommodityDetailsBrand;
    @BindView(R.id.tv_commodity_details_category)
    TextView tvCommodityDetailsCategory;
    @BindView(R.id.tv_commodity_details_model)
    TextView tvCommodityDetailsModel;
    @BindView(R.id.iv_size_diagram_download)
    ImageView ivSizeDiagramDownload;
    @BindView(R.id.iv_installation_instructions_download)
    ImageView ivInstallationInstructionsDownload;
    @BindView(R.id.ll_commodity_details_purchase_inquiries)
    LinearLayout llCommodityDetailsPurchaseInquiries;
    @BindView(R.id.bt_commodity_details_like)
    Button btCommodityDetailsLike;
    @BindView(R.id.bt_commodity_details_pay)
    Button btCommodityDetailsPay;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_details;
    }

    @Override
    protected void initValues() {
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCommodityDetailsTop);
        ivTopBack.setImageResource(R.mipmap.fanhuibai);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_size_diagram_download, R.id.iv_installation_instructions_download, R.id.ll_commodity_details_purchase_inquiries, R.id.bt_commodity_details_like, R.id.bt_commodity_details_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_size_diagram_download:
                break;
            case R.id.iv_installation_instructions_download:
                break;
            case R.id.ll_commodity_details_purchase_inquiries:
                startActivity(new Intent(this, ShopsListActivity.class));
                break;
            case R.id.bt_commodity_details_like:
                break;
            case R.id.bt_commodity_details_pay:
                break;
        }
    }
}
