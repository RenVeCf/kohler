package com.mengyang.kohler.common.net;

/**
 * Description : 接口 URL
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/loading3
 */

public interface Config  {

    /**
     *  登录
     */
    String USER_REGISTER = "authz/account/register"; //普通用户注册
    String APP_LOGIN = "authz/account/login"; //登录
    String LOGIN_VERIFICATION_IMG = "authz/account/getCode"; //登录验证码图片
    String REFRESH_TOKEN = "authz/token/exchange"; // 更新access_token
    String EQUIPMENT_REQISTRATION = "authz/device/register"; //设备注册
    String USER_GO_OUT = "authz/account/logout"; //用户退出
    String USER_CANCEL_BIND_PHONE = "authz/account/unbindMobile"; //用户解绑手机
    String USER_BIND_PHONE = "authz/account/bindMobile"; //用户手机绑定
    String USER_MODIFY_BIND_PHONE = "authz/account/updateBindMobile"; //用户换绑手机
    String FORGET_PWD = "authz/account/forgetPwd"; //忘记密码
    String MODIFY_PWD = "authz/account/updatePwd"; //修改密码

    /**
     * 首页
     */
    String HOME_INDEX = "index/index"; //首页
    String STORE_LIST = "store/list"; //附近店铺
    String ALL_SEARCH = "productSearch/solrByStr"; //全文搜索
    String MEETING = "ndc/facadeGetMeetingAndAgenda"; //经销商大会页数据
    String MEETING_LIVE_REAL_TIME = "ndc/getPictureList"; //经销商大会现场实时投票
    String BOOKS_LIST = "handBook/getHandBooks"; //手册列表
    String MEETING_USER_SETTINGS = "user/data/get"; //获取经销商大会用户设置
    String MEETING_USER_SETTINGS_MODIFY = "user/data/update"; //修改经销商大会用户设置
    String MEETING_LIKE_PICTURE = "ndc/likePicture"; //经销商大会照片点赞
    String MEETING_BARRAGE = "http://106.15.92.74/api/v1/danmu/send?access_token="; //弹幕

    /**
     * 全品类
     */
    String COMMODITY_SEARCH = "productSearch/searchBySku"; //商品redis查找
    String SELECT_CLASSIFICATION = "category/selectionList"; //获得精选分类下所有分类
    String COMMODITY_CLASSIFICATION = "category/listWithoutSelection";//获得所有非精选分类主界面
    String COMMODITY_CLASSIFICATION_TITLE = "category/childList";//商品分类顶部导航栏
    String COMMODITY_CLASSIFICATION_BODY = "productSearch/searchByCate";//对应商品分类顶部导航栏的Fragment
    String COMMODITY_DETAILS = "productSearch/searchGroupBySku";//商品详情
    String ADD_LIKE = "product/insertFavorite"; //用户添加收藏

    /**
     * 账户
     */
    String USER_RESERVE_MSG = "appointment/info"; //获取用户预约信息
    String CANCEL_LIKE = "product/deleteFavorite"; //用户取消收藏
    String LIKE_LIST = "product/favoriteList"; //用户收藏列表
    String MODIFY_HEAD_PORTRAIT = "authz/account/updatePortrait"; //修改头像
    String UPLOAD_HEAD_PORTRAIT = "file/uploadMedia"; //上传头像
    String MODIFY_NIKE_NAME = "authz/account/updateNickName"; //修改昵称
    String FOOT_PRINT = "product/recordList"; //足迹列表
    String USER_MSG = "authz/account/getUserInfo"; //用户信息

    /**
     * 公共
     */
    String QUESTION_SEARCH = "question/search"; //客服问题搜索
    String SYSTEM_MSG = "system/message/list"; //系统消息列表
}
