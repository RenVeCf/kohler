package com.mengyang.kohler.whole_category.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.IOUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.activity.DownLoaderPDFActivity;
import com.mengyang.kohler.home.activity.StoreMapActivity;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.CommodityClassificationFragmentBean;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    @BindView(R.id.tv_function)
    TextView tvFunction;
    @BindView(R.id.ll_commodity_details)
    LinearLayout llCommodityDetails;
    @BindView(R.id.tv_commodity_details_color)
    TextView tvCommodityDetailsColor;
    @BindView(R.id.iv_commodity_details_color_img)
    LinearLayout ivCommodityDetailsColorImg;

    //轮播图集合
    private List<Bitmap> mDatas = new ArrayList<>();
    private PopupWindow mDownloadPopupWindow;
    private View mPopLayout;
    private TextView tvCommodityDetailsDownloadName;
    private Button btCommodityDetailsDownloadPreview;
    private CommodityClassificationFragmentBean.ResultListBean mCommodityClassificationFragmentBean;
    private String mTianMaoUrl = "";
    private String mPdfUrl = "";

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
    }

    public void getLike() {

        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("skuCode", "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getAddLike(stringMap)
                .compose(this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                    }
                });
    }

    /**
     * URL to Bitmap
     *
     * @param url
     * @return
     */
    private Bitmap returnBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;
            int length = http.getContentLength();
            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            IOUtils.close(is);// 关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    public static class BannerViewHolder implements MZViewHolder<Bitmap> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_commodity_details, null);
            mImageView = (ImageView) view.findViewById(R.id.iv_banner_commodity_details);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Bitmap data) {
            LogUtils.i("rmy", "data = " + data);
            // 数据绑定
            mImageView.setImageBitmap(data);
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
        btCommodityDetailsDownloadPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btCommodityDetailsDownloadPreview.getText().toString().trim().equals(App.getContext().getResources().getString(R.string.download_preview))) {
                    startActivity(new Intent(CommodityDetailsActivity.this, DownLoaderPDFActivity.class).putExtra("PdfUrl", mPdfUrl));
                } else if (btCommodityDetailsDownloadPreview.getText().toString().trim().equals(App.getContext().getResources().getString(R.string.jump))) {
                    if (!mTianMaoUrl.equals("")) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(mTianMaoUrl);
                        intent.setData(content_url);
                        startActivity(intent);
                    }
                    mDownloadPopupWindow.dismiss();
                } else if (btCommodityDetailsDownloadPreview.getText().toString().trim().equals(App.getContext().getResources().getString(R.string.i_know))) {
                    mDownloadPopupWindow.dismiss();
                }
            }
        });
    }

    //    //同步get方式提交
    //    private void getRequest() {
    //        new Thread(new Runnable() {
    //            @Override
    //            public void run() {
    //                try {
    //                    final OkHttpClient client = new OkHttpClient();
    //                    String url = "http://s7d4.scene7.com/is/image/kohlerchina/232x200%2D1?$232x200$&$Gradient=kohlerchina%2Fgradient%20%2D%20232x200&$Shadow=kohlerchina%2FBlank%20%2D%202&defaultImage=defaultsquare4&$Product=is{kohlerchina%2FK-8423T-0_01}&$Badge=kohlerchina%2FBlank%20%2D%202";
    //                    Request request = new Request.Builder().url(url).build();
    //                    Response response = client.newCall(request).execute();
    //                    if (response.isSuccessful()) {
    //                    } else {
    //                    }
    //                } catch (IOException e) {
    //                    e.printStackTrace();
    //                }
    //            }
    //        }).start();
    //    }

    @Override
    protected void initData() {
        //        getRequest();
        mCommodityClassificationFragmentBean = (CommodityClassificationFragmentBean.ResultListBean) getIntent().getSerializableExtra("CommodityDetails");
        //        mDatas.add(R.mipmap.ic_launcher_round);
        //        mDatas.add(R.mipmap.ic_launcher_round);
        //        mDatas.add(R.mipmap.ic_launcher_round);
        //        mDatas.add(R.mipmap.ic_launcher_round);
        //        mDatas.add(R.mipmap.ic_launcher_round);
        if (!mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url1().equals("")) {
            mDatas.add(returnBitmap(mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url1()));
        }
        if (!mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url2().equals("")) {
            mDatas.add(returnBitmap(mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url2()));
        }
        if (!mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url3().equals("")) {
            mDatas.add(returnBitmap(mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url3()));
        }
        if (!mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url4().equals("")) {
            mDatas.add(returnBitmap(mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url4()));
        }
        if (!mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url5().equals("")) {
            mDatas.add(returnBitmap(mCommodityClassificationFragmentBean.getProDetail().getTempDetailImage1Url5()));
        }
        // 设置数据
        mzbCommodityDetails.setPages(mDatas, new MZHolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        StringBuffer sb = new StringBuffer();
        tvCommodityDetailsBrand.setText(mCommodityClassificationFragmentBean.getProDetail().getProductName());
        tvCommodityDetailsModel.setText(mCommodityClassificationFragmentBean.getProDetail().getSkuCode());
        for (int i = 0; i < mCommodityClassificationFragmentBean.getAttrList().size(); i++) {
            if (mCommodityClassificationFragmentBean.getAttrList().get(i).getCategoryComAttrName().equals("亮点")) {
                tvFunction.setText(mCommodityClassificationFragmentBean.getAttrList().get(i).getAttrValue());
            } else if (!mCommodityClassificationFragmentBean.getAttrList().get(i).getCategoryComAttrName().equals("TMALL链接") && !mCommodityClassificationFragmentBean.getAttrList().get(i).getCategoryComAttrName().equals("特征") && !mCommodityClassificationFragmentBean.getAttrList().get(i).getCategoryComAttrName().equals("pdfUrl")) {
                LinearLayout relative = new LinearLayout(this);
                relative.setOrientation(LinearLayout.HORIZONTAL);
                TextView label = new TextView(this);
                label.setText(mCommodityClassificationFragmentBean.getAttrList().get(i).getCategoryComAttrName() + ": ");
                label.setTextColor(Color.BLACK);
                label.setTextSize(11);
                label.setLineSpacing(7, 0);
                TextPaint tp = label.getPaint();
                tp.setFakeBoldText(true);
                relative.addView(label);

                TextView attribute = new TextView(this);
                attribute.setText(mCommodityClassificationFragmentBean.getAttrList().get(i).getAttrValue());
                attribute.setTextColor(Color.BLACK);
                attribute.setTextSize(11);
                attribute.setLineSpacing(7, 0);
                relative.addView(attribute);
                llCommodityDetails.addView(relative);
            } else if (mCommodityClassificationFragmentBean.getAttrList().get(i).getCategoryComAttrName().equals("TMALL链接")) {
                mTianMaoUrl = mCommodityClassificationFragmentBean.getAttrList().get(i).getAttrValue();
            }
        }
        for (int i = 0; i < mCommodityClassificationFragmentBean.getSkuAttrList().size(); i++) {
            if (mCommodityClassificationFragmentBean.getSkuAttrList().get(i).getCategorySkuAttrName().equals("颜色/表面处理工艺")) {
                sb.append(mCommodityClassificationFragmentBean.getSkuAttrList().get(i).getAttrValue() + ", ");
                ImageView label = new ImageView(this);
                Glide.with(App.getContext()).load(mCommodityClassificationFragmentBean.getSkuAttrList().get(i).getSkuImageName()).into(label);
                ivCommodityDetailsColorImg.addView(label);
            }
        }
        tvCommodityDetailsColor.setText(sb);
    }

    @OnClick({R.id.iv_size_diagram_download, R.id.iv_installation_instructions_download, R.id.ll_commodity_details_purchase_inquiries, R.id.bt_commodity_details_like, R.id.bt_commodity_details_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_size_diagram_download:
                for (int i = 0; i < mCommodityClassificationFragmentBean.getPdfList().size(); i++) {
                    if (mCommodityClassificationFragmentBean.getPdfList().get(i).getProductPdfName().equals("尺寸图")) {
                        mPdfUrl = mCommodityClassificationFragmentBean.getPdfList().get(i).getFileName();
                    }
                }
                tvCommodityDetailsDownloadName.setText(App.getContext().getResources().getText(R.string.download_size_diagram));
                btCommodityDetailsDownloadPreview.setText(App.getContext().getResources().getString(R.string.download_preview));
                mDownloadPopupWindow.showAsDropDown(view, 0, 0);
                break;
            case R.id.iv_installation_instructions_download:
                for (int i = 0; i < mCommodityClassificationFragmentBean.getPdfList().size(); i++) {
                    if (mCommodityClassificationFragmentBean.getPdfList().get(i).getProductPdfName().equals("安装说明书")) {
                        mPdfUrl = mCommodityClassificationFragmentBean.getPdfList().get(i).getFileName();
                    }
                }
                tvCommodityDetailsDownloadName.setText(App.getContext().getResources().getText(R.string.download_installation_instructions));
                btCommodityDetailsDownloadPreview.setText(App.getContext().getResources().getString(R.string.download_preview));
                mDownloadPopupWindow.showAsDropDown(view, 0, 0);
                break;
            case R.id.ll_commodity_details_purchase_inquiries:
                startActivity(new Intent(this, StoreMapActivity.class));
                break;
            case R.id.bt_commodity_details_like:
                getLike();
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
