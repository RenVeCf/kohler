package com.mengyang.kohler.common.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.adapter.AzureCustomerServiceAdapter;
import com.mengyang.kohler.common.utils.JsonUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.bean.AzureBotAnswerQuestionBean;
import com.mengyang.kohler.module.bean.AzureBotStartBean;
import com.mengyang.kohler.module.bean.AzureBotWartBean;
import com.mengyang.kohler.module.bean.QuestionSearchBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AzureCustomerServiceActivity extends BaseActivity {

    @BindView(R.id.tv_azure_customer_service_top)
    TopView tvAzureCustomerServiceTop;
    @BindView(R.id.iv_top_back)
    ImageView ivTopBack;
    @BindView(R.id.rv_azure_bot)
    RecyclerView rvAzureBot;
    @BindView(R.id.et_azure_question)
    EditText etAzureQuestion;
    @BindView(R.id.bt_azure_send_message)
    Button btAzureSendMessage;

    private List<QuestionSearchBean> mDataList = new ArrayList<>();
    private String mQuestionContent;
    private AzureCustomerServiceAdapter mUserServiceAdapter;

    private static final String URL = "https://directline.botframework.com/";
//    private static final String CREAT = URL + "v3/directline/tokens/generate";
    private static final String START = URL + "v3/directline/conversations";
    private static final String REQUEST = "/activities";
    private AzureBotStartBean mAzureBotStartBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_azure_customer_service;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //沉浸式状态栏初始化白色
        ImmersionBar.with(this).fitsSystemWindows(false).statusBarDarkFont(false).init();
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAzureCustomerServiceTop);
        ivTopBack.setImageResource(R.mipmap.fanhuibai);
        initAdapter();
    }

    private void initAdapter() {
        rvAzureBot.setLayoutManager(new LinearLayoutManager(this));
        mDataList.add(new QuestionSearchBean("Hi～我是科勒客服，需要咨询科勒产品吗，请在对话框输入尝试一下我们的Click to Chat？", 3));
        mDataList.add(new QuestionSearchBean("还可以进入科勒预约系统进行门店查询和预约点击进入 或 返回", 3));
        mUserServiceAdapter = new AzureCustomerServiceAdapter(mDataList);
        rvAzureBot.setAdapter(mUserServiceAdapter);
    }

    @Override
    protected void initListener() {
        rvAzureBot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideInput();
                return false;
            }
        });
    }

    @Override
    protected void initData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(50000, TimeUnit.MILLISECONDS).writeTimeout(50000, TimeUnit.MILLISECONDS).readTimeout(50000, TimeUnit.MILLISECONDS).build();
        Request requestPost = new Request.Builder().url(START).post(RequestBody.create(null, "")).header("Authorization", "Bearer yTlSJIGr5Ak.cwA.k1Y.hD-eSE5mXqmXFNzB6TX_LI4qqD_TyCPQYOqEK2Lnk68").build();
        Call call = okHttpClient.newCall(requestPost);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("rmy", "onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mAzureBotStartBean = new Gson().fromJson(response.body().charStream(), AzureBotStartBean.class);
            }
        });
    }

    @OnClick({R.id.bt_azure_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_azure_send_message:
                mQuestionContent = etAzureQuestion.getText().toString().trim();
                etAzureQuestion.getText().clear();
                etAzureQuestion.setFocusable(true);

                if (!TextUtils.isEmpty(mQuestionContent)) {
                    QuestionSearchBean questionSearchBean = new QuestionSearchBean(mQuestionContent, 1);
                    mUserServiceAdapter.addData(questionSearchBean);
                    try {
                        searchQuestion(mQuestionContent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showToast("输入内容不能为空");
                }
                break;
            default:
                break;
        }
    }

    private void searchQuestion(String question) throws JSONException {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(50000, TimeUnit.MILLISECONDS).writeTimeout(50000, TimeUnit.MILLISECONDS).readTimeout(50000, TimeUnit.MILLISECONDS).build();
        JSONObject clientKey = new JSONObject();
        clientKey.put("id", "aa");
        JSONObject Authorization = new JSONObject();
        Authorization.put("type", "message");
        Authorization.put("from", clientKey);
        Authorization.put("text", question);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), JsonUtils.toJson(Authorization));
        Log.i("rmy", "botBean = " + START + "/" + mAzureBotStartBean.getConversationId() + REQUEST + ", Authorization = " + Authorization);
        Request requestPost = new Request.Builder().url(START + "/" + mAzureBotStartBean.getConversationId() + REQUEST).post(requestBody).header("Authorization", "Bearer yTlSJIGr5Ak.cwA.k1Y.hD-eSE5mXqmXFNzB6TX_LI4qqD_TyCPQYOqEK2Lnk68").build();
        Call call = okHttpClient.newCall(requestPost);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("rmy", "onFailure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtils.i("rmy", "response 000  = " + response.headers());
                AzureBotWartBean azureBotWartBean = new Gson().fromJson(response.body().charStream(), AzureBotWartBean.class);
                Log.i("rmy", "body = " + azureBotWartBean.toString());
                if (azureBotWartBean.toString() != null) {
                    answerQuestion(START + "/" + mAzureBotStartBean.getConversationId() + REQUEST + "?watermark=" + azureBotWartBean.getId());
                }
            }

            private void answerQuestion(String answerUrl) {
                OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(50000, TimeUnit.MILLISECONDS).writeTimeout(50000, TimeUnit.MILLISECONDS).readTimeout(50000, TimeUnit.MILLISECONDS).build();
                Request request = new Request.Builder().url(answerUrl).header("Authorization", "Bearer yTlSJIGr5Ak.cwA.k1Y.hD-eSE5mXqmXFNzB6TX_LI4qqD_TyCPQYOqEK2Lnk68").build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("rmy", "onFailure: " + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        AzureBotAnswerQuestionBean azureBotAnswerQuestionBean = new Gson().fromJson(response.body().charStream(), AzureBotAnswerQuestionBean.class);
                        QuestionSearchBean questionSearchBean = new QuestionSearchBean("", 0);
                        questionSearchBean.setDescription(azureBotAnswerQuestionBean.getActivities().get(0).getText());
                        mUserServiceAdapter.addData(questionSearchBean);
                        rvAzureBot.scrollToPosition(mUserServiceAdapter.getItemCount() - 1);
                    }
                });
            }
        });
    }
}
