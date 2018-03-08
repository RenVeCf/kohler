package com.kohler.arscan;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kohler.arscan.constant.Config;
import com.kohler.arscan.util.LogManager;
import com.kohler.arscan.util.SPUtil;
import com.kohler.arscan.util.SharePreUtil;
import com.xiuyukeji.pictureplayerview.PicturePlayerView;
import com.xiuyukeji.pictureplayerview.interfaces.OnErrorListener;
import com.xiuyukeji.pictureplayerview.interfaces.OnStopListener;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends AppCompatActivity {

    private final static String TAG = PlayActivity.class.getSimpleName();

    @BindView(R2.id.rl_play)
    RelativeLayout rl_play;
    @BindView(R2.id.play_view)
    PicturePlayerView play_view;

    @BindView(R2.id.tv_zh)
    TextView tv_zh;
    @BindView(R2.id.tv_en)
    TextView tv_en;

    @BindView(R2.id.tv_more)
    TextView tv_more;

    private String startWith;
    private String zhName;
    private String enName;
    private int bg;

    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initView() {
        rl_play.setBackgroundResource(bg);

        tv_zh.setText(zhName);
        tv_en.setText(enName);
    }

    private void initData() {
        int flag = getIntent().getIntExtra("flag", 0);

        switch (flag) {
            case 0:
                bg = R.drawable.a_search;
                startWith = "0_";
                zhName = "亲悦 浴室家具";
                enName = "FAMILY CARE BATHROOM FURNITURE";
                break;
            case 1:
                bg = R.drawable.b_search;
                startWith = "1_";
                zhName = "星朗 一体超感座便器";
                enName = "INNATE Intelligent Toilet";
                break;
            case 2:
                bg = R.drawable.c_search;
                startWith = "2_";
                zhName = getString(R.string.zh_biangai);
                enName = getString(R.string.en_biangai);
                break;
            case 3:
                bg = R.drawable.d_search;
                startWith = "3_";
                zhName = "悦明 镜柜";
                enName = "Grooming Mirrored Cabinet";
                break;
            case 4:
                bg = R.drawable.e_search;
                startWith = "7_";
                zhName = "维亚 时尚台盆";
                enName = "VEIL Vessels";
                break;
            case 5:
                bg = R.drawable.f_search;
                startWith = "5_";
                zhName = "圣苏西 3/4.5L五级旋风360连体座便器";
                enName = "SAN SOUCI 3/4.5L Rimless One-piece Toilet";
                break;
            case 6:
                bg = R.drawable.g_search;
                startWith = "6_";
                zhName = "艺廷 臻彩幻色 龙头系列";
                enName = getString(R.string.longtou);
                break;
            case 7:
                bg = R.drawable.h_search;
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

        File mFile = new File(Environment.getExternalStorageDirectory() + "/resource/mp3/");
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

                        tv_more.setVisibility(View.VISIBLE);
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

        File iFile = new File(Environment.getExternalStorageDirectory() + "/resource/image/");
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

                    tv_more.setVisibility(View.VISIBLE);
                }
            });
            play_view.setOnErrorListener(new OnErrorListener() {
                @Override
                public void onError(String msg) {
                    LogManager.e(TAG, "PicturePlayerView onError");
                    play_view.release();
                }
            });
        } else {
            SharePreUtil.getInstance(this).saveConfig(Config.RESOURCE_STATUS, false);
            Toast.makeText(this, "资源包损坏，请重新进入APP下载", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick(R2.id.iv_top_back)
    public void back() {
        finish();
    }

    @OnClick(R2.id.iv_play_cancel)
    public void cancel() {
        finish();
    }

    @OnClick(R2.id.tv_more)
    public void notebook() {
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

    @Override
    protected void onResume() {
        super.onResume();

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
    protected void onPause() {
        super.onPause();

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //释放资源
        if (play_view != null) {
            play_view.release();
        }
        if (mp != null) {
            mp.release();
        }
    }

}
