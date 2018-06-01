package com.mengyang.kohler.common.adapter;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.ReservationExperienceActivity;
import com.mengyang.kohler.common.entity.Level0Item;
import com.mengyang.kohler.common.entity.Level1Item;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.module.bean.AzureServiceMultimediaBean;
import com.mengyang.kohler.module.bean.QuestionSearchBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by liusong on 2018/2/9.
 */

public class UserServiceAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_TEXT = 2;
    public static final int TYPE_PRODUCT = 3;
    public static final int TYPE_USER = 4;

    private SimpleDateFormat mDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mHour;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public UserServiceAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_home_service_list_parent);
        addItemType(TYPE_LEVEL_1, R.layout.item_home_service_list_child);
        addItemType(TYPE_TEXT, R.layout.item_home_service_company_head); //必须设置Item类型,否则空职指针异常
        addItemType(TYPE_PRODUCT, R.layout.item_home_service_product);
        addItemType(TYPE_USER, R.layout.item_home_service_user);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (item.getItemType()) {
            case TYPE_LEVEL_0://父级
                final Level0Item level0Item = (Level0Item) item;
                helper.setText(R.id.tv_service_list_top_01, level0Item.getParrentLeft());
                helper.setText(R.id.tv_service_list_top_02, level0Item.getParrentRight());
                helper.setBackgroundColor(R.id.rl_item_parent, Color.argb(255, 237, 237, 237));
                helper.addOnClickListener(R.id.tv_service_list_top_01);
                helper.addOnClickListener(R.id.tv_service_list_top_02);
                break;
            case TYPE_LEVEL_1: //子级
                final Level1Item level0Item1 = (Level1Item) item;
                helper.setText(R.id.tv_home_list_child_content, level0Item1.getContent());
                helper.setBackgroundColor(R.id.rl_item_child, Color.argb(77, 255, 255, 255));
                break;
            case TYPE_TEXT://客服文本
                int adapterPosition = helper.getAdapterPosition();
                if ((adapterPosition == 4)) {
                    RelativeLayout view = helper.getView(R.id.rl_head);
                    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                    layoutParams.setMargins(0, 78, 0, 0);
                    view.setLayoutParams(layoutParams);
                }

                final QuestionSearchBean textBean = (QuestionSearchBean) item;
                helper.setText(R.id.tv_serviec_user, "客服小科")
                        .setText(R.id.tv_service_time, parseTiem());

                if (textBean.getDescription() != null) {
                    TextView textview = helper.getView(R.id.tv_service_message);
                    if (textBean.getDescription().equals("还可以进入科勒预约系统进行门店查询和预约, 点击进入 或 返回")) {
                        textview.setText(getClickableSpan());
                        textview.setMovementMethod(LinkMovementMethod.getInstance());
                    } else
                        textview.setText(textBean.getDescription());
                }

                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                break;
            case TYPE_PRODUCT:
                RelativeLayout view = helper.getView(R.id.rl_home_service_img);
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
                layoutParams.setMargins(0, 78, 0, 0);
                view.setLayoutParams(layoutParams);
                final AzureServiceMultimediaBean azureServiceMultimediaBean = (AzureServiceMultimediaBean) item;
                helper.setBackgroundColor(R.id.ll_home_service_bg, Color.argb(255, 237, 237, 237));
                Glide.with(App.getContext()).load(azureServiceMultimediaBean.getImageUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into((ImageView) helper.getView(R.id.iv_service_product_icon));
                helper.setText(R.id.tv_service_time, parseTiem()).setText(R.id.tv_service_product_recommend, azureServiceMultimediaBean.getTitle())
                        .setText(R.id.tv_service_product_recommend_01, azureServiceMultimediaBean.getElementDesc());
                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                helper.addOnClickListener(R.id.ll_home_service_bg);
                break;
            case TYPE_USER: //用户
                final QuestionSearchBean questionSearchBean = (QuestionSearchBean) item;
                helper.setText(R.id.tv_serviec_user_name, (String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))
                        .setText(R.id.tv_service_time, parseTiem())
                        .setText(R.id.tv_service_message, questionSearchBean.getDescription());
                //设置头像
                Glide.with(App.getContext()).load(SPUtil.get(App.getContext(), IConstants.USER_HEAD_PORTRAIT, ""))
                        .apply(new RequestOptions().placeholder(R.mipmap.oval)).into((ImageView) helper.getView(R.id.iv_service_photo));

                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to, final View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    v.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }

    private String parseTiem() {
        long time = System.currentTimeMillis();
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        //		 取得小时：
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        //		 取得分钟：
        int mMinuts = mCalendar.get(Calendar.MINUTE);
        String mMinString = null;
        String mHourString = null;
        if (mMinuts < 10) {
            mMinString = "0" + mMinuts;
        } else {
            mMinString = "" + mMinuts;
        }

        if (mHour == 0) {
            mHourString = "0" + mHour;
        } else {
            mHourString = "" + mHour;
        }
        return mHourString + ":" + mMinString;
    }

    /**
     * 获取可点击的SpannableString
     *
     * @return
     */
    private SpannableString getClickableSpan() {
        SpannableString spannableString = new SpannableString("还可以进入科勒预约系统进行门店查询和预约, 点击进入 或 返回");
        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 22, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                mContext.startActivity(new Intent(mContext, ReservationExperienceActivity.class));
            }
        }, 22, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(App.getContext().getResources().getColor(R.color.colorBluePrimary)), 22, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 29, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                App.destoryActivity("AzureCustomerServiceActivity");
            }
        }, 29, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(App.getContext().getResources().getColor(R.color.colorBluePrimary)), 29, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
