package com.mengyang.kohler.common.net;

/**
 * Description : 公共参数配置类
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/9/22
 */

public class IConstants {
    //包名
    public static final String PACKAGE_NAME = "com.mengyang.kohler";
    /**
    * 共享参数
    */
    public static final String TOKEN = "token"; //token
    public static final String REFRESH_TOKEN = "refreshtoken"; //refreshtoken
    public static final String FIRST_APP = "isFirstApp"; //第一次进应用
    public static final String IS_LOGIN = "isLogin"; //已经登录
    public static final String TYPE = "type"; //用户身份
    public static final String POEN_ID = "openId"; //用户唯一标识
    public static final String USER_ID = "userId"; //用户uid
    public static final String JPUSH_SYSTEM_ID = "registrationId"; //极光系统id

    /**
     * 请求码 从60开始
     */
    public static final int DELETE_REQUESTCODE = 60;

    /**
     * 实例化常量
     */
    public static final long COMMODITY_CLASSIFICATION_FRAGMENT_BEAN = 1L;
    public static final long RESULT_LIST_BEAN = 2L;
    public static final long PRO_DETAIL_BEAN = 3L;
    public static final long ATTR_LIST_BEAN = 4L;
    public static final long PDF_LIST_BEAN = 5L;
    public static final long SKU_ATTR_LIST_BEAN = 6L;


}
