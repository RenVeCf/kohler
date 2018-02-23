package com.mengyang.kohler.home.activity;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.Config;
import com.mengyang.kohler.common.view.TopView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
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

public class BarrageActivity extends BaseActivity {
    @BindView(R.id.tv_barrage_top)
    TopView tvBarrageTop;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_barrage;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvBarrageTop);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    private void SendBarrage() {
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
