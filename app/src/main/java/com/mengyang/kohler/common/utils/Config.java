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
    String EQUIPMENT_REQISTRATION = "authz/device/register"; //设备注册
    String USER_GO_OUT = "authz/account/logout"; //用户退出
}
