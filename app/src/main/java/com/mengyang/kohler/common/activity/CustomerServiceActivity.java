package com.mengyang.kohler.common.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.adapter.UserServiceAdapter;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.QuestionSearchBean;
import com.mengyang.kohler.module.bean.UserMsg;

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
    @BindView(R.id.recycler_view_service)
    RecyclerView mRecyclerViewService;
    @BindView(R.id.et_question)
    EditText mEtQuestion;

    private List<UserMsg> mDataList = new ArrayList<>();
    private List<QuestionSearchBean> mResponse = new ArrayList<>();
    private String mQuestionContent;
    private UserServiceAdapter mUserServiceAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_customer_service;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvCustomerServiceTop);

        mRecyclerViewService.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mDataList.add(new UserMsg("1235213",1));
        mUserServiceAdapter = new UserServiceAdapter(mDataList);
        mRecyclerViewService.setAdapter(mUserServiceAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.recycler_view_service, R.id.btn_send_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recycler_view_service:

                break;
            case R.id.btn_send_message:
                mQuestionContent = mEtQuestion.getText().toString().trim();

                mDataList.add(new UserMsg(mQuestionContent,1));

                searchQuestion(mQuestionContent);


//                                        mDataList.clear();

                break;
                default:
                    break;
        }
    }

    private void searchQuestion( String question) {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("keyWord", question); // 头像URL

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .questionSearch(stringMap)
                .compose(this.<BasicResponse<QuestionSearchBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<QuestionSearchBean>>(this,true) {
                    @Override
                    public void onSuccess(BasicResponse<QuestionSearchBean> response) {
                        Log.i("666",response.getData().toString());
//                        mResponse = response.getData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                mUserServiceAdapter.addData(mDataList);
//                                mUserServiceAdapter.notifyDataSetChanged();
                            }
                        });

                    }

                    @Override
                    public void onFail(BasicResponse<QuestionSearchBean> response, int code) {
                        super.onFail(response, code);

                    }
                });

    }
}
