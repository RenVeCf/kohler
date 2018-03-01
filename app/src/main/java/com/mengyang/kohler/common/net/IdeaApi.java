package com.mengyang.kohler.common.net;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.mengyang.kohler.App;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.MD5Utils;
import com.mengyang.kohler.common.utils.NetWorkUtils;
import com.mengyang.kohler.common.utils.SPUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络请求公共配置
 * Created by zhpan on 2017/4/loading1.
 */

public class IdeaApi {
    private Retrofit retrofit;
    private IdeaApiService service;

    private IdeaApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
//                    LogUtils.e("OKHttp-----", text);
                    LogUtils.e("OKHttp-----", message);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.e("OKHttp-----", message);
                }
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        File cacheFile = new File(App.getContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(IdeaApiService.DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new HttpHeaderInterceptor())
                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(IdeaApiService.API_SERVER_URL)
                .build();
        service = retrofit.create(IdeaApiService.class);
    }

    //  添加请求头的拦截器
    private class HttpHeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //  将token统一放到请求头
            String token = (String) SPUtil.get(App.getContext(), IConstants.TOKEN, "");
            //            //  也可以统一配置用户名
            //            String user_id="123456";
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse.newBuilder()
                    .header("token", token)
                    //                    .header("user_id", user_id)
                    .build();
        }
    }

    /**
     * 公共参数
     */
    public static Map<String, String> getSign() {
        Map<String, String> stringMap = new HashMap<String, String>();

        String token = (String) SPUtil.get(App.getContext(), IConstants.TOKEN, "");
        if (token == null || "".equals(token)) {
        } else {
            stringMap.put("accessToken", token);
        }
        stringMap.put("appVersion", "1.0");
        stringMap.put("appType", "android");
        stringMap.put("clientId", "FjyrG8VkMLntjtGi");
        stringMap.put("charset", "utf-8");
        stringMap.put("deviceId", Build.ID);
        stringMap.put("resultType", "json");
        stringMap.put("ipAddress", "192.168.0.6");
        stringMap.put("reqTime", System.currentTimeMillis() + "");
        StringBuffer sb = new StringBuffer();
        //设置表单参数
        for (String key : stringMap.keySet()) {
            sb.append(key + "=" + stringMap.get(key) + "&");
        }
        sb.append("Uujr6uw2QQVFKI9GFVYUfPLN5c4WKwc6");
        stringMap.put("sign", MD5Utils.encodeMD5(sb.toString()));
        stringMap.put("signType", "MD5");
        return stringMap;
    }

    //打印请求Log
    public static String getRequestLogin(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (String key : map.keySet()) {
            i++;
            if (i == map.size()) {
                sb.append(key + "=" + map.get(key));
            } else {
                sb.append(key + "=" + map.get(key) + "&");
            }
        }
        String sendBuf = sb.toString().replace("+", "%2B");
        LogUtils.i("请求参数 = " + sendBuf);
        return sendBuf;
    }

    //  创建单例
    private static class SingletonHolder {
        private static final IdeaApi INSTANCE = new IdeaApi();
    }

    public static IdeaApiService getApiService() {
        return SingletonHolder.INSTANCE.service;
    }

    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.checkNetwork(App.getContext())) {  //没网强制从缓存读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                LogUtils.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtils.checkNetwork(App.getContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}
