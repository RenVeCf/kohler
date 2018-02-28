package com.kohler.arscan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kohler.arscan.constant.Config;
import com.kohler.arscan.util.SPUtil;
import com.kohler.arscan.util.SharePreUtil;
import com.xiuyukeji.pictureplayerview.PicturePlayerView;
import com.xiuyukeji.pictureplayerview.interfaces.OnStopListener;

import java.io.File;
import java.io.FilenameFilter;

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
                zhName = "C3 -420 清舒宝智能便盖";
                enName = "C3-420 Smart Seat";
                break;
            case 3:
                bg = R.drawable.d_search;
                startWith = "3_";
                zhName = "悦明 镜柜";
                enName = "Grooming Mirrored Cabinet";
                break;
            case 4:
                bg = R.drawable.e_search;
                startWith = "4_";
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
                enName = "Component OMBRE Graphic Faucet";
                break;
            case 7:
                bg = R.drawable.h_search;
                startWith = "7_";
                zhName = "多功能一体台盆";
                enName = "WATERFOIL Tri Lavatory";
                break;
        }

        File file = new File(Environment.getExternalStorageDirectory() + "/resource/image/");

        if (file.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.startsWith(startWith);
                }
            };
            File[] files = file.listFiles(filter);
            String[] paths = new String[files.length];
            for (int i = 0; i < files.length; i++) {
                paths[i] = files[i].getAbsolutePath();
                Log.e(TAG, "path: " + paths[i]);
            }

            play_view.setDataSource(paths, files.length * 1000 / 16);
            play_view.start();
            play_view.setOnStopListener(new OnStopListener() {
                @Override
                public void onStop() {
                    tv_more.setVisibility(View.VISIBLE);
                }
            });
        } else {
            SharePreUtil.getInstance(this).saveConfig(Config.RESOURCE_STATUS, false);
            Toast.makeText(this, "资源包损坏，请重新进入APP下载", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick(R2.id.iv_play_cancel)
    public void cancel() {
        finish();
    }

    @OnClick(R2.id.tv_more)
    public void notebook() {
        finish();
        Intent intent = new Intent();
        if (((boolean) SPUtil.get(this, "isLogin", false))) {
            if (SPUtil.get(this, "no_type", "").equals("dealer")) {
                intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.home.activity.MineManualActivity");
                startActivity(intent);
            }
        } else {
            intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.account.activity.LoginActivity");
            startActivity(intent);
        }
    }
}
