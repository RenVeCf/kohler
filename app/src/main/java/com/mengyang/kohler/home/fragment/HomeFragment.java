package com.mengyang.kohler.home.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.allyes.analytics.AIOAnalytics;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.deepano.kohlortest.UnityPlayerActivity;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.CustomerServiceActivity;
import com.mengyang.kohler.common.activity.WebViewActivity;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.net.IdeaApiService;
import com.mengyang.kohler.common.utils.DateUtils;
import com.mengyang.kohler.common.utils.FileUtil;
import com.mengyang.kohler.common.utils.FileUtils;
import com.mengyang.kohler.common.utils.JsonUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.utils.VerifyUtils;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.activity.ArtKohlerActivity;
import com.mengyang.kohler.home.activity.DownLoaderPDFActivity;
import com.mengyang.kohler.home.activity.GanChuangActivity;
import com.mengyang.kohler.home.activity.HiKohlerActivity;
import com.mengyang.kohler.home.activity.HomeSearchActivity;
import com.mengyang.kohler.home.activity.KbisActivity;
import com.mengyang.kohler.home.activity.PDFActivity;
import com.mengyang.kohler.home.activity.WeeklyRadioConcertActivity;
import com.mengyang.kohler.home.adapter.BrochureListAdapter2;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.BooksListBean;
import com.mengyang.kohler.module.bean.HomeIndexBean;
import com.ryane.banner.AdPageInfo;
import com.ryane.banner.AdPlayBanner;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.ryane.banner.AdPlayBanner.ImageLoaderType.GLIDE;
import static com.ryane.banner.AdPlayBanner.IndicatorType.NONE_INDICATOR;

/**
 * 主页
 */

public class HomeFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener, View.OnClickListener {

    @BindView(R.id.tv_home_top)
    TopView tvHomeTop;
    @BindView(R.id.iv_top_system_msg)
    ImageView ivTopSystemMsg;
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
    @BindView(R.id.iv_weekly_radio_concert)
    ImageView ivWeeklyRadioConcert;
    @BindView(R.id.iv_home_appointment)
    ImageView ivHomeAppointment;

    /**
     * 预约 Dialog
     */
    private TextView tvAppointmentHelp;
    private TextView tvAppointmentProductBig;
    private TextView tvAppointmentProductMedium;
    private TextView tvAppointmentProductSmall;
    private TextView tvAppointmentProductProvince;
    private TextView tvAppointmentProductCity;
    private TextView tvAppointmentProductAddrKey;
    private TextView tvAppointmentAddress;
    private EditText etAppointmentName;
    private RadioButton rbAppointmentBoy;
    private RadioButton rbAppointmentGirl;
    private RadioGroup rgAppointment;
    private TextView tvAppointmentFamily;
    private TextView tvAppointmentInStoreTime;
    private EditText etAppointmentPhoneNum;
    private Button btAppointmentCommit;
    private TimePickerView pvTime;
    private Dialog dialog;
    private Dialog dialog_one;
    private OkHttpClient okHttpClient;
    private String url = "http://www.kohler.com.cn/chinaweb/book/add.action";
    private String gender = "男";
    private List<String> mOptionsItems = new ArrayList<>();
    private TextView tvSure;
    private TextView tvCancel;
    private TextView tvCenterTitle;

    //侧滑Meun键的接口回调
    private OnFragmentInteractionListener mListener;
    private HomeIndexBean mHomeIndexBean;
    private HandleViewPager mHandleListenning;
    private PopupWindow mNoJurisdictionPopupWindow;
    private View mPopLayout;
    private BrochureListAdapter2 mMineManualAdapter;
    private LinearLayout mLlPopupWindowNoJurisdictuon;

    //轮播图集合
    private List<AdPageInfo> mDatas = new ArrayList<>();
    private List<BooksListBean.ResultListBean> mBooksListBean = new ArrayList<>();
    private List<String> mLocalTempPdfFileName = new ArrayList<>();

    private int mCurPosition;
    private int prevousPosition = 0;
    private int pageNum;

    private String mPdfTotalPath;
    private String mDownLoadKvUrl;
    private String mH5_URL = "";

    private boolean mIsOpen;

    List<String> mProvinceList = new ArrayList<>();
    List<String> mBathroomList = new ArrayList<>();
    Map<String, String> mSecondMap = new HashMap<>();
    Map<String, String> mThirdMap = new HashMap<>();
    Map<String, String> mCityMap = new HashMap<>();
    Map<String, String> mRoomNameMap = new HashMap<>();
    Map<String, String> mAddressMap = new HashMap<>();

    private String mSelectedText = "";
    private String mSelectedProvince = "";
    private String mSelectedCity = "";
    private String mSelectedRoomName = "";

    private String mSelectedType = "";
    private String mSelectedSecond = "";
    private String mSelectedThird = "";

    private boolean mIsShowDeatilAddress;
    private String mJson;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initValues() {
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(getActivity(), tvHomeTop);
        MobclickAgent.onEvent(getActivity(), "index");
        //        if (SPUtil.get(App.getContext(), IConstants.TYPE, "").equals("dealer")) {
        ivTopCustomerService.setVisibility(View.VISIBLE);
        //        } else {
        //        ivTopCustomerService.setVisibility(View.GONE);
        //        }

        //轮播
        abHomeLoop.measure(0, 0);
        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
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
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        mPopLayout = inflater.inflate(R.layout.popup_window_no_jurisdictuon, null);
        mNoJurisdictionPopupWindow.setContentView(mPopLayout);
        mNoJurisdictionPopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mNoJurisdictionPopupWindow.setOutsideTouchable(false);
        mNoJurisdictionPopupWindow.setFocusable(true);
        abHomeLoop.setImageViewScaleType(AdPlayBanner.ScaleType.CENTER_CROP);
        mLlPopupWindowNoJurisdictuon = mPopLayout.findViewById(R.id.ll_pop_no_jurisdictuon);
        mLlPopupWindowNoJurisdictuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNoJurisdictionPopupWindow.dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        AIOAnalytics.onPageBegin("index");
        if (((boolean) SPUtil.get(getActivity(), IConstants.IS_LOGIN, false)) == true) {
            String userLevel = (String) SPUtil.get(getActivity(), IConstants.TYPE, "");
            if (userLevel.equals("dealer") || userLevel.equals("designer")) {
                final List<String> listFileName = FileUtil.judgePdfIsExit(mLocalTempPdfFileName);
                // TODO: 2018/2/23 ,若是用户手动删除了手机上的文件，还没有做处理

                if (listFileName != null && listFileName.size() > 0) {

                    // TODO: 2018/3/2 ,请求网络获取PDF数据进行对比。

                    Map<String, Object> stringMap = IdeaApi.getSign();
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
                                                mPdfTotalPath = IConstants.ROOT_PATH + "/" + finalSubstring;
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

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
        AIOAnalytics.onPageEnd("index");
    }

    /**
     * 显示
     */
    private void updateUI() {
        Map<String, Object> stringMap = IdeaApi.getSign();
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
                                        mPdfTotalPath = IConstants.ROOT_PATH + "/" + mBooksListBean.get(position).getPdfUrl().substring(mBooksListBean.get(position).getPdfUrl().lastIndexOf("/") + 1);
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
                                                startActivity(intent);
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
                                mMineManualAdapter.loadMoreEnd(true);
                            }
                        } else {
                            mMineManualAdapter.loadMoreEnd(true);
                        }
                    }
                });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, Object> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserHomeKV(stringMap)
                .compose(this.<BasicResponse<HomeIndexBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<HomeIndexBean>>(getActivity(), false) {
                    @Override
                    public void onSuccess(BasicResponse<HomeIndexBean> response) {
                        etHomeSearch.setFocusableInTouchMode(true);
                        mHomeIndexBean = response.getData();
                        boolean noRead = mHomeIndexBean.isNoRead();
                        if (noRead)
                            ivTopSystemMsg.setImageResource(R.mipmap.message_new);
                        for (int i = 0; i < mHomeIndexBean.getKvList().size(); i++) {
                            ImageView point = new ImageView(getActivity());
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(75, 9);

                            if (i != 0) {
                                layoutParams.leftMargin = 6;
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

                                if (mHandleListenning != null) {
                                    mIsOpen = mHandleListenning.handleListenning();
                                    if (mIsOpen) {
                                        return;
                                    }
                                }

                                if (postion == 0) {
                                    AIOAnalytics.onEvent("trade_show");
                                    startActivity(new Intent(getActivity(), KbisActivity.class));
                                } else if (postion == 1) {
                                    AIOAnalytics.onEvent("ganchuang");
                                    startActivity(new Intent(getActivity(), GanChuangActivity.class));
                                } else if (postion == 2) {
                                    AIOAnalytics.onEvent("shejishanghai");
                                    startActivity(new Intent(getActivity(), ArtKohlerActivity.class));
                                    // 暂去掉经销商大会
                                    //                                } else if (postion == 3 && SPUtil.get(getActivity(), IConstants.TYPE, "").equals("")) {
                                    //                                    AIOAnalytics.onEvent("jingxiaoshangdahui");
                                    //                                    startActivity(new Intent(getActivity(), LoginActivity.class));
                                    //                                } else if (postion == 3 && SPUtil.get(getActivity(), IConstants.TYPE, "").equals("dealer")) {
                                    //                                    AIOAnalytics.onEvent("jingxiaoshangdahui");
                                    //                                    startActivity(new Intent(getActivity(), MeetingActivity.class));
                                    //                                } else if (postion == 3 && !SPUtil.get(getActivity(), IConstants.TYPE, "").equals("dealer")) {
                                    //                                    AIOAnalytics.onEvent("jingxiaoshangdahui");
                                    //                                    if (Build.VERSION.SDK_INT == 24) {//android7.0需要单独做适配
                                    //                                        mNoJurisdictionPopupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, 0, getStatusBarHeight());
                                    //                                    } else {
                                    //                                        mNoJurisdictionPopupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, 0, 0);
                                    //                                    }
                                } else if (!mHomeIndexBean.getKvList().get(3).getH5Url().equals("") && postion == 3) {
                                    startActivity(new Intent(getActivity(), HiKohlerActivity.class).putExtra("h5url", mHomeIndexBean.getKvList().get(3).getH5Url()));
                                } else {
                                    if (postion == 4) {
                                        MobclickAgent.onEvent(getActivity(), "arjieshuo");
                                        AIOAnalytics.onEvent("arjieshuo");
                                        Intent intent = new Intent(getActivity(), UnityPlayerActivity.class);
                                        intent.putExtra("flag", "9");
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
                                //mLlIndicator.getChildAt(prevousPosition).setEnabled(false);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_appointment_help:
                mOptionsItems.clear();
                mOptionsItems.add("商品咨询");
                mOptionsItems.add("交货时间");
                mOptionsItems.add("工程安排及进度");
                mOptionsItems.add("其他");
                selectOne(tvAppointmentHelp, mOptionsItems, 0, "帮助");
                break;
            case R.id.tv_appointment_product_big:
                selectOne(tvAppointmentProductBig, mBathroomList, 4, "一级分类");

                tvAppointmentProductMedium.setText("请选择");
                tvAppointmentProductSmall.setText("请选择");
                break;
            case R.id.tv_appointment_product_medium:
                showSecond(true);

                tvAppointmentProductSmall.setText("请选择");
                break;
            case R.id.tv_appointment_product_small:
                showThird(true);
                break;
            case R.id.tv_appointment_product_province:
                selectOne(tvAppointmentProductProvince, mProvinceList, 1, "省");

                tvAppointmentProductCity.setText("请选择");
                tvAppointmentProductAddrKey.setText("请选择");
                tvAppointmentAddress.setText("");
                break;
            case R.id.tv_appointment_product_city:
                showCity(true);

                tvAppointmentProductAddrKey.setText("请选择");
                tvAppointmentAddress.setText("");
                break;
            case R.id.tv_appointment_product_addr_key:
                mIsShowDeatilAddress = true;
                showRoomName(true);
                tvAppointmentAddress.setText("");
                break;
            case R.id.tv_appointment_family:
                mOptionsItems.clear();
                mOptionsItems.add("一家两口");
                mOptionsItems.add("一家三口");
                mOptionsItems.add("三世同堂");
                mOptionsItems.add("其他");
                selectOne(tvAppointmentFamily, mOptionsItems, 0, "家庭状况");
                break;
            case R.id.tv_appointment_in_store_time:
                selectTime();
                break;
            case R.id.bt_appointment_commit:
                AIOAnalytics.onEvent("appoint");
                MobclickAgent.onEvent(getActivity(), "appoint");
                MobclickAgent.onResume(getActivity());
                AIOAnalytics.onPageBegin("appoint");
                appointmentCommit();
                break;
            default:
                break;
        }
    }

    private void showRoomName(boolean isShowDia) {
        List<String> List2 = new ArrayList<>();
        for (Map.Entry<String, String> entry : mRoomNameMap.entrySet()) {
            if (entry.getKey().contains(mSelectedCity)) {
                String value = entry.getValue();
                if (!List2.contains(value)) {
                    List2.add(value);
                }
            }
        }

        selectOne(tvAppointmentProductAddrKey, List2, 3, "门店");
    }

    private void showCity(boolean isShowDia) {
        List<String> List = new ArrayList<>();
        for (Map.Entry<String, String> entry : mCityMap.entrySet()) {
            if (entry.getKey().contains(mSelectedProvince)) {
                String value = entry.getValue();
                if (!List.contains(value)) {
                    List.add(value);
                }
            }
        }

        selectOne(tvAppointmentProductCity, List, 2, "市");
    }

    private void showThird(boolean isShowDia) {
        List<String> List4 = new ArrayList<>();
        for (Map.Entry<String, String> entry : mThirdMap.entrySet()) {
            if (entry.getKey().contains(mSelectedSecond)) {
                String value = entry.getValue();
                if (!List4.contains(value)) {
                    List4.add(value);
                }
            }
        }

        selectOne(tvAppointmentProductSmall, List4, 6, "三级分类");
    }

    private void showSecond(boolean isShowDia) {
        List<String> List3 = new ArrayList<>();
        for (Map.Entry<String, String> entry : mSecondMap.entrySet()) {
            if (entry.getKey().contains(mSelectedType)) {
                String value = entry.getValue();
                if (!List3.contains(value)) {
                    List3.add(value);
                }
            }
        }

        selectOne(tvAppointmentProductMedium, List3, 5, "二级分类");
    }

    /**
     * 单选选择器样式
     *
     * @param view
     * @param item
     */
    private void selectOne(final TextView view, final List<String> item, final int type, String content) {
        if (item == null || item.size() < 1) {
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = LayoutInflater.from(getActivity());
        View v = inflater1.inflate(R.layout.appointment_one, null);
        tvSure = v.findViewById(R.id.tv_sure);
        tvCancel = v.findViewById(R.id.tv_cancel);
        tvCenterTitle = v.findViewById(R.id.tv_center_title);
        tvCenterTitle.setText(content);
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelectedText = item.get(0);
                view.setText(mSelectedText);
                dialog_one.dismiss();

                handleClickEvent(type);
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_one.dismiss();
            }
        });
        WheelView wheelView = v.findViewById(R.id.wv_appointment_one);

        wheelView.setCyclic(false);

        wheelView.setAdapter(new ArrayWheelAdapter(item));
        wheelView.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final int index) {
                tvSure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSelectedText = item.get(index);
                        view.setText(mSelectedText);
                        dialog_one.dismiss();

                        handleClickEvent(type);
                    }
                });
                tvCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_one.dismiss();
                    }
                });
            }
        });

        dialog_one = builder.create();
        dialog_one.setCanceledOnTouchOutside(false);
        dialog_one.show();
        dialog_one.getWindow().setContentView(v);
        dialog_one.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

        switch (view.getId()) {
            case R.id.tv_appointment_product_addr_key:
                tvAppointmentProductAddrKey.setSingleLine();
                tvAppointmentProductAddrKey.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
            case R.id.tv_appointment_product_big:
                tvAppointmentProductBig.setSingleLine();
                tvAppointmentProductBig.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
            case R.id.tv_appointment_product_medium:
                tvAppointmentProductMedium.setSingleLine();
                tvAppointmentProductMedium.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
            case R.id.tv_appointment_product_small:
                tvAppointmentProductSmall.setSingleLine();
                tvAppointmentProductSmall.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                break;
        }
    }

    private void handleClickEvent(int type) {
        if (type == 1) {
            mSelectedProvince = mSelectedText;
        } else if (type == 2) {
            mSelectedCity = mSelectedText;
        } else if (type == 3) {
            mSelectedRoomName = mSelectedText;
        } else if (type == 4) {
            mSelectedType = mSelectedText;
        } else if (type == 5) {
            mSelectedSecond = mSelectedText;
        } else if (type == 6) {
            mSelectedThird = mSelectedText;
        }

        if (mIsShowDeatilAddress) {
            mIsShowDeatilAddress = false;
            List<String> List2 = new ArrayList<>();
            for (Map.Entry<String, String> entry : mAddressMap.entrySet()) {
                if (entry.getKey().contains(mSelectedRoomName)) {
                    String value = entry.getValue();
                    if (!List2.contains(value)) {
                        List2.add(value);
                    }
                }
            }

            if (List2.size() > 0) {
                tvAppointmentAddress.setText(List2.get(0));
            }
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String data);
    }

    @OnClick({R.id.iv_top_menu, R.id.iv_home_search, R.id.iv_top_customer_service, R.id.tv_home_top, R.id.iv_weekly_radio_concert, R.id.iv_home_appointment})
    public void onViewClicked(View view) {
        if (getActivity().getCurrentFocus() != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) getActivity()).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }

        switch (view.getId()) {
            case R.id.iv_home_search:
                if (!etHomeSearch.getText().toString().trim().equals("")) {
                    startActivity(new Intent(getActivity(), HomeSearchActivity.class).putExtra("etHomeSearch", etHomeSearch.getText().toString().trim()));
                }
                break;
            case R.id.tv_home_top:
                mListener.onFragmentInteraction("topView");
                break;
            case R.id.iv_top_menu:
                mListener.onFragmentInteraction("");
                break;
            case R.id.iv_top_customer_service:
                startActivity(new Intent(getContext(), CustomerServiceActivity.class));
                break;
            case R.id.iv_weekly_radio_concert:
                AIOAnalytics.onEvent("yinyuehuiliebiao");
                startActivity(new Intent(getContext(), WeeklyRadioConcertActivity.class));
                break;
            case R.id.iv_home_appointment:
                AppointmentDialogInit();
                break;
            default:
                break;
        }
    }

    private void AppointmentDialogInit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater1 = LayoutInflater.from(getActivity());
        View v = inflater1.inflate(R.layout.activity_appointment, null);
        tvAppointmentHelp = v.findViewById(R.id.tv_appointment_help);
        tvAppointmentProductBig = v.findViewById(R.id.tv_appointment_product_big);
        tvAppointmentProductMedium = v.findViewById(R.id.tv_appointment_product_medium);
        tvAppointmentProductSmall = v.findViewById(R.id.tv_appointment_product_small);
        tvAppointmentProductProvince = v.findViewById(R.id.tv_appointment_product_province);
        tvAppointmentProductCity = v.findViewById(R.id.tv_appointment_product_city);
        tvAppointmentProductAddrKey = v.findViewById(R.id.tv_appointment_product_addr_key);
        tvAppointmentAddress = v.findViewById(R.id.tv_appointment_address);
        etAppointmentName = v.findViewById(R.id.et_appointment_name);
        rbAppointmentBoy = v.findViewById(R.id.rb_appointment_boy);
        rbAppointmentGirl = v.findViewById(R.id.rb_appointment_girl);
        rgAppointment = v.findViewById(R.id.rg_appointment);
        rgAppointment.check(rbAppointmentBoy.getId());
        tvAppointmentFamily = v.findViewById(R.id.tv_appointment_family);
        tvAppointmentInStoreTime = v.findViewById(R.id.tv_appointment_in_store_time);
        etAppointmentPhoneNum = v.findViewById(R.id.et_appointment_phone_num);
        btAppointmentCommit = v.findViewById(R.id.bt_appointment_commit);

        tvAppointmentHelp.setOnClickListener(this);
        tvAppointmentProductBig.setOnClickListener(this);
        tvAppointmentProductMedium.setOnClickListener(this);
        tvAppointmentProductSmall.setOnClickListener(this);
        tvAppointmentProductProvince.setOnClickListener(this);
        tvAppointmentProductCity.setOnClickListener(this);
        tvAppointmentProductAddrKey.setOnClickListener(this);
        tvAppointmentFamily.setOnClickListener(this);
        tvAppointmentInStoreTime.setOnClickListener(this);
        btAppointmentCommit.setOnClickListener(this);

        dialog = builder.create();
        dialog.show();
        dialog.getWindow().setContentView(v);//自定义布局应该在这里添加，要在dialog.show()的后面
        //清除flags,获取焦点(为了让EditText可以输入)
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        //弹出输入法
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.getWindow().setGravity(Gravity.CENTER);//可以设置显示的位置

        initJsonData();
        parseAssetsJson();

        requestAddressData();
    }

    private void requestAddressData() {
        OkHttpClient okHttpClient = new OkHttpClient();
        String url2 = "http://www.kohler.com.cn/js/exports_2.json";
        Request request = new Request.Builder().url(url2).build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();

                try {
                    JSONArray jsonArray = new JSONArray(string);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        String province = jsonArray.getJSONObject(i).getString("province");
                        if (!mProvinceList.contains(province)) {
                            mProvinceList.add(province);
                        }

                        String city = jsonArray.getJSONObject(i).getString("city");
                        String roomname = jsonArray.getJSONObject(i).getString("roomname");
                        String address = jsonArray.getJSONObject(i).getString("address");

                        if (mCityMap.containsKey(province)) {
                            mCityMap.put(province + i, city);
                        } else {
                            mCityMap.put(province, city);
                        }

                        if (mRoomNameMap.containsKey(city)) {
                            mRoomNameMap.put(city + i, roomname);
                        } else {
                            mRoomNameMap.put(city, roomname);
                        }

                        if (mAddressMap.containsKey(roomname)) {
                            mAddressMap.put(roomname + i, address);
                        } else {
                            mAddressMap.put(roomname, address);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseAssetsJson() {
        try {
            JSONArray jsonArray = new JSONArray(mJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                String type = jsonArray.getJSONObject(i).getString("type");
                String second = jsonArray.getJSONObject(i).getString("second");
                String third = jsonArray.getJSONObject(i).getString("third");

                if (!mBathroomList.contains(type)) {
                    mBathroomList.add(type);
                }

                if (mSecondMap.containsKey(type)) {
                    mSecondMap.put(type + i, second);
                } else {
                    mSecondMap.put(type, second);
                }

                if (mThirdMap.containsKey(second)) {
                    mThirdMap.put(second + i, third);
                } else {
                    mThirdMap.put(second, third);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 时间选择器样式
     */
    private void selectTime() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013, 0, 1);
        endDate.set(2020, 11, 31);

        pvTime = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                tvAppointmentInStoreTime.setText(DateUtils.timedate(date.getTime() + ""));
            }
        })
                .setType(new boolean[]{true, true, true, true, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                //                .setContentSize(18)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("时间")
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.BLACK)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setTitleBgColor(0xFFFFFFFF)//标题背景颜色 Night mode
                .setBgColor(0xFFFFFFFF)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    private void appointmentCommit() {
        okHttpClient = new OkHttpClient.Builder().connectTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).writeTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).readTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS).build();
        rgAppointment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (rgAppointment.getCheckedRadioButtonId()) {
                    case R.id.rb_appointment_boy:
                        gender = rbAppointmentBoy.getText().toString().trim();
                        break;
                    case R.id.rb_appointment_girl:
                        gender = rbAppointmentGirl.getText().toString().trim();
                        break;
                }
            }
        });

        //提交信息不能为空
        if (tvAppointmentProductProvince.getText().toString().trim().equals("请选择") || tvAppointmentProductCity.getText().toString().trim().equals("请选择") || tvAppointmentProductAddrKey.getText().toString().trim().equals("请选择") || tvAppointmentProductBig.getText().toString().trim().equals("请选择") || tvAppointmentProductMedium.getText().toString().trim().equals("请选择") || tvAppointmentProductSmall.getText().toString().trim().equals("请选择") || tvAppointmentInStoreTime.getText().toString().trim().equals("请选择") || etAppointmentPhoneNum.getText().toString().trim().equals("")) {
            ToastUtil.showToast("请将信息填写完整!");
        } else if (VerifyUtils.isMobileNumber(etAppointmentPhoneNum.getText().toString().trim()) == false) {
            ToastUtil.showToast("请填写正确的电话号码!");
        } else {
            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
            HashMap<String, String> map = new HashMap<>();
            map.put("help", tvAppointmentHelp.getText().toString().trim());
            map.put("province", tvAppointmentProductProvince.getText().toString().trim());
            map.put("city", tvAppointmentProductCity.getText().toString().trim());
            map.put("addr_key", tvAppointmentProductAddrKey.getText().toString().trim());
            map.put("address", tvAppointmentAddress.getText().toString().trim());
            map.put("goods1", tvAppointmentProductBig.getText().toString().trim());
            map.put("goods2", tvAppointmentProductMedium.getText().toString().trim());
            map.put("goods3", tvAppointmentProductSmall.getText().toString().trim());
            map.put("family", tvAppointmentFamily.getText().toString().trim());
            map.put("gender", gender);
            map.put("name", etAppointmentName.getText().toString().trim());
            map.put("time", tvAppointmentInStoreTime.getText().toString().trim());
            map.put("telephone", etAppointmentPhoneNum.getText().toString().trim());
            map.put("source", "pc");
            RequestBody requestBody = RequestBody.create(MEDIA_TYPE_JSON, JsonUtils.toJson(map));
            Request requestPost = new Request.Builder().url(url).post(requestBody).build();
            Call call = okHttpClient.newCall(requestPost);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast("预约成功！");
                            MobclickAgent.onPause(getActivity());
                            AIOAnalytics.onPageEnd("appoint");
                            dialog.dismiss();
                        }
                    });
                }
            });
        }
    }

    public void stopViewPager() {
        if (abHomeLoop != null) {
            abHomeLoop.setAutoPlay(false);
        }

        if (etHomeSearch != null) {
            etHomeSearch.setFocusable(false);
            etHomeSearch.setFocusableInTouchMode(false);

            etHomeSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onFragmentInteraction("topView");
                }
            });
        }
    }

    public void startViewPager() {
        if (abHomeLoop != null) {
            abHomeLoop.setAutoPlay(true);
        }
        if (etHomeSearch != null) {
            etHomeSearch.setFocusable(true);
            etHomeSearch.setFocusableInTouchMode(true);
            etHomeSearch.setOnClickListener(null);
        }

    }

    public interface HandleViewPager {
        boolean handleListenning();
    }

    public void setHandleListenning(HandleViewPager handleListenning) {
        mHandleListenning = handleListenning;
    }

    //读取本地json生成json字符串
    private String initJsonData() {
        try {
            InputStream is = getResources().getAssets().open("goods.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            mJson = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mJson;
    }
}
