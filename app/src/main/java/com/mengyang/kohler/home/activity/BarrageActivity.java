package com.mengyang.kohler.home.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.Config;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.SpacesItemDecoration;
import com.mengyang.kohler.common.view.TopView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class BarrageActivity extends BaseActivity {
    @BindView(R.id.tv_barrage_top)
    TopView tvBarrageTop;
    @BindView(R.id.rv_barrage)
    RecyclerView rvBarrage;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_barrage;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvBarrageTop);
        // 设置管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.getContext());
        rvBarrage.setLayoutManager(layoutManager);
        rvBarrage.addItemDecoration(new SpacesItemDecoration(20));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rvBarrage.setHasFixedSize(true);
        rvBarrage.setItemAnimator(new DefaultItemAnimator());
        //        mSystemMsgBean = new ArrayList<>();
        //        mSystemMsgAdapter = new SystemMsgAdapter(mSystemMsgBean);
        //        rvBarrage.setAdapter(mSystemMsgAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        new Thread() {
            @Override
            public void run() {

                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                JSONObject json = new JSONObject();

                try {
                    json.put("content", "ttuututututututututtututututututu");
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
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });
            }
        }.start();
    }
}
