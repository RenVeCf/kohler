package com.mengyang.kohler.account.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.mengyang.kohler.App;
import com.mengyang.kohler.BaseActivity;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.DefaultObserver;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.net.IdeaApi;
import com.mengyang.kohler.common.utils.AppUtils;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.common.view.TopView;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.main.view.CommonProgressDialog;
import com.mengyang.kohler.module.BasicResponse;
import com.mengyang.kohler.module.bean.VisionBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 我的账户——设置
 */

public class AccountSettingsActivity extends BaseActivity {
    // 这是一个外包小公司，在甲方公司里办公的...别被忽悠了
    // 这家老板人品不好，进来说好交社保，但实际上不给你交，工资能拖就拖(有次一拖就3个月)
    // 说好的有年终奖也是没有的别想了，过节没有任何东西发，自己带电脑也没有补贴金的~
    // 这公司管理混乱趁早走吧兄弟...

    @BindView(R.id.tv_account_settings_top)
    TopView tvAccountSettingsTop;
    @BindView(R.id.tv_account_settings_about)
    TextView tvAccountSettingsAbout;
    @BindView(R.id.tv_account_settings_checkUp)
    TextView tvAccountSettingsCheckUpz;
    @BindView(R.id.tv_account_settings_privacy_policy)
    TextView tvAccountSettingsPrivacyPolicy;
    @BindView(R.id.tv_account_settings_modify_pwd)
    TextView tvAccountSettingsModifyPwd;
    @BindView(R.id.tv_account_settings_modify_bind_phone)
    TextView tvAccountSettingsModifyBindPhone;
    @BindView(R.id.bt_account_settings_sign_out)
    Button btAccountSettingsSignOut;
    private List<VisionBean.ResultListBean> mVisionBean = new ArrayList<>();
    private boolean is_Login = (boolean) SPUtil.get(App.getContext(), IConstants.IS_LOGIN, false);
    private CommonProgressDialog pBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_settings;
    }

    @Override
    protected void initValues() {
        App.getManager().addActivity(this);
        //防止状态栏和标题重叠
        ImmersionBar.setTitleBar(this, tvAccountSettingsTop);
        if (is_Login) {
            btAccountSettingsSignOut.setText(App.getContext().getResources().getString(R.string.sign_out));
            tvAccountSettingsModifyPwd.setClickable(true);
            tvAccountSettingsModifyPwd.setTextColor(App.getContext().getResources().getColor(R.color.is_login_settings));
            tvAccountSettingsModifyBindPhone.setClickable(true);
            tvAccountSettingsModifyBindPhone.setTextColor(App.getContext().getResources().getColor(R.color.is_login_settings));
        } else {
            btAccountSettingsSignOut.setText(App.getContext().getResources().getString(R.string.login));
            tvAccountSettingsModifyPwd.setClickable(false);
            tvAccountSettingsModifyPwd.setTextColor(App.getContext().getResources().getColor(R.color.no_login_settings));
            tvAccountSettingsModifyBindPhone.setClickable(false);
            tvAccountSettingsModifyBindPhone.setTextColor(App.getContext().getResources().getColor(R.color.no_login_settings));
        }
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData() {

    }

    private void UserGoOut() {
        Map<String, Object> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getUserGoOut(stringMap)
                .compose(this.<BasicResponse>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        SPUtil.put(App.getContext(), IConstants.IS_LOGIN, false);
                        SPUtil.put(App.getContext(), IConstants.TOKEN, "");
                        SPUtil.put(App.getContext(), IConstants.REFRESH_TOKEN, "");
                        SPUtil.put(App.getContext(), IConstants.TYPE, "");
                        SPUtil.put(App.getContext(), IConstants.USER_NIKE_NAME, App.getContext().getResources().getString(R.string.login_or_register));
                        SPUtil.put(App.getContext(), IConstants.USER_HEAD_PORTRAIT, "");
                        App.destoryActivity("MainActivity");
                        startActivity(new Intent(AccountSettingsActivity.this, LoginActivity.class));
                        finish();
                    }
                });
    }

    @OnClick({R.id.tv_account_settings_about, R.id.tv_account_settings_checkUp, R.id.tv_account_settings_privacy_policy, R.id.tv_account_settings_modify_pwd, R.id.tv_account_settings_modify_bind_phone, R.id.bt_account_settings_sign_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_settings_about:
                startActivity(new Intent(this, AccountAboutActivity.class));
                break;
            case R.id.tv_account_settings_checkUp:
                checkUp();
                break;
            case R.id.tv_account_settings_privacy_policy:
                startActivity(new Intent(this, AccountPrivacyPolicyActivity.class));
                break;
            case R.id.tv_account_settings_modify_pwd:
                startActivity(new Intent(this, ModifyPwdActivity.class));
                break;
            case R.id.tv_account_settings_modify_bind_phone:
                startActivity(new Intent(this, AccountBindPhoneActivity.class));
                break;
            case R.id.bt_account_settings_sign_out:
                if ((boolean) SPUtil.get(this, IConstants.IS_LOGIN, false))
                    UserGoOut();
                else {
                    startActivity(new Intent(AccountSettingsActivity.this, LoginActivity.class));
                    finish();
                }
                break;
        }
    }

    private void checkUp() {
        Map<String, Object> stringMap = IdeaApi.getSign();

        IdeaApi.getRequestLogin(stringMap);
        IdeaApi.getApiService()
                .getCheckUp(stringMap)
                .compose(AccountSettingsActivity.this.<BasicResponse<VisionBean>>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse<VisionBean>>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse<VisionBean> response) {
                        mVisionBean = response.getData().getResultList();
                        // 获取本版本号，是否更新
                        String vision = AppUtils.getVersionName(AccountSettingsActivity.this);

                        if (mVisionBean.get(1).getDictDesc().equals("android") && !vision.equals(mVisionBean.get(1).getDictName())) {
                            String content = "\n" + "科勒应用有新的版本 v" + mVisionBean.get(1).getDictName() + "。\n";//更新内容
                            getVersion(vision, mVisionBean.get(1).getDictName(), content);
                        } else {
                            ToastUtil.showToast("您已经是最新版本！");
                        }
                    }
                });
    }

    // 获取更新版本号
    private void getVersion(final String vision, String newversion, String content) {
        String url = "http://openbox.mobilem.360.cn/index/d/sid/3976114";//安装包下载地址

        if (!newversion.equals(vision)) {
            // 版本号不同
            ShowDialog(vision, newversion, content, url);
        }
    }

    /**
     * 升级系统
     *
     * @param content
     * @param url
     */
    private void ShowDialog(String vision, String newversion, String content,
                            final String url) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("版本更新")
                .setMessage(content)
                .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        pBar = new CommonProgressDialog(AccountSettingsActivity.this);
                        pBar.setIndeterminate(true);
                        pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        //                        pBar.setCanceledOnTouchOutside(false);
                        // downFile(URLData.DOWNLOAD_URL);
                        final AccountSettingsActivity.DownloadTask downloadTask = new AccountSettingsActivity.DownloadTask(
                                AccountSettingsActivity.this);
                        downloadTask.execute(url);
                        pBar.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                downloadTask.cancel(true);
                            }
                        });
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.getButton(CommonProgressDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.background_color));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorHint));
    }

    /**
     * 下载应用
     *
     * @author Administrator
     */
    class DownloadTask extends AsyncTask<String, Integer, String> {

        private Context context;
        private PowerManager.WakeLock mWakeLock;

        public DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... sUrl) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            File file = null;
            try {
                URL url = new URL(sUrl[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                // expect HTTP 200 OK, so we don't mistakenly save error
                // report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP "
                            + connection.getResponseCode() + " "
                            + connection.getResponseMessage();
                }
                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    file = new File(AccountSettingsActivity.this.getExternalCacheDir().getPath() + File.separator + "app",
                            IConstants.DOWNLOAD_NAME);

                    if (!file.exists()) {
                        // 判断父文件夹是否存在
                        if (!file.getParentFile().exists()) {
                            file.getParentFile().mkdirs();
                        }
                    }
                } else {
                    Toast.makeText(AccountSettingsActivity.this, "sd卡未挂载",
                            Toast.LENGTH_LONG).show();
                }
                input = connection.getInputStream();
                output = new FileOutputStream(file);
                byte data[] = new byte[4096];
                long total = 0;
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    total += count;
                    // publishing the progress....
                    if (fileLength > 0) // only if total length is known
                        publishProgress((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }
                if (connection != null)
                    connection.disconnect();
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // take CPU lock to prevent CPU from going off if the user
            // presses the power button during download
            PowerManager pm = (PowerManager) context
                    .getSystemService(Context.POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                    getClass().getName());
            mWakeLock.acquire();
            pBar.show();
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            // if we get here, length is known, now set indeterminate to false
            pBar.setIndeterminate(false);
            pBar.setMax(100);
            pBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            mWakeLock.release();
            pBar.dismiss();
            update();
        }
    }

    private void update() {
        //安装应用
        String apkPath = AccountSettingsActivity.this.getExternalCacheDir().getPath() + File.separator + "app" + File.separator;
        if (TextUtils.isEmpty(apkPath)) {
            Toast.makeText(AccountSettingsActivity.this, "更新失败！未找到安装包", Toast.LENGTH_SHORT).show();
            return;
        }
        File apkFile = new File(apkPath + IConstants.DOWNLOAD_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Android 7.0 系统共享文件需要通过 FileProvider 添加临时权限，否则系统会抛出 FileUriExposedException .
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(AccountSettingsActivity.this, "com.mengyang.kohler.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        AccountSettingsActivity.this.startActivity(intent);
    }
}
