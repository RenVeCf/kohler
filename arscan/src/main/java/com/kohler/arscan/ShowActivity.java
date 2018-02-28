package com.kohler.arscan;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kohler.arscan.util.SPUtil;
import com.kohler.arscan.view.ColourImageBaseLayerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = ShowActivity.class.getSimpleName();

    @BindView(R2.id.iv_top_scan)
    ImageView iv_top_scan;
    @BindView(R2.id.map_view)
    ColourImageBaseLayerView map_view;
    @BindView(R2.id.rl_icon)
    RelativeLayout rl_icon;

    private PopupWindow armodePop;
    private PopupWindow alertPop;
    private PopupWindow notebookPop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        initView();
        initPop();
    }

    private ImageView iv_alert_icon;
    private TextView tv_zh;
    private TextView tv_en;
    private TextView tv_alert_content;
    private TextView tv_go_scan;

    private void initPop() {
        View view = LayoutInflater.from(this).inflate(R.layout.armode_pop, null);
        view.findViewById(R.id.tv_know).setOnClickListener(this);

        armodePop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                armodePop.showAtLocation(iv_top_scan, Gravity.NO_GRAVITY, 0, 0);
            }
        }, 100);


        view = LayoutInflater.from(this).inflate(R.layout.map_alert_pop, null);

        view.findViewById(R.id.iv_alert_cancel).setOnClickListener(this);
        tv_go_scan = view.findViewById(R.id.tv_go_scan);
        tv_go_scan.setOnClickListener(this);

        iv_alert_icon = view.findViewById(R.id.iv_alert_icon);
        tv_zh = view.findViewById(R.id.tv_zh);
        tv_en = view.findViewById(R.id.tv_en);
        tv_alert_content = view.findViewById(R.id.tv_alert_content);

        alertPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        view = LayoutInflater.from(this).inflate(R.layout.notebook_pop, null);

        view.findViewById(R.id.tv_notebook_download).setOnClickListener(this);
        view.findViewById(R.id.iv_notebook_cancel).setOnClickListener(this);

        notebookPop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void initView() {
        iv_top_scan.setVisibility(View.VISIBLE);

        map_view.initPop(handler);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    rl_icon.setVisibility(View.GONE);
                    break;
                case 1:
                    setAlert(msg.arg1);

                    alertPop.showAtLocation(map_view, Gravity.NO_GRAVITY, 0, 0);
                    break;
            }
        }
    };

    private void setAlert(int selected) {
        int icon = 0;
        String zh = null;
        String en = null;
        String content = null;
        String go = null;
        switch (selected) {
            case 0:
                icon = R.drawable.a_alert;
                zh = "145周年 美式套间";
                en = "145 ANNIVERSARY SUITE";
                content = "沿袭美式经典风格，融入现代优雅生活。致敬科勒145\n年历史传承，145周年套件以优雅大气的设计语言，集\n中展现了科勒“代表每个时代的最高水准”的追求与承\n诺";
                go = "前往145周年套间，体验科勒美式优雅生活";
                break;
            case 1:
                icon = R.drawable.b_alert;
                zh = "智能生活";
                en = "SMART HOME SUITE";
                content = "云技术、大数据、物联网、人工智能......科勒将这些复\n杂的尖端科技运用在浴室和卫生间，为您提供舒适贴心\n的生活体验。";
                go = "前往智能生活套间，体验科勒现代科技生活";
                break;
            case 2:
                icon = R.drawable.c_alert;
                zh = "维亚套间";
                en = "VEIL SUITE";
                content = "感应灯光与科勒云境系统创造性地融合，灯随人动，光\n随时变，打造远离尘嚣、静谧疗愈的光感体验。";
                go = "前往维亚套间，体验科勒光影魅力";
                break;
            case 3:
                icon = R.drawable.d_alert;
                zh = "科勒精选 为美执着";
                en = "KOHLER EXQUISITE SUITE";
                content = "凝聚145年品牌精粹，科勒精选代表着科勒顶级品质，\n每一件都是百年匠心与艺术美学的杰作。";
                go = "前往科勒精选间，体验科勒匠心精粹";
                break;
            case 4:
                icon = R.drawable.e_alert;
                zh = "极净空间";
                en = "HYGIENE AND CLEAN SUITE";
                content = "多重革新技术，极致清洁体验，卫浴空间的每一件“贴\n身”用品，都应该洁净入微。";
                go = "前往极净空间，感受科勒洁净科技";
                break;
            case 5:
                icon = R.drawable.f_alert;
                zh = "敢创设计";
                en = "SELF EXPRESSION SUITE";
                content = "伟大的设计始于独特的创造性、丰富的经验和对艺术的\n执着和创新的坚持。我们不断在艺术的世界中寻找灵感\n，以简约流畅的线条，多样化的风格，打造一件件令人\n惊艳的卫浴产品。";
                go = "前往敢闯设计套间，感受科勒独特创意";
                break;
            case 6:
                icon = R.drawable.g_alert;
                zh = "关爱家";
                en = "FAMILY CARE SUITE";
                content = "用环保材质和体贴细节，科勒创造一个真正属于全家人\n的浴室，安全、易用、健康，处处给孩子与老人贴身宠\n爱，让浴室充满温馨的天伦之情。 ";
                go = "前往关爱家套间，发现科勒细致之美";
                break;
            case 7:
                icon = R.drawable.h_alert;
                zh = "爱厨房";
                en = "KITCHEN";
                content = "优雅的造型、人性化的设计、健康安全的材质、坚固耐\n用的质量，科勒助您享受料理乐趣，在厨房大显身手。";
                go = "前往爱厨房套间，体验有爱科勒";
                break;
        }
        iv_alert_icon.setImageResource(icon);
        tv_zh.setText(zh);
        tv_en.setText(en);
        tv_alert_content.setText(content);
        tv_go_scan.setText(go);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_know) {
            armodePop.dismiss();
        } else if (id == R.id.iv_alert_cancel) {
            alertPop.dismiss();
        } else if (id == R.id.tv_go_scan) {
            alertPop.dismiss();

            Intent intent = new Intent(this, UnityPlayerActivity.class);
            intent.putExtra("flag", "9");
            startActivity(intent);
        } else if (id == R.id.iv_notebook_cancel) {
            notebookPop.dismiss();
        } else if (id == R.id.tv_notebook_download) {
            notebookPop.dismiss();

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

    @OnClick(R2.id.iv_top_back)
    public void back() {
        finish();
    }

    @OnClick(R2.id.iv_top_scan)
    public void scan() {
        Intent intent = new Intent(this, UnityPlayerActivity.class);
        intent.putExtra("flag", "9");
        startActivity(intent);
    }

    @OnClick(R2.id.ll_main)
    public void main() {
        Intent intent = new Intent();
        if (((boolean) SPUtil.get(this, "isLogin", false))) {
            if (SPUtil.get(this, "no_type", "").equals("dealer")) {
                intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.home.activity.MeetingActivity");
                startActivity(intent);
            }
        } else {
            intent.setClassName("com.mengyang.kohler", "com.mengyang.kohler.account.activity.LoginActivity");
            startActivity(intent);
        }
    }

    @OnClick(R2.id.ll_search)
    public void search() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @OnClick(R2.id.ll_notebook)
    public void notebook() {
        notebookPop.showAtLocation(map_view, Gravity.NO_GRAVITY, 0, 0);
    }

}