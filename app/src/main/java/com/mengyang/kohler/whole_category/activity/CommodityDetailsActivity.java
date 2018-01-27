package com.mengyang.kohler.whole_category.activity;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;

import butterknife.BindView;

/**
 * 商品详情
 */

public class CommodityDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_commodity_details_top)
    TopView tvCommodityDetailsTop;
    @BindView(R.id.tv_commodity_details_brand)
    TextView tvCommodityDetailsBrand;
    @BindView(R.id.tv_commodity_details_category)
    TextView tvCommodityDetailsCategory;
    @BindView(R.id.tv_commodity_details_model)
    TextView tvCommodityDetailsModel;
    @BindView(R.id.bt_commodity_details_like)
    Button btCommodityDetailsLike;
    @BindView(R.id.bt_commodity_details_pay)
    Button btCommodityDetailsPay;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_details;
    }

    @Override
    protected void initValues() {
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
}
