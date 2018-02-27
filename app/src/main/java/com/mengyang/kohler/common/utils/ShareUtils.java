package com.mengyang.kohler.common.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.mengyang.kohler.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by liusong on 2017/11/27.
 */

public class ShareUtils {
    private static final String TAG = "ShareUtils";
    private final Activity mActivity;
    private final String mUrl;
    private int screenHeight;
    private int screenWidth;
    private View share_dialog;
    private Dialog showDialogToClearCache;
    private String mFoodBrand;
    private ShareSuccess mShareListener;
    public ShareUtils(Activity mActivity, String url) {
        this.mActivity = mActivity;
        this.mUrl = url;
    }


    /**
     * 弹出自定义的对话框进行分享
     * @param foodBrand，分享商品的名字
     */
    public void popShare(String foodBrand) {
        mFoodBrand = foodBrand;
        screenHeight =getScreenHeight(mActivity);
        screenWidth = getScreenWidth(mActivity);

        if (share_dialog == null) {
            share_dialog = LayoutInflater.from(mActivity).inflate(
                    R.layout.dialog_share, null);
        }

        if (showDialogToClearCache == null) {
            showDialogToClearCache = new Dialog(mActivity, R.style.CommentItemDelete);
            showDialogToClearCache.setContentView(share_dialog);
            WindowManager.LayoutParams p = showDialogToClearCache
                    .getWindow().getAttributes();

            p.height = screenHeight; // 高度设置为屏幕的0.3
            p.width = screenWidth; // 宽度设置为屏幕的0.5
            showDialogToClearCache.getWindow().setAttributes(p); // 设置生效
            showDialogToClearCache.setCanceledOnTouchOutside(false);

            ImageView share_wechat = (ImageView) share_dialog.findViewById(R.id.iv_share_winxi);
            share_wechat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    share(SHARE_MEDIA.WEIXIN);
                }
            });


        }
        showDialogToClearCache.show();

    }


    private void share(SHARE_MEDIA Plat) {
//        UMImage thumb =  new UMImage(mActivity, mUrl);
        UMImage thumb =  new UMImage(mActivity, R.mipmap.logo);
        UMWeb web = new UMWeb(mUrl);

        web.setTitle(mActivity.getString(R.string.app_name));//标题
        web.setThumb(thumb);                                    //缩略图
        if (mFoodBrand.equals("")) {                            //代表不需要二外的传递什么信息
            web.setDescription(mActivity.getString(R.string.share_describe));//描述
        } else {//分享商品的名字
            web.setDescription(mFoodBrand);//描述
        }



        //开启自定义分享页面
        UMImage image = new UMImage(mActivity, mUrl);
//        UMImage image = new UMImage(context, "http://tshare.glor.cn/share-card.html");
        image.compressStyle = UMImage.CompressStyle.SCALE;//大小压缩，默认为大小压缩，适合普通很大的图
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        //压缩格式设置
        image.compressFormat = Bitmap.CompressFormat.PNG;//用户分享透明背景的图片可以设置这种方式，但是qq好友，微信朋友圈，不支持透明背景图片，会变成黑色

        new ShareAction(mActivity)
                .setPlatform(Plat)
                .setCallback(umShareListener)
//                .withText("hello world!")
                .withMedia(web)
                .share();
    }

    private static void copy(InputStream in, OutputStream out)
            throws IOException {
        byte[] b = new byte[1024];
        int read;
        while ((read = in.read(b)) != -1) {
            out.write(b, 0, read);
        }
    }

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }
    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private UMShareListener umShareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Log.i(TAG, "分享onStart");
            if (mShareListener != null) {
                mShareListener.onShareStart();
            }
            hideDialog();
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            ToastUtil.showToast(mActivity.getString(R.string.share_success));
            Log.i(TAG, "分享成功了");
            hideDialog();
            if (mShareListener != null) {
                mShareListener.onShareSuccess();
            }
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Log.i(TAG, "分享失败");
            hideDialog();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.i(TAG, "分享被取消了");
            hideDialog();
        }
    };

    private void hideDialog() {
        if (showDialogToClearCache != null) {
            showDialogToClearCache.dismiss();
        }
    }

    public interface ShareSuccess {
        void onShareSuccess();
        void onShareStart();
    }

    public void setUmShareListener(ShareSuccess shareListener ) {
        mShareListener = shareListener;
    }
}
