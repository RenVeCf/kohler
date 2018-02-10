package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/10
 */

public class UserMsgBean {
    /**
     * hasRoles : dealer
     * mobileNo : 18511111114
     * nickName : 185****1114
     * portraitUrl : /storage/emulated/0/DCIM/Camera/IMG_20180101_152745_Bokeh.jpg
     * pushMsg : true
     * userId : 56
     * userName : 185****1114
     */

    private String hasRoles;
    private String mobileNo;
    private String nickName;
    private String portraitUrl;
    private boolean pushMsg;
    private int userId;
    private String userName;

    public String getHasRoles() {
        return hasRoles;
    }

    public void setHasRoles(String hasRoles) {
        this.hasRoles = hasRoles;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public boolean isPushMsg() {
        return pushMsg;
    }

    public void setPushMsg(boolean pushMsg) {
        this.pushMsg = pushMsg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
