package com.kohler.arscan;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kohler.arscan.constant.Config;
import com.kohler.arscan.util.LogManager;
import com.kohler.arscan.util.SPUtil;
import com.kohler.arscan.util.SharePreUtil;
import com.unity3d.player.UnityPlayer;
import com.xiuyukeji.pictureplayerview.PicturePlayerView;
import com.xiuyukeji.pictureplayerview.interfaces.OnErrorListener;
import com.xiuyukeji.pictureplayerview.interfaces.OnStopListener;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class UnityPlayerActivity extends Activity implements View.OnClickListener {

    private final static String TAG = UnityPlayerActivity.class.getSimpleName();

    public static UnityPlayerActivity mActivity;
    protected UnityPlayer mUnityPlayer; // don't change the name of this variable; referenced from native code
    private String flag;

    private PopupWindow playPop;
    private RelativeLayout rl_play;
    private TextView tv_zh;
    private TextView tv_en;
    private PicturePlayerView play_view;

    // Setup activity layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        // 设置显示窗口参数
        getWindow().setFormat(PixelFormat.RGBX_8888); // <--- This makes xperia play happy
        setContentView(R.layout.unity_view);
        ButterKnife.bind(this);

        mActivity = this;

        flag = getIntent().getStringExtra("flag");
        Log.e(TAG, "flag: " + flag);

        initView();
        initPop();
    }

    private void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.activity_play, null);
        view.findViewById(R.id.iv_play_cancel).setOnClickListener(this);
        rl_play = view.findViewById(R.id.rl_play);
        tv_zh = view.findViewById(R.id.tv_zh);
        tv_en = view.findViewById(R.id.tv_en);
        play_view = view.findViewById(R.id.play_view);

        playPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void initView() {
        LinearLayout ll_unity = findViewById(R.id.ll_unity);
        mUnityPlayer = new UnityPlayer(this);
        ll_unity.addView(mUnityPlayer);
        mUnityPlayer.requestFocus();
    }

    public void NoteBook(String string) {
        LogManager.e(TAG, "NoteBook: " + string);
        finish();

        // TODO: 2018/3/2 注释解开
        Intent intent = new Intent();
        if (((boolean) SPUtil.get(this, "isLogin", false))) {
            if (SPUtil.get(this, "no_type", "").equals("dealer") || SPUtil.get(this, "no_type", "").equals("designer")) {
                intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.home.activity.MineManualActivity");
                startActivity(intent);
            }
        } else {
            intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.account.activity.LoginActivity");
            startActivity(intent);
        }
    }

    //unity调用android方法，初始化参数
    public void InitUnity(String string) {
        LogManager.e(TAG, "InitUnity: " + string);
        UnityPlayer.UnitySendMessage("LogicManager", "ConfirmIndex", flag);
    }

    private String startWith;
    private String zhName;
    private String enName;

    private MediaPlayer mp;

    //unity调用android方法，播放序列帧
    public void StartAnim(int id) {
        id = id - 1;
        LogManager.e(TAG, "StartAnim: " + id);
        switch (id) {
            case 0:
                startWith = "0_";
                zhName = "亲悦 浴室家具";
                enName = "FAMILY CARE BATHROOM FURNITURE";
                break;
            case 1:
                startWith = "1_";
                zhName = "星朗 一体超感座便器";
                enName = "INNATE Intelligent Toilet";
                break;
            case 2:
                startWith = "2_";
                zhName = getString(R.string.zh_biangai);
                enName = getString(R.string.en_biangai);
                break;
            case 3:
                startWith = "3_";
                zhName = "悦明 镜柜";
                enName = "Grooming Mirrored Cabinet";
                break;
            case 4:
                startWith = "7_";
                zhName = "维亚 时尚台盆";
                enName = "VEIL Vessels";
                break;
            case 5:
                startWith = "5_";
                zhName = "圣苏西 3/4.5L五级旋风360连体座便器";
                enName = "SAN SOUCI 3/4.5L Rimless One-piece Toilet";
                break;
            case 6:
                startWith = "6_";
                zhName = "艺廷 臻彩幻色 龙头系列";
                enName = getString(R.string.longtou);
                break;
            case 7:
                startWith = "4_";
                zhName = "多功能一体台盆";
                enName = "WATERFOIL Tri Lavatory";
                break;
        }

        FilenameFilter filter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(startWith);
            }
        };

        rl_play.setBackgroundColor(0xD9000000);
        tv_zh.setText(zhName);
        tv_en.setText(enName);

        File mFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/resource/mp3/");
        if (mFile.exists()) {
            File[] files = mFile.listFiles(filter);

            if (files == null) {
                SharePreUtil.getInstance(this).saveConfig(Config.RESOURCE_STATUS, false);
                Toast.makeText(this, "资源包损坏，请重新进入APP下载", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            try {
                mp = new MediaPlayer();
                mp.setDataSource(files[0].getAbsolutePath());
                mp.prepare();

                // 音乐播放完毕的事件处理
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {
                        LogManager.e(TAG, "MediaPlayer onCompletion");
                        mp.release();
                    }
                });
                // 播放音乐时发生错误的事件处理
                mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        LogManager.e(TAG, "MediaPlayer onError");
                        // 释放资源
                        mp.release();
                        return false;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            SharePreUtil.getInstance(this).saveConfig(Config.RESOURCE_STATUS, false);
            Toast.makeText(this, "资源包损坏，请重新进入APP下载", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        File iFile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/resource/image/");
        if (iFile.exists()) {
            File[] files = iFile.listFiles(filter);

            if (files == null) {
                SharePreUtil.getInstance(this).saveConfig(Config.RESOURCE_STATUS, false);
                Toast.makeText(this, "资源包损坏，请重新进入APP下载", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            String[] paths = new String[files.length];
            LogManager.e(TAG, "length: " + files.length);
            for (int i = 0; i < files.length; i++) {
                paths[i] = files[i].getAbsolutePath();
                //                Log.e(TAG, "path: " + paths[i]);
            }

            play_view.setDataSource(paths, mp.getDuration());
            play_view.start();

            mp.start();

            play_view.setOnStopListener(new OnStopListener() {
                @Override
                public void onStop() {
                    LogManager.e(TAG, "PicturePlayerView onStop");
                    play_view.release();
                }
            });
            play_view.setOnErrorListener(new OnErrorListener() {
                @Override
                public void onError(String msg) {
                    LogManager.e(TAG, "PicturePlayerView onError");
                    play_view.release();
                }
            });

            playPop.showAtLocation(mUnityPlayer, Gravity.NO_GRAVITY, 0, 0);
        } else {
            SharePreUtil.getInstance(this).saveConfig(Config.RESOURCE_STATUS, false);
            Toast.makeText(this, "资源包损坏，请重新进入APP下载", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_play_cancel) {
            LogManager.e(TAG, "iv_play_cancel");
            play_view.release();
            mp.release();
            playPop.dismiss();
            UnityPlayer.UnitySendMessage("LogicManager", "CloseARButton", "");
        }
    }

    @OnClick(R2.id.iv_top_back)
    public void back() {
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // To support deep linking, we need to make sure that the client can get access to
        // the last sent intent. The clients access this through a JNI api that allows them
        // to get the intent set on launch. To update that after launch we have to manually
        // replace the intent with the one caught here.
        setIntent(intent);
    }

    // Quit Unity
    @Override
    protected void onDestroy() {
        if (play_view != null) {
            play_view.release();
        }
        if (mp != null) {
            mp.release();
        }
        mUnityPlayer.quit();
        super.onDestroy();
    }

    // Pause Unity
    @Override
    protected void onPause() {
        super.onPause();
        mUnityPlayer.pause();

        try {
            if (play_view.isPlaying()) {
                play_view.pause();
            }
            if (mp != null && mp.isPlaying()) {
                mp.pause();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    // Resume Unity
    @Override
    protected void onResume() {
        super.onResume();
        mUnityPlayer.resume();

        try {
            if (play_view.isPaused()) {
                play_view.resume();
            }
            if (mp != null && !mp.isPlaying()) {
                mp.start();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mUnityPlayer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUnityPlayer.stop();
    }

    // Low Memory Unity
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mUnityPlayer.lowMemory();
    }

    // Trim Memory Unity
    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == TRIM_MEMORY_RUNNING_CRITICAL) {
            mUnityPlayer.lowMemory();
        }
    }

    // This ensures the layout will be correct.
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mUnityPlayer.configurationChanged(newConfig);
    }

    // Notify Unity of the focus change.
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mUnityPlayer.windowFocusChanged(hasFocus);
    }

    // For some reason the multiple keyevent type is not supported by the ndk.
    // Force event injection by overriding dispatchKeyEvent().
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }

    // Pass any events not handled by (unfocused) views straight to UnityPlayer
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

    /*API12*/
    public boolean onGenericMotionEvent(MotionEvent event) {
        return mUnityPlayer.injectEvent(event);
    }

}
