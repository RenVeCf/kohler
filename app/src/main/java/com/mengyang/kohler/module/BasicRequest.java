package com.mengyang.kohler.module;

import com.mengyang.kohler.App;
import com.mengyang.kohler.common.utils.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/2/1
 */

public class BasicRequest {
    public String token = (String) SPUtil.get(App.getContext(), IConstants.TOKEN, "");

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
