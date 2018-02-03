package com.mengyang.kohler.common.net;

import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.RefreshTokenBean;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/1/31
 */

public class RequestHelper {
    private BaseActivity activity;
    private int times;  //  刷新token重连次数
    private RequestCallback callback;

    public RequestHelper(BaseActivity activity) {
        this.activity = activity;
    }


    public RequestHelper(BaseActivity activity, RequestCallback callback) {
        this.activity = activity;
        this.callback = callback;
    }

    //  刷新token
    public void refreshToken() {
        Map<String, String> stringMap = IdeaApi.getSign();
        stringMap.put("refreshToken ", IConstants.REFRESH_TOKEN);

        IdeaApi.getApiService()
                .getRefreshToken(stringMap)
                .subscribeOn(Schedulers.io())
                .compose(activity.<BasicResponse<RefreshTokenBean>>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<RefreshTokenBean>>(activity, false) {
                    @Override
                    public void onSuccess(BasicResponse<RefreshTokenBean> response) {
                        SPUtil.put(activity, IConstants.TOKEN, response.getData().getAccessToken());
                        SPUtil.put(activity, IConstants.REFRESH_TOKEN, response.getData().getRefreshToken());
                        if (null != callback) {
                            callback.onTokenUpdateSucceed();
                        }
                    }
                });
    }


    public interface RequestCallback {
        /**
         * token验证完成，API访问已经ready
         */
        void onTokenUpdateSucceed();
    }
}