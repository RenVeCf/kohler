package com.mengyang.kohler.common.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.adapter.UserServiceAdapter;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.QuestionSearchBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    private List<QuestionSearchBean> mDataList = new ArrayList<>();
    private String mQuestionContent;
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
    }

    private void initAdapter() {
        mRecyclerViewService.setLayoutManager(new LinearLayoutManager(this));
        mDataList.add(new QuestionSearchBean("您好 我的是科勒机器人客服，请输入您的姓名来搜索桌号", 3));
        mUserServiceAdapter = new UserServiceAdapter(mDataList);
        mRecyclerViewService.setAdapter(mUserServiceAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.recycler_view_service11, R.id.btn_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recycler_view_service11:

                break;
            case R.id.btn_send_message:
                mQuestionContent = mEtQuestion.getText().toString().trim();
                mEtQuestion.getText().clear();
                mEtQuestion.setFocusable(true);

                if (!TextUtils.isEmpty(mQuestionContent)) {
                    QuestionSearchBean questionSearchBean = new QuestionSearchBean(mQuestionContent, 1);
                    mUserServiceAdapter.addData(questionSearchBean);
                    searchQuestion(mQuestionContent);
                } else {
                    ToastUtil.showToast("输入内容不能为空");
                }
                break;
            default:
                break;
        }
    }

    private void searchQuestion(String question) {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("keyWord", question); // 头像URL

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .questionSearch(stringMap)
                .compose(this.<BasicResponse<QuestionSearchBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<QuestionSearchBean>>(this, false) {
                    @Override
                    public void onSuccess(final BasicResponse<QuestionSearchBean> response) {
                        QuestionSearchBean questionSearchBean = new QuestionSearchBean("", 0);
                        if (response.getData() != null) {
                            questionSearchBean = response.getData();
                            mUserServiceAdapter.addData(questionSearchBean);
                            mRecyclerViewService.scrollToPosition(mUserServiceAdapter.getItemCount() - 1);

                            mUserServiceAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                                @Override
                                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                    switch (view.getId()) {
                                        case R.id.tv_service_list:
                                            startActivity(new Intent(CustomerServiceActivity.this, WebViewActivity.class).putExtra("h5url", response.getData().getH5Url()).putExtra("flag", 1));
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            });
                        }
                    }
                });
    }
}
