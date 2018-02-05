package com.mengyang.kohler.common.net;

import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.AllSearchBean;
import com.mengyang.kohler.module.bean.LikeListBean;
import com.mengyang.kohler.module.bean.LoginBean;
import com.mengyang.kohler.module.bean.NotSelectClassificationBean;
import com.mengyang.kohler.module.bean.RefreshTokenBean;
import com.mengyang.kohler.module.bean.ReservationQueryBean;
import com.mengyang.kohler.module.bean.SelectClassificationBean;
import com.mengyang.kohler.module.bean.StoreListBean;
import com.mengyang.kohler.module.bean.SystemMsgBean;
import com.mengyang.kohler.module.bean.UploadHeadPortraitBean;
import com.mengyang.kohler.module.bean.UserHomeKVBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    //普通用户首页KV图
    @FormUrlEncoded
    @POST(Config.USER_HOME_KV)
    Observable<BasicResponse<List<UserHomeKVBean>>> getUserHomeKV(@FieldMap Map<String, String> map);

    //经销商用户首页KV图
    @FormUrlEncoded
    @POST(Config.DEALER_HOME_KV)
    Observable<BasicResponse<List<UserHomeKVBean>>> getDealerHomeKV(@FieldMap Map<String, String> map);

    //设计师用户首页KV图
    @FormUrlEncoded
    @POST(Config.DESIGNER_HOME_KV)
    Observable<BasicResponse<List<UserHomeKVBean>>> getDesignerHomeKV(@FieldMap Map<String, String> map);

    //普通用户注册
    @FormUrlEncoded
    @POST(Config.USER_REGISTER)
    Observable<BasicResponse> getUserRegister(@FieldMap Map<String, String> map);

    //登陆
    @FormUrlEncoded
    @POST(Config.APP_LOGIN)
    Observable<BasicResponse<LoginBean>> getLogin(@FieldMap Map<String, String> map);

    //登陆验证码
    @FormUrlEncoded
    @POST(Config.LOGIN_VERIFICATION_IMG)
    Observable<BasicResponse> getLoginVerificationImg(@FieldMap Map<String, String> map);

    //更新access_token
    @FormUrlEncoded
    @POST(Config.REFRESH_TOKEN)
    Observable<BasicResponse<RefreshTokenBean>> getRefreshToken(@FieldMap Map<String, String> map);

    //设备注册
    @FormUrlEncoded
    @POST(Config.EQUIPMENT_REQISTRATION)
    Observable<BasicResponse> getEquipmentReqistration(@FieldMap Map<String, String> map);

    //退出登录
    @FormUrlEncoded
    @POST(Config.USER_GO_OUT)
    Observable<BasicResponse> getUserGoOut(@FieldMap Map<String, String> map);

    //用户解绑手机
    @FormUrlEncoded
    @POST(Config.USER_CANCEL_BIND_PHONE)
    Observable<BasicResponse> getCancelBindPhone(@FieldMap Map<String, String> map);

    //用户绑定手机
    @FormUrlEncoded
    @POST(Config.USER_BIND_PHONE)
    Observable<BasicResponse> getBindPhone(@FieldMap Map<String, String> map);

    //用户换绑手机
    @FormUrlEncoded
    @POST(Config.USER_MODIFY_BIND_PHONE)
    Observable<BasicResponse> getModifyBindPhone(@FieldMap Map<String, String> map);

    //系统消息
    @FormUrlEncoded
    @POST(Config.SYSTEM_MSG)
    Observable<BasicResponse<List<SystemMsgBean>>> getSystemMsg(@FieldMap Map<String, String> map);

    //修改头像
    @FormUrlEncoded
    @POST(Config.MODIFY_HEAD_PORTRAIT)
    Observable<BasicResponse> getModifyHeadPortrait(@FieldMap Map<String, String> map);

    //上传头像
    @Multipart
    @POST(Config.UPLOAD_HEAD_PORTRAIT)
    Observable<BasicResponse<UploadHeadPortraitBean>> getUploadHeadPortrait(@Part List<MultipartBody.Part> image);

    //修改昵称
    @FormUrlEncoded
    @POST(Config.MODIFY_NIKE_NAME)
    Observable<BasicResponse> getModifyNikeName(@FieldMap Map<String, String> map);

    //忘记密码
    @FormUrlEncoded
    @POST(Config.FORGET_PWD)
    Observable<BasicResponse> getForgetPwd(@FieldMap Map<String, String> map);

    //修改密码
    @FormUrlEncoded
    @POST(Config.MODIFY_PWD)
    Observable<BasicResponse> getModifyPwd(@FieldMap Map<String, String> map);

    //获取用户预约信息
    @FormUrlEncoded
    @POST(Config.USER_RESERVE_MSG)
    Observable<BasicResponse<ReservationQueryBean>> getUserReserveMsg(@FieldMap Map<String, String> map);

    //用户添加收藏
    @FormUrlEncoded
    @POST(Config.Add_Like)
    Observable<BasicResponse> getAddLike(@FieldMap Map<String, String> map);

    //用户取消收藏
    @FormUrlEncoded
    @POST(Config.CANCEL_Like)
    Observable<BasicResponse> getCancelLike(@FieldMap Map<String, String> map);

    //用户收藏列表
    @FormUrlEncoded
    @POST(Config.Like_List)
    Observable<BasicResponse<LikeListBean>> getLikeList(@FieldMap Map<String, String> map);

    //附近店铺
    @FormUrlEncoded
    @POST(Config.STORE_LIST)
    Observable<BasicResponse<StoreListBean>> getStoreList(@FieldMap Map<String, String> map);

    //精选分类
    @FormUrlEncoded
    @POST(Config.SELECT_CLASSIFICATION)
    Observable<BasicResponse<List<SelectClassificationBean>>> getSelectClassification(@FieldMap Map<String, String> map);

    //非精选分类
    @FormUrlEncoded
    @POST(Config.COMMODITY_CLASSIFICATION)
    Observable<BasicResponse<List<NotSelectClassificationBean>>> getNotSelectClassification(@FieldMap Map<String, String> map);

    //全文搜索
    @FormUrlEncoded
    @POST(Config.ALL_SEARCH)
    Observable<BasicResponse<List<AllSearchBean>>> getAllSearch(@FieldMap Map<String, String> map);

}
