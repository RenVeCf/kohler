package com.mengyang.kohler.common.net;

import com.mengyang.kohler.BaseActivity;

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

    //    public void login() {
    //        LoginRequest loginRequest = new LoginRequest(activity);
    //        IdeaApi.getApiService()
    //                .login(loginRequest)
    //                .subscribeOn(Schedulers.io())
    //                .compose(activity.<BasicResponse<LoginBean>>bindToLifecycle())
    //                .observeOn(AndroidSchedulers.mainThread())
    //                .subscribe(new DefaultObserver<BasicResponse<LoginBean>>(activity) {
    //                    @Override
    //                    public void onSuccess(BasicResponse<LoginBean> response) {
    //                        LoginBean results = response.getData();
    //                        /**
    //                         * 可以将这些数据存储到User中，User存储到本地数据库
    //                         */
    //                        SPUtil.put(App.getContext(), IConstants.TOKEN, response);
    //                        SPUtil.put(App.getContext(), IConstants.REFRESH_TOKEN, response);
    //                    }
    //                });
    //    }


    //    //  刷新token
    //    public void refreshToken() {
    //        IdeaApi.getApiService()
    ////                .refreshToken(new RefreshTokenRequest())
    //                .subscribeOn(Schedulers.io())
    //                .compose(activity.<BasicResponse<RefreshTokenBean>>bindToLifecycle())
    //                .observeOn(AndroidSchedulers.mainThread())
    //                .subscribe(new DefaultObserver<BasicResponse<RefreshTokenBean>>(activity) {
    //                    @Override
    //                    public void onSuccess(BasicResponse<RefreshTokenBean> response) {
    //                        SPUtil.put(activity, IConstants.TOKEN, response.setAccessToken());
    //                        if (null != callback) {
    //                            callback.onTokenUpdateSucceed();
    //                        }
    //                    }
    //                });
    //    }


    public interface RequestCallback {
        /**
         * token验证完成，API访问已经ready
         */
        void onTokenUpdateSucceed();
    }
}
