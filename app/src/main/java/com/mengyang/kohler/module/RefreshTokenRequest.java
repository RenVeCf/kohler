package com.mengyang.kohler.module;

import com.mengyang.kohler.App;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/1
 */

public class RefreshTokenRequest extends BasicRequest {
    private String refreshToken;

    public RefreshTokenRequest() {
        this.refreshToken = (String) SPUtil.get(App.getContext(), IConstants.REFRESH_TOKEN, "");
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
