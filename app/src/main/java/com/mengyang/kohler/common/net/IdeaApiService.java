package com.mengyang.kohler.common.net;

import com.mengyang.kohler.common.utils.Config;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.LoginBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 接口请求类
 * Created by dell on 2017/4/loading1.
 */

public interface IdeaApiService {
    /**
     * 网络请求超时时间毫秒
     */
    int DEFAULT_TIMEOUT = 5000;
    //项目主页(测试环境)
    String API_SERVER_URL = "http://kohler-app.glor.cn/";
    //项目主页(正式环境)
    //    String API_SERVER_URL = "http://t-api.glor.cn/";

    //普通用户注册
    @FormUrlEncoded
    @POST(Config.USER_REGISTER)
    Observable<BasicResponse> getUserRegister(@FieldMap Map<String, String> map);

    //登陆
    @FormUrlEncoded
    @POST(Config.APP_LOGIN)
    Observable<BasicResponse<LoginBean>> getLogin(@FieldMap Map<String, String> map);

    //设备注册
    @FormUrlEncoded
    @POST(Config.EQUIPMENT_REQISTRATION)
    Observable<BasicResponse> getEquipmentReqistration(@FieldMap Map<String, String> map);

    //设备注册
    @FormUrlEncoded
    @POST(Config.EQUIPMENT_REQISTRATION)
    Observable<BasicResponse> getUserGoOut(@FieldMap Map<String, String> map);

    //手册
    @FormUrlEncoded
    @POST(Config.BOOK_LIST)
    Observable<BasicResponse> getBookList(@FieldMap Map<String, String> map);
}
