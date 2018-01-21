package com.mengyang.kohler.common.net;

import com.mengyang.kohler.common.utils.Config;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.IndexBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 接口请求类
 * Created by dell on 2017/4/1.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 5000;
    //项目主页(测试环境)
    String API_SERVER_URL = "http://test.jifen.api.jie-ci.com/";
    //项目主页(正式环境)
    //    String API_SERVER_URL = "http://t-api.glor.cn/";

    @FormUrlEncoded
    @POST(Config.INDEX)
    Observable<BasicResponse<IndexBean>> getIndex(@FieldMap Map<String, String> map);
}
