package com.mengyang.kohler.common.net;

import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.AddressBean;
import com.mengyang.kohler.module.bean.AllSearchBean;
import com.mengyang.kohler.module.bean.ArtKohlerBean;
import com.mengyang.kohler.module.bean.ArtKohlerSelectImgBean;
import com.mengyang.kohler.module.bean.BooksListBean;
import com.mengyang.kohler.module.bean.CommodityClassificationFragmentBean;
import com.mengyang.kohler.module.bean.CommodityClassificationTitleBean;
import com.mengyang.kohler.module.bean.CommodityDetailsBean;
import com.mengyang.kohler.module.bean.FootPrintBean;
import com.mengyang.kohler.module.bean.HomeIndexBean;
import com.mengyang.kohler.module.bean.KbisBean;
import com.mengyang.kohler.module.bean.LikeListBean;
import com.mengyang.kohler.module.bean.LiveRealTimeBean;
import com.mengyang.kohler.module.bean.LoginBean;
import com.mengyang.kohler.module.bean.MeetingBean;
import com.mengyang.kohler.module.bean.NotSelectClassificationBean;
import com.mengyang.kohler.module.bean.QuestionSearchBean;
import com.mengyang.kohler.module.bean.RefreshTokenBean;
import com.mengyang.kohler.module.bean.ReservationQueryBean;
import com.mengyang.kohler.module.bean.SelectClassificationBean;
import com.mengyang.kohler.module.bean.StoreListBean;
import com.mengyang.kohler.module.bean.SystemMsgBean;
import com.mengyang.kohler.module.bean.UploadHeadPortraitBean;
import com.mengyang.kohler.module.bean.UserMsgBean;
import com.mengyang.kohler.module.bean.VisionBean;
import com.mengyang.kohler.module.bean.WeeklyRadioConcertBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
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
    int DEFAULT_TIMEOUT = 50000;
//    String API_SERVER_URL = "https://app.kohler.com.cn/"; //客户正式环境
    //    String API_SERVER_URL = "https://staging.app.kohler.com.cn/"; //客户测试
    //    String API_SERVER_URL = "https://kohler-app.glor.cn/"; //项目正式环境
        String API_SERVER_URL = "https://kohler-app-tmp.glor.cn/"; //项目测试环境
    //    String API_SERVER_URL = "http://www.kohler.com.cn/"; //地址

    //用户首页KV图
    @FormUrlEncoded
    @POST(Config.HOME_INDEX)
    Observable<BasicResponse<HomeIndexBean>> getUserHomeKV(@FieldMap Map<String, Object> map);

    //    //经销商用户首页KV图
    //    @FormUrlEncoded
    //    @POST(Config.DEALER_HOME_KV)
    //    Observable<BasicResponse<List<UserHomeKVBean>>> getDealerHomeKV(@FieldMap Map<String, String> map);
    //
    //    //设计师用户首页KV图
    //    @FormUrlEncoded
    //    @POST(Config.DESIGNER_HOME_KV)
    //    Observable<BasicResponse<List<UserHomeKVBean>>> getDesignerHomeKV(@FieldMap Map<String, String> map);

    //用户注册
    @FormUrlEncoded
    @POST(Config.USER_REGISTER)
    Observable<BasicResponse> getUserRegister(@FieldMap Map<String, Object> map);

    //登陆
    @FormUrlEncoded
    @POST(Config.APP_LOGIN)
    Observable<BasicResponse<LoginBean>> getLogin(@FieldMap Map<String, Object> map);

    //登陆验证码
    @FormUrlEncoded
    @POST(Config.LOGIN_VERIFICATION_IMG)
    Observable<BasicResponse> getLoginVerificationImg(@FieldMap Map<String, Object> map);

    //更新access_token
    @FormUrlEncoded
    @POST(Config.REFRESH_TOKEN)
    Observable<BasicResponse<RefreshTokenBean>> getRefreshToken(@FieldMap Map<String, Object> map);

    //设备注册
    @FormUrlEncoded
    @POST(Config.EQUIPMENT_REQISTRATION)
    Observable<BasicResponse> getEquipmentReqistration(@FieldMap Map<String, Object> map);

    //退出登录
    @FormUrlEncoded
    @POST(Config.USER_GO_OUT)
    Observable<BasicResponse> getUserGoOut(@FieldMap Map<String, Object> map);

    //用户解绑手机
    @FormUrlEncoded
    @POST(Config.USER_CANCEL_BIND_PHONE)
    Observable<BasicResponse> getCancelBindPhone(@FieldMap Map<String, Object> map);

    //用户绑定手机
    @FormUrlEncoded
    @POST(Config.USER_BIND_PHONE)
    Observable<BasicResponse> getBindPhone(@FieldMap Map<String, Object> map);

    //用户换绑手机
    @FormUrlEncoded
    @POST(Config.USER_MODIFY_BIND_PHONE)
    Observable<BasicResponse> getModifyBindPhone(@FieldMap Map<String, Object> map);

    //系统消息
    @FormUrlEncoded
    @POST(Config.SYSTEM_MSG)
    Observable<BasicResponse<SystemMsgBean>> getSystemMsg(@FieldMap Map<String, Object> map);

    //修改头像
    @FormUrlEncoded
    @POST(Config.MODIFY_HEAD_PORTRAIT)
    Observable<BasicResponse> getModifyHeadPortrait(@FieldMap Map<String, Object> map);

    //上传头像
    @Multipart
    @POST(Config.UPLOAD_HEAD_PORTRAIT)
    Observable<BasicResponse<UploadHeadPortraitBean>> getUploadHeadPortrait(@Part List<MultipartBody.Part> image);

    //修改昵称
    @FormUrlEncoded
    @POST(Config.MODIFY_NIKE_NAME)
    Observable<BasicResponse> getModifyNikeName(@FieldMap Map<String, Object> map);

    //忘记密码
    @FormUrlEncoded
    @POST(Config.FORGET_PWD)
    Observable<BasicResponse> getForgetPwd(@FieldMap Map<String, Object> map);

    //修改密码
    @FormUrlEncoded
    @POST(Config.MODIFY_PWD)
    Observable<BasicResponse> getModifyPwd(@FieldMap Map<String, Object> map);

    //获取用户预约信息
    @FormUrlEncoded
    @POST(Config.USER_RESERVE_MSG)
    Observable<BasicResponse<ReservationQueryBean>> getUserReserveMsg(@FieldMap Map<String, Object> map);

    //用户添加收藏
    @FormUrlEncoded
    @POST(Config.ADD_LIKE)
    Observable<BasicResponse> getAddLike(@FieldMap Map<String, Object> map);

    //用户取消收藏
    @FormUrlEncoded
    @POST(Config.CANCEL_LIKE)
    Observable<BasicResponse> getCancelLike(@FieldMap Map<String, Object> map);

    //用户收藏列表
    @FormUrlEncoded
    @POST(Config.LIKE_LIST)
    Observable<BasicResponse<LikeListBean>> getLikeList(@FieldMap Map<String, Object> map);

    //足迹列表
    @FormUrlEncoded
    @POST(Config.FOOT_PRINT)
    Observable<BasicResponse<FootPrintBean>> getFootPrint(@FieldMap Map<String, Object> map);

    //附近店铺
    @FormUrlEncoded
    @POST(Config.STORE_LIST)
    Observable<BasicResponse<StoreListBean>> getStoreList(@FieldMap Map<String, Object> map);

    //精选分类
    @FormUrlEncoded
    @POST(Config.SELECT_CLASSIFICATION)
    Observable<BasicResponse<List<SelectClassificationBean>>> getSelectClassification(@FieldMap Map<String, Object> map);

    //非精选分类
    @FormUrlEncoded
    @POST(Config.COMMODITY_CLASSIFICATION)
    Observable<BasicResponse<List<NotSelectClassificationBean>>> getNotSelectClassification(@FieldMap Map<String, Object> map);

    //商品分类顶部导航栏
    @FormUrlEncoded
    @POST(Config.COMMODITY_CLASSIFICATION_TITLE)
    Observable<BasicResponse<List<CommodityClassificationTitleBean>>> getCommodityClassificationTitle(@FieldMap Map<String, Object> map);

    //对应商品分类顶部导航栏的Fragment
    @FormUrlEncoded
    @POST(Config.COMMODITY_CLASSIFICATION_BODY)
    Observable<BasicResponse<CommodityClassificationFragmentBean>> getCommodityClassificationBody(@FieldMap Map<String, Object> map);

    //商品详情
    @FormUrlEncoded
    @POST(Config.COMMODITY_DETAILS)
    Observable<BasicResponse<List<CommodityDetailsBean>>> getCommodityDetails(@FieldMap Map<String, Object> map);

    //经销商大会页数据
    @FormUrlEncoded
    @POST(Config.MEETING)
    Observable<BasicResponse<MeetingBean>> getMeeting(@FieldMap Map<String, Object> map);

    //2018上海厨卫展
    //    @FormUrlEncoded
    @GET(Config.KBIS)
    Observable<BasicResponse<KbisBean>> getKbis();

    //获取经销商大会推送是否开启的用户设置
    @FormUrlEncoded
    @POST(Config.MEETING_USER_SETTINGS_MODIFY)
    Observable<BasicResponse> getMeetingUserSettingsModify(@FieldMap Map<String, Object> map);

    //经销商大会现场实时投票
    @FormUrlEncoded
    @POST(Config.MEETING_LIVE_REAL_TIME)
    Observable<BasicResponse<LiveRealTimeBean>> getMeetingLiveRealTime(@FieldMap Map<String, Object> map);

    //销商大会照片点赞
    @FormUrlEncoded
    @POST(Config.MEETING_LIKE_PICTURE)
    Observable<BasicResponse> getMeetingLikePicture(@FieldMap Map<String, Object> map);

    //全文搜索
    @FormUrlEncoded
    @POST(Config.ALL_SEARCH)
    Observable<BasicResponse<AllSearchBean>> getAllSearch(@FieldMap Map<String, Object> map);

    //我的手册
    @FormUrlEncoded
    @POST(Config.BOOKS_LIST)
    Observable<BasicResponse<BooksListBean>> getBooksList(@FieldMap Map<String, Object> map);

    //问题搜索
    @FormUrlEncoded
    @POST(Config.QUESTION_SEARCH)
    Observable<BasicResponse<QuestionSearchBean>> questionSearch(@FieldMap Map<String, Object> map);

    //用户信息
    @FormUrlEncoded
    @POST(Config.USER_MSG)
    Observable<BasicResponse<UserMsgBean>> getUserMsg(@FieldMap Map<String, Object> map);

    //登录短信验证
    @FormUrlEncoded
    @POST(Config.LOGIN_SMS)
    Observable<BasicResponse> getLoginSMS(@FieldMap Map<String, Object> map);

    //星广会内容列表
    @FormUrlEncoded
    @POST(Config.WEEKLY_RADIO_CONCERT)
    Observable<BasicResponse<WeeklyRadioConcertBean>> getWeeklyRadioConcert(@FieldMap Map<String, Object> map);

    //科勒艺术
    @FormUrlEncoded
    @POST(Config.ART_KOHLER)
    Observable<BasicResponse<ArtKohlerBean>> getArtKohler(@FieldMap Map<String, Object> map);

    //敢创•科勒亚太艺术展
    @FormUrlEncoded
    @POST(Config.GAN_CHUANG)
    Observable<BasicResponse<ArtKohlerBean>> getGanChuang(@FieldMap Map<String, Object> map);

    //科勒艺术精选图片
    @FormUrlEncoded
    @POST(Config.ART_KOHLER_SELECT_IMG)
    Observable<BasicResponse<ArtKohlerSelectImgBean>> getArtKohlerSelectImg(@FieldMap Map<String, Object> map);

    //检查更新
    @FormUrlEncoded
    @POST(Config.CHECK_UP)
    Observable<BasicResponse<VisionBean>> getCheckUp(@FieldMap Map<String, Object> map);

    //预约地址
    @GET(Config.APPOINTMENT_PATH)
    Observable<BasicResponse<AddressBean>> getAddress();// TODO: 2018/5/19 ,retrofit的BaseURL需要做处理。
}
