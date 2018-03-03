package com.mengyang.kohler.home.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.Config;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.home.adapter.BarrageAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 我要爬墙
 */

public class BarrageActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {
    private List<String> mDataList = new ArrayList<>();

    @BindView(R.id.tv_barrage_top)
    TopView tvBarrageTop;
    @BindView(R.id.btn_barrage_send)
    Button mBtnBarrageSend;
    @BindView(R.id.recycler_view_barrage)
    RecyclerView mRecyclerViewBarrage;
    @BindView(R.id.et_barrage)
    EditText mEtBarrage;
    private String mContent;
    private BarrageAdapter mBarrageAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_barrage;
    }

    @Override
    protected void initValues() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvBarrageTop);

        mRecyclerViewBarrage.setLayoutManager(new LinearLayoutManager(this));
        mBarrageAdapter = new BarrageAdapter(mDataList);
        mBarrageAdapter.setOnItemClickListener(this);
        mRecyclerViewBarrage.setAdapter(mBarrageAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
    }

    @OnClick({R.id.btn_barrage_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_barrage_send:
                mContent = mEtBarrage.getText().toString().trim();
                mEtBarrage.getText().clear();
                mEtBarrage.setFocusable(true);

                if (!TextUtils.isEmpty(mContent)) {
                    mBarrageAdapter.addData(mContent);
                    mRecyclerViewBarrage.scrollToPosition(mBarrageAdapter.getItemCount() - 1);
                    SendBarrage(mContent);
                } else {
                    ToastUtil.showToast("输入内容不能为空");
                }
                break;
            default:
                break;
        }
    }

    private void SendBarrage(final String content) {
        new Thread() {
            @Override
            public void run() {

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject json = new JSONObject();

                try {
                    json.put("content", content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //申明给服务端传递一个json串
                //创建一个OkHttpClient对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                //json为String类型的json数据
                RequestBody requestBody = RequestBody.create(JSON, String.valueOf(json));
                //创建一个请求对象
                String token = "09HllB4xu5ZnRxlaREAGaFwoYcEaiqlvnaUlXzl4g4A";
                String path = Config.MEETING_BARRAGE + token;
                String format = String.format(path);

                Request request = new Request.Builder()
                        .url(format)
                        .post(requestBody)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("123456", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("123456", "请求成功");
                    }
                });
            }
        }.start();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        hideInput();
    }
}
