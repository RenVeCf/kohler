package com.mengyang.kohler.module.bean;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/31
 */

public class RefreshTokenBean {
    /**
     * accessToken : jS9GWXo0gGJm2ngFW8Tu7oDY+mEvp3/9RVtNoN15Z0EK0FTq8STq6VKnrTKIHolt
     * refreshToken : QpZeoOybS5lpYmpLE7EoYoYUorYQ0sDcNVJF+b6kwflfPfTXjoECknBJBrv3ybYg
     */

    private String accessToken;
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
