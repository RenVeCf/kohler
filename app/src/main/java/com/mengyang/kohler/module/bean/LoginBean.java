package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/31
 */

public class LoginBean {
    /**
     * accessToken : sk4RDjJNixt3Z+pao7P7fh5mhcgHvRW2BsWpB+HVqR0Sgk8bvB5N0awh8jyXCszu
     * isNew : false
     * openId : 1yp5A1nL2uI=
     * refreshToken : dBy3mG3niUdUtBI2zY2mnAJnWF5CccZ/Q1hb2iWWFyEIRwSbKqAZsEGlEEFYGMtD
     */

    private String accessToken;
    private boolean isNew;
    private String openId;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public boolean isIsNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
