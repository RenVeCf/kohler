package com.mengyang.kohler.whole_category.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.activity.PDFActivity;
import com.mengyang.kohler.home.activity.StoreMapActivity;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    @BindView(R.id.mzb_commodity_details)
    MZBannerView mzbCommodityDetails;
    //轮播图集合
    private List<Integer> mDatas = new ArrayList<>();
    private PopupWindow mDownloadPopupWindow;
    private View mPopLayout;
    private TextView tvCommodityDetailsDownloadName;
    private Button btCommodityDetailsDownloadPreview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_details;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCommodityDetailsTop);
        ivTopBack.setImageResource(R.mipmap.fanhuibai);

        mDatas.add(R.mipmap.cebianlanbg);
        mDatas.add(R.mipmap.cebianlanbg);
        mDatas.add(R.mipmap.cebianlanbg);

        // 设置数据
        mzbCommodityDetails.setPages(mDatas, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
        //设置是否显式指示器
        mzbCommodityDetails.setIndicatorVisible(false);
        //设置BannerView 的切换时间间隔
        mzbCommodityDetails.setDelayedTime(5000);

        mDownloadPopupWindow = new PopupWindow(this);
        mDownloadPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mDownloadPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        mPopLayout = inflater.inflate(R.layout.popup_window_commodity_details_download, null);
        mDownloadPopupWindow.setContentView(mPopLayout);
        mDownloadPopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mDownloadPopupWindow.setOutsideTouchable(false);
        mDownloadPopupWindow.setFocusable(true);
        tvCommodityDetailsDownloadName = mPopLayout.findViewById(R.id.tv_commodity_details_download_name);
        btCommodityDetailsDownloadPreview = mPopLayout.findViewById(R.id.bt_commodity_details_download_preview);
        btCommodityDetailsDownloadPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btCommodityDetailsDownloadPreview.getText().toString().trim().equals(App.getContext().getResources().getString(R.string.download_preview))) {
                    startActivity(new Intent(CommodityDetailsActivity.this, PDFActivity.class));
                } else if (btCommodityDetailsDownloadPreview.getText().toString().trim().equals(App.getContext().getResources().getString(R.string.jump))) {

                } else if (btCommodityDetailsDownloadPreview.getText().toString().trim().equals(App.getContext().getResources().getString(R.string.i_know))) {
                    mDownloadPopupWindow.dismiss();
                }
            }
        });
    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_commodity_details,null);
            mImageView = (ImageView) view.findViewById(R.id.iv_banner_commodity_details);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mzbCommodityDetails.pause();//暂停轮播
    }

    @Override
    public void onResume() {
        super.onResume();
        mzbCommodityDetails.start();//开始轮播
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
                tvCommodityDetailsDownloadName.setText(App.getContext().getResources().getText(R.string.download_size_diagram));
                btCommodityDetailsDownloadPreview.setText(App.getContext().getResources().getString(R.string.download_preview));
                mDownloadPopupWindow.showAsDropDown(view, 0, 0);
                break;
            case R.id.iv_installation_instructions_download:
                tvCommodityDetailsDownloadName.setText(App.getContext().getResources().getText(R.string.download_installation_instructions));
                btCommodityDetailsDownloadPreview.setText(App.getContext().getResources().getString(R.string.download_preview));
                mDownloadPopupWindow.showAsDropDown(view, 0, 0);
                break;
            case R.id.ll_commodity_details_purchase_inquiries:
                startActivity(new Intent(this, StoreMapActivity.class));
                break;
            case R.id.bt_commodity_details_like:
                tvCommodityDetailsDownloadName.setText(App.getContext().getResources().getText(R.string.you_can_cancel));
                btCommodityDetailsDownloadPreview.setText(App.getContext().getResources().getString(R.string.i_know));
                mDownloadPopupWindow.showAsDropDown(view, 0, 0);
                break;
            case R.id.bt_commodity_details_pay:
                tvCommodityDetailsDownloadName.setText(App.getContext().getResources().getText(R.string.jump_tianmao));
                btCommodityDetailsDownloadPreview.setText(App.getContext().getResources().getString(R.string.jump));
                mDownloadPopupWindow.showAsDropDown(view, 0, 0);
                break;
        }
    }
}
