package com.mengyang.kohler.home.fragment;

import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.allyes.analytics.AIOAnalytics;
import com.mengyang.kohler.BaseFragment;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.DisplayUtils;
import com.mengyang.kohler.home.activity.KbisActivity;
import com.mengyang.kohler.home.view.photoview.OnPhotoTapListener;
import com.mengyang.kohler.home.view.photoview.PhotoView;
import com.mengyang.kohler.home.view.photoview.PhotoViewAttacher;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 2018科勒上海厨卫展——导览图
 */

public class KbisGuideMapFragment extends BaseFragment implements OnPhotoTapListener {
    @BindView(R.id.photo_view)
    PhotoView mPhotoView;
    @BindView(R.id.photo_view_shadow)
    PhotoView mPhotoViewShadow;
    @BindView(R.id.photo_view1)
    PhotoView mPhotoView1;
    @BindView(R.id.photo_view2)
    PhotoView mPhotoView2;
    @BindView(R.id.photo_view3)
    PhotoView mPhotoView3;
    @BindView(R.id.photo_view4)
    PhotoView mPhotoView4;
    @BindView(R.id.photo_view5)
    PhotoView mPhotoView5;

    @BindView(R.id.scrollView)
    HorizontalScrollView mScrollView;

    private ImageView mIvTopTitle;
    private TextView mTvNum1;
    private TextView mTvNum2;
    private TextView mTvNum3;

    private PhotoViewAttacher mAttacher;

    List<String> mYList = new ArrayList<>();
    List<String> mXList = new ArrayList<>();
    private PhotoViewAttacher mAttacher1;
    private PhotoViewAttacher mAttacher2;
    private PhotoViewAttacher mAttacher3;
    private PhotoViewAttacher mAttacher4;
    private PhotoViewAttacher mAttacher5;
    private PopupWindow mPopupWindow;
    private PhotoViewAttacher mAttacher0;
    private LinearLayout mLlGuidemapPop;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_kbis_guide_map;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initValues() {
        AIOAnalytics.onEvent("trade_show_guide");
        MobclickAgent.onEvent(getActivity(), "trade_show_guide");
        mAttacher = new PhotoViewAttacher(mPhotoView);
        //        mAttacher0 = new PhotoViewAttacher(mPhotoViewShadow);
        mAttacher1 = new PhotoViewAttacher(mPhotoView1);
        mAttacher2 = new PhotoViewAttacher(mPhotoView2);
        mAttacher3 = new PhotoViewAttacher(mPhotoView3);
        mAttacher4 = new PhotoViewAttacher(mPhotoView4);
        mAttacher5 = new PhotoViewAttacher(mPhotoView5);

        mAttacher.setOnPhotoTapListener(this);
        //        mAttacher0.setOnPhotoTapListener(this);
        mAttacher1.setOnPhotoTapListener(this);
        mAttacher2.setOnPhotoTapListener(this);
        mAttacher3.setOnPhotoTapListener(this);
        mAttacher4.setOnPhotoTapListener(this);
        mAttacher5.setOnPhotoTapListener(this);

        //        mPhotoView.setZoomable(false);
        //        mAttacher.setZoomable(false);
        //        mAttacher0.setZoomable(false);
        mAttacher1.setZoomable(false);
        mAttacher2.setZoomable(false);
        mAttacher3.setZoomable(false);
        mAttacher4.setZoomable(false);
        mAttacher5.setZoomable(false);

        mPopupWindow = new PopupWindow(KbisGuideMapFragment.this.getActivity());
        mPopupWindow.setWidth(DisplayUtils.getScreenWidth(getActivity()) / 2 + DisplayUtils.getScreenWidth(getActivity()) / 10 * 2);
        mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = LayoutInflater.from(KbisGuideMapFragment.this.getActivity()).inflate(R.layout.item_pop, null);
        mPopupWindow.setContentView(view);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);
        mPopupWindow.setBackgroundDrawable(getResources().getDrawable(R.mipmap.guide));
        mLlGuidemapPop = (LinearLayout) view.findViewById(R.id.ll_guidemap_pop);

        mLlGuidemapPop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mAttacher.setMove(true);
            }
        });

        mIvTopTitle = (ImageView) view.findViewById(R.id.iv_top_title);
        mTvNum1 = (TextView) view.findViewById(R.id.tv_num1);
        mTvNum2 = (TextView) view.findViewById(R.id.tv_num2);
        mTvNum3 = (TextView) view.findViewById(R.id.tv_num3);

        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            }
        });

        mScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPopupWindow.isShowing()) {
                    return true;
                }
                return false;
            }
        });
        moveScrolleiew();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            moveScrolleiew();
        }
    }

    private void moveScrolleiew() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (mScrollView != null) {
                    SystemClock.sleep(300);
                    mScrollView.smoothScrollTo(430, 0);
                }
            }
        }.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        AIOAnalytics.onPageBegin("trade_show_guide");
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
        AIOAnalytics.onPageEnd("trade_show_guide");
    }

    @Override
    public void onPhotoTap(ImageView view, float x, float y) {
        if (mPopupWindow != null) {
            if (!mPopupWindow.isOutsideTouchable() && mPopupWindow.isShowing()) {
                return;
            }
        }

        mPhotoViewShadow.setVisibility(View.GONE);
        mYList.add(y + "");
        mXList.add(x + "");

        String yyy = mYList.toString();
        String xxx = mXList.toString();

        float scale = mPhotoView.getScale();

        if ((y >= 0.01 && y <= 0.50) && (x >= 0.20 && x <= 0.72)) {
            setAllGone(5);

        } else if ((y >= 0.27 && y <= 0.82) && (x >= 0.04 && x <= 0.61)) {
            setAllGone(4);

        } else if ((y >= 0.30 && y <= 0.51) && (x >= 0.83 && x <= 0.99)) {
            setAllGone(1);

        } else if ((y >= 0.49 && y <= 0.64) && (x >= 0.74 && x <= 0.90)) {
            setAllGone(2);

        } else if ((y >= 0.64 && y <= 0.88) && (x >= 0.66 && x <= 0.88)) {
            setAllGone(3);
        } else {
            mPhotoView1.setVisibility(View.GONE);
            mPhotoView2.setVisibility(View.GONE);
            mPhotoView3.setVisibility(View.GONE);
            mPhotoView4.setVisibility(View.GONE);
            mPhotoView5.setVisibility(View.GONE);

            mPhotoViewShadow.setVisibility(View.VISIBLE);
        }
    }

    private void setAllGone(int order) {
        mPhotoView1.setVisibility(View.GONE);
        mPhotoView2.setVisibility(View.GONE);
        mPhotoView3.setVisibility(View.GONE);
        mPhotoView4.setVisibility(View.GONE);
        mPhotoView5.setVisibility(View.GONE);

        switch (order) {
            case 1:
                mPhotoView1.setVisibility(View.VISIBLE);
                break;
            case 2:
                mPhotoView2.setVisibility(View.VISIBLE);
                break;
            case 3:
                mPhotoView3.setVisibility(View.VISIBLE);
                break;
            case 4:
                mPhotoView4.setVisibility(View.VISIBLE);
                break;
            case 5:
                mPhotoView5.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

        switch (order) {
            case 1:
                mIvTopTitle.setImageResource(R.mipmap.iv_kbis_white_01);
                mTvNum1.setText("享");
                mTvNum2.setText("Joy of Water");
                mTvNum3.setText("听水滴坠落的旋律，让淋浴成为充满乐趣的舞蹈。\n" +
                        "Enjoy a refreshing shower while listening to the rhythm of the falling water droplets. ");
                break;
            case 2:
                mIvTopTitle.setImageResource(R.mipmap.iv_kbis_white_02);
                mTvNum1.setText("智");
                mTvNum2.setText("Smart Home");
                mTvNum3.setText("云技术、大数据、物联网、人工智能……科勒将这些复杂的尖端科技运用在浴室和卫生间，为您提供舒适贴心的智能生活体验。\n" +
                        "Cloud technology, big data, Internet of Things, artificial intelligence — Kohler applies these complex, state-of-the-art technologies in kitchens and bathrooms to bring you a comfortable, convenient and elegant living experience.");
                break;
            case 3:
                mIvTopTitle.setImageResource(R.mipmap.iv_kbis_white_03);
                mTvNum1.setText("净");
                mTvNum2.setText("Hygiene & Clean");
                mTvNum3.setText("多重革新技术，极致清洁体验，卫浴空间的每一件“贴身”用品，都应该洁净入微。\n" +
                        "Multiple technological innovations enable Kohler to leap forward once more in hygiene and purification.");
                break;
            case 4:
                mIvTopTitle.setImageResource(R.mipmap.iv_kbis_white_04);
                mTvNum1.setText("爱");
                mTvNum2.setText("Family Care");
                mTvNum3.setText("用环保材质和体贴细节，科勒创造一个真正属于全家人的浴室，安全、易用、健康，处处给孩子与老人贴身宠爱，让浴室充满温馨的天伦之情。\n" +
                        "Every family member — men, women, children and the elderly — receive all-around care from Kohler, where your safety, health and convenience have all been carefully considered in detail.");
                break;
            case 5:
                mIvTopTitle.setImageResource(R.mipmap.iv_kbis_white_05);
                mTvNum1.setText("美");
                mTvNum2.setText("Bold Luxury");
                mTvNum3.setText("科勒145周年，从未停步的创新之旅。我们不断在艺术的世界中寻找灵感，坚持将敢创设计与卓越技术融入每一件产品。百年传承，为美执着。\n" +
                        "Great design starts from unique inspirations, rich experiences and the never-ending pursuit of artistic perfection. Kohler is serious about every curve and angle, creating amazing artistic creations with a variety of styles.");
                break;
            default:
                break;
        }

        mPopupWindow.showAtLocation(mScrollView, Gravity.CENTER, 10, 10);
        mAttacher.setMove(false);
        //        mAttacher0.setMove(false);
        mAttacher1.setMove(false);
        mAttacher2.setMove(false);
        mAttacher3.setMove(false);
        mAttacher4.setMove(false);

    }

    public void clearData(View view) {
        mYList.clear();
        mXList.clear();

        mPhotoView1.setVisibility(View.GONE);
        mPhotoView2.setVisibility(View.GONE);
        mPhotoView3.setVisibility(View.GONE);
        mPhotoView4.setVisibility(View.GONE);
        mPhotoView5.setVisibility(View.GONE);
    }
}
