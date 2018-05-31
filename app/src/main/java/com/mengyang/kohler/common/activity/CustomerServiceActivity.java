package com.mengyang.kohler.common.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.Gson;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.adapter.UserServiceAdapter;
import com.mengyang.kohler.common.entity.Level0Item;
import com.mengyang.kohler.common.entity.Level1Item;
import com.mengyang.kohler.common.net.Config;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.StringUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.bean.AzureServiceBean;
import com.mengyang.kohler.module.bean.AzureServiceMultimediaBean;
import com.mengyang.kohler.module.bean.QuestionSearchBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 客服
 */
public class CustomerServiceActivity extends BaseActivity {

    @BindView(R.id.tv_customer_service_top)
    TopView tvCustomerServiceTop;
    @BindView(R.id.btn_send_message)
    Button mBtnSendMessage;
    @BindView(R.id.recycler_view_service11)
    RecyclerView mRecyclerViewService;
    @BindView(R.id.et_question)
    EditText mEtQuestion;
    @BindView(R.id.rb_customer_01)
    RadioButton mRbCustomer01;
    @BindView(R.id.rb_customer_02)
    RadioButton mRbCustomer02;
    @BindView(R.id.rb_customer_03)
    RadioButton mRbCustomer03;
    @BindView(R.id.ll_customer)
    LinearLayout mLlCustomer;
    @BindView(R.id.hsv_customer)
    HorizontalScrollView mHsvCustomer;


    private static final String HEADER_KEY = "Authorization";
    //    private static final String HEADER_VALUE = "Bearer yTlSJIGr5Ak.cwA.k1Y.hD-eSE5mXqmXFNzB6TX_LI4qqD_TyCPQYOqEK2Lnk68";
    private static final String HEADER_VALUE = "Basic [base64(n0lWVV1Lwx8p7tYR:95f19722c7f2544b395bf89c8789ebaa2748020a5bf49162385219ec67c9b0fc)]";


    private String mQuestionContent;
    //    private AzureCustomerServiceAdapter mUserServiceAdapter;

    //    private static final String URL = "https://directline.botframework.com/";
    //    private static final String START = URL + "v3/directline/conversations";
    //    private static final String REQUEST = "/activities";
    //    private AzureBotStartBean mAzureBotStartBean;
    private AzureServiceMultimediaBean mAzureServiceMultimediaBean;
    private ArrayList<MultiItemEntity> res;
    private UserServiceAdapter mUserServiceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //        //防止状态栏和标题重叠
        //        ImmersionBar.setTitleBar(this, tvCustomerServiceTop);
        initAdapter();

        mLlCustomer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                mLlCustomer.getWindowVisibleDisplayFrame(r);

                int heightDiff = mLlCustomer.getRootView().getHeight() - (r.bottom - r.top);
                boolean isOpen = heightDiff > 100;

                if (isOpen) {
                    mHsvCustomer.setVisibility(View.GONE);
                } else {
                    mHsvCustomer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initAdapter() {
        mRecyclerViewService.setLayoutManager(new LinearLayoutManager(this));
        mUserServiceAdapter = new UserServiceAdapter(generateData());
        mRecyclerViewService.setAdapter(mUserServiceAdapter);
    }

    @Override
    protected void initListener() {
        mRecyclerViewService.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideInput();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        //        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(50000, TimeUnit.MILLISECONDS).writeTimeout(50000, TimeUnit.MILLISECONDS).readTimeout(50000, TimeUnit.MILLISECONDS).build();
        //        Request requestPost = new Request.Builder().url(START).post(RequestBody.create(null, "")).header(HEADER_KEY, HEADER_VALUE).build();
        //        Call call = okHttpClient.newCall(requestPost);
        //        call.enqueue(new Callback() {
        //            @Override
        //            public void onFailure(Call call, IOException e) {
        //                e.printStackTrace();
        //            }
        //
        //            @Override
        //            public void onResponse(Call call, Response response) {
        //                mAzureBotStartBean = new Gson().fromJson(response.body().charStream(), AzureBotStartBean.class);
        //            }
        //        });
    }


    @OnClick({R.id.recycler_view_service11, R.id.btn_send_message, R.id.rb_customer_01, R.id.rb_customer_02, R.id.rb_customer_03})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //            case R.id.recycler_view_service11:
            //
            //                break;
            case R.id.btn_send_message:
                mQuestionContent = mEtQuestion.getText().toString().trim();
                mEtQuestion.getText().clear();
                mEtQuestion.setFocusable(true);

                if (!TextUtils.isEmpty(mQuestionContent)) {
                    QuestionSearchBean questionSearchBean = new QuestionSearchBean(mQuestionContent, 4);
                    mUserServiceAdapter.addData(questionSearchBean);
                    searchQuestion(mQuestionContent);
                } else {
                    ToastUtil.showToast("输入内容不能为空");
                }
                break;
            case R.id.rb_customer_01:
                String rb01 = mRbCustomer01.getText().toString().trim();
                mEtQuestion.setText(rb01);
                break;
            case R.id.rb_customer_02:
                String rb02 = mRbCustomer02.getText().toString().trim();
                mEtQuestion.setText(rb02);
                break;
            case R.id.rb_customer_03:
                String rb03 = mRbCustomer03.getText().toString().trim();
                mEtQuestion.setText(rb03);
                break;
            default:
                break;
        }
    }

    private void searchQuestion(String question) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(50000, TimeUnit.MILLISECONDS).writeTimeout(50000, TimeUnit.MILLISECONDS).readTimeout(50000, TimeUnit.MILLISECONDS).build();
        //        AzureBotSendMsgBean azureBotSendMsgBean = new AzureBotSendMsgBean();
        //        AzureBotSendMsgBean.FromBean fromBean = new AzureBotSendMsgBean.FromBean();
        //        fromBean.setId("user_name");
        //        azureBotSendMsgBean.setType("message");
        //        azureBotSendMsgBean.setFrom(fromBean);
        //        azureBotSendMsgBean.setText(question);
        Map<String, Object> map = IdeaApi.getSign();
        map.put("queryStr", question);
        Gson gson = new Gson();
        String Authorization = gson.toJson(map);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), Authorization);
        Request requestPost = new Request.Builder().url(Config.AZURE_AI).post(requestBody).header(HEADER_KEY, HEADER_VALUE).build();
        Call call = okHttpClient.newCall(requestPost);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) {
                AzureServiceBean azureServiceBean = new Gson().fromJson(response.body().charStream(), AzureServiceBean.class);
                //                AzureBotAnswerQuestionBean azureBotAnswerQuestionBean = new Gson().fromJson(response.body().charStream(), AzureBotAnswerQuestionBean.class);
                final QuestionSearchBean questionSearchBean = new QuestionSearchBean("", 2);
                if (!azureServiceBean.getData().getMessage().equals("") && azureServiceBean.getData().getMessage() != null)
                    questionSearchBean.setDescription(azureServiceBean.getData().getMessage());
                if (azureServiceBean.getData().getClickVos().size() > 0) {
                    for (int i = 0; i < azureServiceBean.getData().getClickVos().size(); i++) {
                        //                        String parent_start = StringUtils.identical(azureServiceBean.getData().getClickVos().get(i).getText(), "【", "】");
                        //                        String parent_end = azureServiceBean.getData().getClickVos().get(i).getText().substring(parent_start.lastIndexOf("】") + 1, azureServiceBean.getData().getClickVos().get(i).getText().length());
                        //                        res.add(new Level0Item(parent_start, parent_end));
                        int a = 24;
                        String str = StringUtils.identical("【售前问题】（门店预约、产品询价、官方商城地址）", "【", "】");
                        LogUtils.i("rmy", "str = " + str);
                        int o = str.lastIndexOf("】");
                        LogUtils.i("rmy", "o = " + o);
                        String ppp = "【售前问题】（门店预约、产品询价、官方商城地址）".substring(o + 1, a);
                        LogUtils.i("rmy", "ppp = " + ppp);
                        res.add(new Level0Item(str, ppp));
                    }
                }
                if (azureServiceBean.getData().getMultimedia().size() > 0) {
                    for (int i = 0; i < azureServiceBean.getData().getMultimedia().size(); i++) {
                        mAzureServiceMultimediaBean = new AzureServiceMultimediaBean(azureServiceBean.getData().getMultimedia().get(i).getElementDesc(), azureServiceBean.getData().getMultimedia().get(i).getElementType(), azureServiceBean.getData().getMultimedia().get(i).getImageUrl(), azureServiceBean.getData().getMultimedia().get(i).getTitle(), 3);
                        res.add(mAzureServiceMultimediaBean);
                    }
                }
                CustomerServiceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mUserServiceAdapter.addData(questionSearchBean);
                        mRecyclerViewService.scrollToPosition(mUserServiceAdapter.getItemCount() - 1);
                    }
                });
                //                new Thread() {
                //                    public void run() {
                //                        try {
                //                            sleep(3000);
                //                            answerQuestion(START + "/" + mAzureBotStartBean.getConversationId() + REQUEST + "?watermark=" + azureBotWartBean.getId().substring(23));
                //                        } catch (InterruptedException e) {
                //                            e.printStackTrace();
                //                        }
                //                    }
                //                }.start();
            }
        });
    }

    //    private void answerQuestion(String answerUrl) {
    //        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(50000, TimeUnit.MILLISECONDS).writeTimeout(50000, TimeUnit.MILLISECONDS).readTimeout(50000, TimeUnit.MILLISECONDS).build();
    //        Request request = new Request.Builder().url(answerUrl).header(HEADER_KEY, HEADER_VALUE).build();
    //        okHttpClient.newCall(request).enqueue(new Callback() {
    //            @Override
    //            public void onFailure(Call call, IOException e) {
    //            }
    //
    //            @Override
    //            public void onResponse(Call call, Response response) {
    //                AzureBotAnswerQuestionBean azureBotAnswerQuestionBean = new Gson().fromJson(response.body().charStream(), AzureBotAnswerQuestionBean.class);
    //                final QuestionSearchBean questionSearchBean = new QuestionSearchBean("", 2);
    //                questionSearchBean.setDescription(azureBotAnswerQuestionBean.getActivities().get(0).getText());
    //                AzureCustomerServiceActivity.this.runOnUiThread(new Runnable() {
    //                    @Override
    //                    public void run() {
    //                        mUserServiceAdapter.addData(questionSearchBean);
    //                        rvAzureBot.scrollToPosition(mUserServiceAdapter.getItemCount() - 1);
    //                    }
    //                });
    //            }
    //        });
    //    }

    private ArrayList<MultiItemEntity> generateData() {
        int lv1Count = 3;

        res = new ArrayList<>();
        QuestionSearchBean textBean = new QuestionSearchBean("Hi～我是科勒客服，需要咨询科勒产品吗，请在对话框输入尝试一下我们的Click to Chat？", 2);
        res.add(textBean);

        Level0Item lv0 = new Level0Item("【售前问题】", "（门店预约、产品询价、官方商城地址）");
        for (int j = 0; j < lv1Count; j++) {
            Level1Item lv1 = new Level1Item("更改配送时间？" + j);
            lv0.addSubItem(lv1);
        }
        res.add(lv0);
        lv0 = new Level0Item("【产品相关】", "（产品规格/参数、产品SKU）");
        for (int j = 0; j < lv1Count; j++) {
            Level1Item lv1 = new Level1Item("更改配送时间？" + j);
            lv0.addSubItem(lv1);
        }
        res.add(lv0);
        lv0 = new Level0Item("【售后问题】", "（商品故障/损坏、保修查询、客服热线）");
        for (int j = 0; j < lv1Count; j++) {
            Level1Item lv1 = new Level1Item("更改配送时间？" + j);
            lv0.addSubItem(lv1);
        }
        res.add(lv0);

        textBean = new QuestionSearchBean("还可以进入科勒预约系统进行门店查询和预约, 点击进入 或 返回", 2);
        res.add(textBean);
        //        res.add(new QuestionSearchBean("2222222222222222", 3));
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
