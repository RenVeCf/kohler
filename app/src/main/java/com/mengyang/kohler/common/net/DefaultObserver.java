package com.mengyang.kohler.common.net;

import android.app.Activity;
import android.content.Intent;

import com.google.gson.JsonParseException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.account.activity.LoginActivity;
import com.mengyang.kohler.common.utils.CommonDialogUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.module.BasicResponse;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.mengyang.kohler.common.net.DefaultObserver.ExceptionReason.CONNECT_ERROR;
import static com.mengyang.kohler.common.net.DefaultObserver.ExceptionReason.CONNECT_TIMEOUT;
import static com.mengyang.kohler.common.net.DefaultObserver.ExceptionReason.PARSE_ERROR;
import static com.mengyang.kohler.common.net.DefaultObserver.ExceptionReason.UNKNOWN_ERROR;

/**
 * 网络请求结果处理
 * Created by zhpan on 2017/4/18.
 */

public abstract class DefaultObserver<T extends BasicResponse> implements Observer<T> {
    private Activity activity;
    //  Activity 是否在执行onStop()时取消订阅
    private boolean isAddInStop = false;
    private Disposable disposable;
    private CommonDialogUtils dialogUtils;
    /**
     * 请求成功
     */
    public final static String REQUEST_SUCCESS = "200";
    /**
     * token错误
     */
    public final static String TOKEN_INCORRECT = "201";
    /**
     * token过期
     */
    public final static String TOKEN_EXPIRED = "202";

    /**
     * refresh_token过期
     */
    public final static String REFRESH_TOKEN_EXPIRED = "1000002";

    public DefaultObserver(Activity activity, boolean isShowLoading) {
        this.activity = activity;
        dialogUtils = new CommonDialogUtils();
        if (isShowLoading) {
            dialogUtils.showProgress(activity, "Loading...");
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposable = d;
    }

    @Override
    public void onNext(T response) {
        dismissProgress();
        if (response.getError().equals(REQUEST_SUCCESS)) {
            onSuccess(response);
        } else {
            if (response.getError().equals("")) {
                onFail(response, "12345");
            } else {
                onFail(response, response.getError());
            }
        }
    }

    private void dismissProgress() {
        if (dialogUtils != null) {
            dialogUtils.dismissProgress();
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissProgress();
        if (e instanceof HttpException) {     //   HTTP错误
            onException(ExceptionReason.BAD_NETWORK);
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {   //   连接错误
            onException(CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {   //  连接超时
            onException(CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {   //  解析错误
            onException(PARSE_ERROR);
        } else {
            onException(UNKNOWN_ERROR);
        }
    }

    @Override
    public void onComplete() {
    }

    /**
     * 请求成功
     *
     * @param response 服务器返回的数据
     */
    abstract public void onSuccess(T response);

    /**
     * 服务器返回数据，但响应码不为200
     *
     * @param response 服务器返回的数据
     */
    public void onFail(T response, String code) {
        String message = response.getMessage();
        LogUtils.i("rmy", "code = " + code);
        switch (code) {
            case TOKEN_EXPIRED: //  token过期 刷新token
                LogUtils.i("rmy", "TOKEN_EXPIRED");
                refreshToken();
                break;
            case REFRESH_TOKEN_EXPIRED:// refresh_token过期
                LogUtils.i("rmy", "REFRESH_TOKEN_EXPIRED");
            case TOKEN_INCORRECT:// token错误重新登录
                LogUtils.i("rmy", "TOKEN_INCORRECT");
                SPUtil.put(activity, IConstants.IS_LOGIN, false);
                SPUtil.put(activity, IConstants.TOKEN, "");
                SPUtil.put(activity, IConstants.REFRESH_TOKEN, "");
                SPUtil.put(activity, IConstants.TYPE, "");
                SPUtil.put(activity, IConstants.USER_NIKE_NAME, App.getContext().getResources().getString(R.string.login_or_register));
                SPUtil.put(activity, IConstants.USER_HEAD_PORTRAIT, "");
                activity.startActivity(new Intent(activity, LoginActivity.class));
                activity.finish();
                break;
            default:
                LogUtils.i("rmy", "default");
                ToastUtil.showToast(message);
                break;
        }
    }

    //  刷新token
    private void refreshToken() {
        new RequestHelper((BaseActivity) activity, new RequestHelper.RequestCallback() {
            @Override
            public void onTokenUpdateSucceed() {
                onTokenUpdateSuccess();
            }
        }).refreshToken();
    }

    //  刷新token成功，此方法需要在请求网络时重写
    public void onTokenUpdateSuccess() {
    }

    /**
     * 请求异常
     *
     * @param reason
     */
    public void onException(ExceptionReason reason) {
        switch (reason) {
            case CONNECT_ERROR:
                ToastUtil.showToast(App.getContext().getString(R.string.connect_error));
                break;

            case CONNECT_TIMEOUT:
                ToastUtil.showToast(App.getContext().getString(R.string.connect_timeout));
                break;

            case BAD_NETWORK:
                ToastUtil.showToast(App.getContext().getString(R.string.bad_network));
                break;

            case PARSE_ERROR:
                ToastUtil.showToast(App.getContext().getString(R.string.parse_error));
                break;

            case UNKNOWN_ERROR:
            default:
                ToastUtil.showToast(App.getContext().getString(R.string.unknown_error));
                break;
        }
    }

    /**
     * 请求网络失败原因
     */
    public enum ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR,
    }
}
