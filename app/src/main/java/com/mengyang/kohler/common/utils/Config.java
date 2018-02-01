package com.mengyang.kohler.common.utils;

/**
 * Description : 接口 URL
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/loading3
 */

public interface Config  {

    /**
     * 首页
     */
    String BOOK_LIST = "authz/handBook/list"; //手册列表
    /**
     *  账户
     */
    String USER_REGISTER = "authz/account/register"; //普通用户注册
    String APP_LOGIN = "authz/account/login"; //登陆
    String REFRESH_TOKEN = "authz/token/exchange"; // 更新access_token
    String EQUIPMENT_REQISTRATION = "authz/device/register"; //设备注册
    String USER_GO_OUT = "authz/account/logout"; //用户退出
    String USER_CANCEL_BIND_PHONE = "authz/account/unbindMobile"; //用户解绑手机
    String USER_BIND_PHONE = "authz/account/bindMobile"; //用户手机绑定
    String USER_MODIFY_BIND_PHONE = "authz/account/updateBindMobile"; //用户换绑手机
    String SYSTEM_MSG = "system/message/list"; //系统消息列表
    String MODIFY_HEAD_PORTRAIT = "authz/account/updatePortrait"; //修改头像
    String UPLOAD_HEAD_PORTRAIT = "file/uploadMedia"; //上传头像
    String FORGET_PWD = "authz/account/forgetPwd"; //忘记密码
    String USER_RESERVE_MSG = "appointment/info"; //获取用户预约信息
    String AddLike = "product/insertFavorite"; //用户添加收藏
    String CANCEL_Like = "product/deleteFavorite"; //用户取消收藏
    String LikeList = "product/favoriteList"; //用户收藏列表
}
