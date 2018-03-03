package com.mengyang.kohler.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.kohler.arscan.DownloadActivity;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.LoginActivity;
import com.mengyang.kohler.common.activity.CustomerServiceActivity;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.FileUtil;
import com.mengyang.kohler.common.utils.FileUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.activity.DownLoaderPDFActivity;
import com.mengyang.kohler.home.activity.HomeSearchActivity;
import com.mengyang.kohler.home.activity.MeetingActivity;
import com.mengyang.kohler.home.activity.PDFActivity;
import com.mengyang.kohler.home.adapter.BrochureListAdapter2;
import com.mengyang.kohler.home.adapter.HomeBooksAdapter;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.PdfBean;
import com.mengyang.kohler.module.bean.BooksListBean;
import com.mengyang.kohler.module.bean.HomeIndexBean;
import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.ryane.banner.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner.AdPlayBanner.IndicatorType.NONE_INDICATOR;

/**
 * 主页
 */

public class HomeFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    String mRootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private List<String> mLocalTempPdfFileName = new ArrayList<>();


    @BindView(R.id.tv_home_top)
    TopView tvHomeTop;
    @BindView(R.id.et_home_search)
    EditText etHomeSearch;
    @BindView(R.id.ab_home_loop)
    AdPlayBanner abHomeLoop;
    @BindView(R.id.rv_home_video)
    RecyclerView rvHomeVideo;
    @BindView(R.id.rv_home_books)
    RecyclerView rvHomeBooks;
    @BindView(R.id.iv_top_menu)
    ImageView ivTopMenu;
    @BindView(R.id.iv_home_search)
    ImageView ivHomeSearch;
    @BindView(R.id.tv_my_brochure_top)
    TextView tvMyBrochureTop;
    @BindView(R.id.tv_my_brochure_donw)
    TextView tvMyBrochureDonw;
    @BindView(R.id.iv_top_customer_service)
    ImageView ivTopCustomerService;
    @BindView(R.id.ll_indicator)
    LinearLayout mLlIndicator;
    //侧滑Meun键的接口回调
    private OnFragmentInteractionListener mListener;
    private HomeIndexBean mHomeIndexBean;
    //轮播图集合
    private List<AdPageInfo> mDatas = new ArrayList<>();
    private HomeBooksAdapter mHomeBooksAdapter;
    private String mH5_URL = "";
    private int prevousPosition = 0;
    private PopupWindow mNoJurisdictionPopupWindow;
    private View mPopLayout;
    private PdfBean mPdfBean = new PdfBean();
    private String mUserName;
    private List<PdfBean.UserNameBean.UserPdfItemBean> mPdfItemList;
    private int pageNum;
    private List<BooksListBean.ResultListBean> mBooksListBean = new ArrayList<>();
    private BrochureListAdapter2 mMineManualAdapter;
    private int mCurPosition;
    private String mPdfTotalPath;
    private String mDownLoadKvUrl;
    private HandleViewPager mHandleListenning;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvHomeTop);

        //        if (SPUtil.get(App.getContext(), IConstants.TYPE, "").equals("dealer")) {
        //            ivTopCustomerService.setVisibility(View.VISIBLE);
        //        } else {
        ivTopCustomerService.setVisibility(View.GONE);
        //        }
        //轮播
        abHomeLoop.measure(0, 0);
        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvHomeBooks.setLayoutManager(layoutManager);
        rvHomeBooks.addItemDecoration(new SpacesItemDecoration(16));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvHomeBooks.setHasFixedSize(true);
        rvHomeBooks.setItemAnimator(new DefaultItemAnimator());

        if (SPUtil.get(getActivity(), IConstants.TYPE, "").equals("dealer") || SPUtil.get(getActivity(), IConstants.TYPE, "").equals("designer")) {
            rvHomeBooks.setVisibility(View.VISIBLE);
            tvMyBrochureTop.setVisibility(View.VISIBLE);
            tvMyBrochureDonw.setVisibility(View.VISIBLE);
        } else {
            rvHomeBooks.setVisibility(View.GONE);
            tvMyBrochureTop.setVisibility(View.GONE);
            tvMyBrochureDonw.setVisibility(View.GONE);
        }
        abHomeLoop.setImageViewScaleType(AdPlayBanner.ScaleType.CENTER_CROP);

        mNoJurisdictionPopupWindow = new PopupWindow(getActivity());
        mNoJurisdictionPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mNoJurisdictionPopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        mPopLayout = inflater.inflate(R.layout.popup_window_no_jurisdictuon, null);
        mNoJurisdictionPopupWindow.setContentView(mPopLayout);
        mNoJurisdictionPopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mNoJurisdictionPopupWindow.setOutsideTouchable(false);
        mNoJurisdictionPopupWindow.setFocusable(true);
        abHomeLoop.setImageViewScaleType(AdPlayBanner.ScaleType.CENTER_CROP);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (((boolean) SPUtil.get(getActivity(), IConstants.IS_LOGIN, false)) == true) {
            String userLevel = (String) SPUtil.get(getActivity(), IConstants.TYPE, "");
            if (userLevel.equals("dealer") || userLevel.equals("designer")) {
                final List<String> listFileName = FileUtil.judgePdfIsExit(mLocalTempPdfFileName);
                    // TODO: 2018/2/23 ,若是用户手动删除了手机上的文件，还没有做处理

                if (listFileName != null && listFileName.size() > 0) {

                    // TODO: 2018/3/2 ,请求网络获取PDF数据进行对比。

                    Map<String, String> stringMap = IdeaApi.getSign();
                    stringMap.put("pageNum", 0 + "");
                    IdeaApi.getRequestLogin(stringMap);
                    IdeaApi.getApiService()
                            .getBooksList(stringMap)
                            .compose(this.<BasicResponse<BooksListBean>>bindToLifecycle())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new DefaultObserver<BasicResponse<BooksListBean>>(getActivity(), true) {
                                @Override
                                public void onSuccess(BasicResponse<BooksListBean> response) {
                                    //KVurl = http://ojd06y9cv.bkt.clouddn.com/4969f7828af22e1ec8d30ecc9e7d2c22.png?/0/w/1280/h/960
                                    //pdfUrl = http://ojd06y9cv.bkt.clouddn.com/619820641c5890217b99e5cc968e526c.pdf
                                    String substring = "";
                                    mBooksListBean.clear();

                                    if (response != null) {
                                        for (int i = 0; i < response.getData().getResultList().size(); i++) {
                                            String pdfUrl = response.getData().getResultList().get(i).getPdfUrl();

                                            if (pdfUrl != null && !TextUtils.isEmpty(pdfUrl)) {

                                                substring = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
                                                if (listFileName.contains(substring)) {
                                                    mBooksListBean.add(response.getData().getResultList().get(i));
                                                    //添加到bean里面
                                                }
                                            }
                                            //之后添加到bean放到条目展示
                                        }

                                        if (mMineManualAdapter == null) {
                                            mMineManualAdapter = new BrochureListAdapter2(mBooksListBean);
                                            rvHomeBooks.setAdapter(mMineManualAdapter);
                                        } else {
                                            mMineManualAdapter.notifyDataSetChanged();
                                        }

                                        final String finalSubstring = substring;
                                        mMineManualAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                            @Override
                                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                                mCurPosition = position;
                                                //判断是否这个pdf文件已存在
                                                mPdfTotalPath = mRootPath + "/" + finalSubstring;
                                                if (FileUtils.isFileExist(mPdfTotalPath)) {
                                                    startActivity(new Intent(getActivity(), PDFActivity.class).putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl()));
                                                }

                                            }
                                        });


                                    }
                                    listFileName.clear();
                                    mLocalTempPdfFileName.clear();
                                }
                            });
                } else {
                    //直接进行获取展示
                    updateUI();
                }
            }
        }
    }

    /**
     * 显示
     */
    private void updateUI() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("pageNum", 0 + "");

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getBooksList(stringMap)
                .compose(this.<BasicResponse<BooksListBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<BooksListBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BasicResponse<BooksListBean> response) {
                        if (response != null) {
                                mBooksListBean.clear();
                                mBooksListBean.addAll(response.getData().getResultList());
                                if (mBooksListBean.size() > 0) {
                                    pageNum += 1;

                                    if (mMineManualAdapter == null) {
                                        mMineManualAdapter = new BrochureListAdapter2(mBooksListBean);
                                        rvHomeBooks.setAdapter(mMineManualAdapter);
                                    } else {
                                        mMineManualAdapter.notifyDataSetChanged();
                                    }

                                    mMineManualAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                        @Override
                                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                            mCurPosition = position;
                                            //判断是否这个pdf文件已存在
                                            mPdfTotalPath = mRootPath + "/" + mBooksListBean.get(position).getPdfUrl().substring(mBooksListBean.get(position).getPdfUrl().lastIndexOf("/") + 1);
                                            if (FileUtils.isFileExist(mPdfTotalPath)) {
                                                startActivity(new Intent(getActivity(), PDFActivity.class).putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl()));
                                            } else {//没找到就去下载
                                                mDownLoadKvUrl = mBooksListBean.get(mCurPosition).getKvUrl();
                                                // TODO: 2018/2/11 ,还需要考虑到断点续传的功能,若是客户在下载的过程中退出应用，下次在进来的时候，PDF虽然有了，但是不能显示

                                                String pdfUrl = mBooksListBean.get(position).getPdfUrl();
                                                if (pdfUrl != null && !TextUtils.isEmpty(pdfUrl)) {
                                                    Intent intent = new Intent(getActivity(), DownLoaderPDFActivity.class);
                                                    intent.putExtra("PdfUrl", mBooksListBean.get(position).getPdfUrl());
                                                    intent.putExtra("mPdfTotalPath", mPdfTotalPath);
                                                    intent.putExtra("mDownLoadKvUrl", mDownLoadKvUrl);
                                                    startActivityForResult(intent, IConstants.REQUEST_CODE_DOWN_LOAD);
                                                    return;
                                                } else {
                                                    String videoUrl = mBooksListBean.get(position).getVideoUrl();
                                                    if (videoUrl != null && !TextUtils.isEmpty(videoUrl)) {
                                                        startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("h5url", videoUrl).putExtra("flag", 2));
                                                    }
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    mMineManualAdapter.loadMoreEnd();
                                }
                        } else {
                            mMineManualAdapter.loadMoreEnd();
                        }
                    }
                });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserHomeKV(stringMap)
                .compose(this.<BasicResponse<HomeIndexBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<HomeIndexBean>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BasicResponse<HomeIndexBean> response) {
                        etHomeSearch.setFocusable(true);
                        etHomeSearch.setFocusableInTouchMode(true);

                        mHomeIndexBean = response.getData();

                        for (int i = 0; i < mHomeIndexBean.getKvList().size(); i++) {
                            ImageView point = new ImageView(getActivity());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(75, 9);

                            if (i != 0) {
                                layoutParams.leftMargin = 6;//layout_marginleft= 6
                                point.setEnabled(false);
                            }

                            point.setLayoutParams(layoutParams);
                            point.setBackgroundColor(Color.parseColor("#e3e3e3"));
                            mLlIndicator.addView(point);

                        }

                        for (int i = 0; i < mHomeIndexBean.getKvSize(); i++) {
                            AdPageInfo info = new AdPageInfo("", mHomeIndexBean.getKvList().get(i).getKvUrl(), "", i + 1);
                            mDatas.add(info);
                        }
                        abHomeLoop.setImageLoadType(GLIDE);
                        abHomeLoop.setOnPageClickListener(new AdPlayBanner.OnPageClickListener() {
                            @Override
                            public void onPageClick(AdPageInfo info, int postion) {
                                if (postion == 0 && SPUtil.get(getActivity(), IConstants.TYPE, "").equals("dealer")) {
                                    startActivity(new Intent(getActivity(), MeetingActivity.class));
                                } else if (postion == 0 && SPUtil.get(getActivity(), IConstants.TYPE, "").equals("commonUser")) {
                                    if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                                        mNoJurisdictionPopupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                                    } else {
                                        mNoJurisdictionPopupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, 0, 0);
                                    }
                                } else if (postion == 0 && SPUtil.get(getActivity(), IConstants.TYPE, "").equals("designer")) {
                                    if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                                        mNoJurisdictionPopupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                                    } else {
                                        mNoJurisdictionPopupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, 0, 0);
                                    }
                                } else {
                                    if (postion == 0) {
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                    } else if (postion == 1) {
                                        Intent intent = new Intent(getActivity(), DownloadActivity.class);
                                        intent.putExtra("way", "banner");
                                        startActivity(intent);
                                    } else {
                                        if (mHomeIndexBean.getKvList().get(postion).getClickRedirect() != null && !mHomeIndexBean.getKvList().get(postion).getClickRedirect().equals("")) {
                                            mH5_URL = mHomeIndexBean.getKvList().get(postion).getClickRedirect() + "";
                                        } else if (mHomeIndexBean.getKvList().get(postion).getH5Url() != null && !mHomeIndexBean.getKvList().get(postion).getH5Url().equals("")) {
                                            mH5_URL = mHomeIndexBean.getKvList().get(postion).getH5Url() + "";
                                        }
                                        startActivity(new Intent(getActivity(), WebViewActivity.class).putExtra("h5url", mH5_URL));
                                    }
                                }
                            }
                        });
                        //自动滚动
                        abHomeLoop.setAutoPlay(true);
                        //页码指示器
                        abHomeLoop.setIndicatorType(NONE_INDICATOR);
                        //间隔时间
                        abHomeLoop.setInterval(3000);
                        //背景
                        abHomeLoop.setBannerBackground(0xffffffff);
                        //滑动监听
                        abHomeLoop.setOnPagerChangeListener(new AdPlayBanner.OnPagerChangeListener() {
                            @Override
                            public void onPageSelected(int position) {
                                //当前页面选中时，先将上一次选中的位置设置为未选中，并将当前的位置记录下来为下一次移动做准备
                                //                                mLlIndicator.getChildAt(prevousPosition).setEnabled(false);
                                if (mLlIndicator != null) {
                                    if (mLlIndicator.getChildAt(prevousPosition) != null) {
                                        mLlIndicator.getChildAt(prevousPosition).setBackgroundColor(Color.parseColor("#e3e3e3"));
                                        prevousPosition = position;
                                    }
                                    //要让对应角标的小圆点选中
                                    View childAt = mLlIndicator.getChildAt(position);
                                    //                                childAt.setEnabled(true);
                                    childAt.setBackgroundColor(Color.BLACK);
                                }
                            }

                            @Override
                            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            }

                            @Override
                            public void onPageScrollStateChanged(int state) {
                            }
                        });
                        //数据源
                        abHomeLoop.setInfoList((ArrayList<AdPageInfo>) mDatas);
                        abHomeLoop.setUp();
                    }
                });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onLoadMoreRequested() {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String data);
    }

    @OnClick({R.id.iv_top_menu, R.id.iv_home_search, R.id.iv_top_customer_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_home_search:
                if (!etHomeSearch.getText().toString().trim().equals("")) {
                    startActivity(new Intent(getActivity(), HomeSearchActivity.class).putExtra("etHomeSearch", etHomeSearch.getText().toString().trim()));
                }
                break;
            case R.id.iv_top_menu:
                mListener.onFragmentInteraction("");
                break;
            case R.id.iv_top_customer_service:
                startActivity(new Intent(getContext(), CustomerServiceActivity.class));
                break;
            default:
                break;
        }
    }

    public void stopViewPager() {
        if (abHomeLoop != null) {
            abHomeLoop.setAutoPlay(false);
        }
    }

    public void startViewPager() {
        if (abHomeLoop != null) {
            abHomeLoop.setAutoPlay(true);
            abHomeLoop.setUp();
            Log.i("123", "456");
        }
    }

    interface HandleViewPager {
        void handleListenning();
    }

    public void setHandleListenning(HandleViewPager handleListenning) {
        mHandleListenning = handleListenning;
    }

}
