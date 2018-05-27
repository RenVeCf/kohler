package com.mengyang.kohler.common.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.ReservationExperienceActivity;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.main.activity.MainActivity;
import com.mengyang.kohler.module.bean.QuestionSearchBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by liusong on 2018/2/9.
 */

public class AzureCustomerServiceAdapter extends BaseMultiItemQuickAdapter<QuestionSearchBean, BaseViewHolder> {

    private SimpleDateFormat mDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mHour;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AzureCustomerServiceAdapter(List<QuestionSearchBean> data) {
        super(data);
        addItemType(0, R.layout.item_azure_service_company); //必须设置Item类型,否则空职指针异常
        addItemType(1, R.layout.item_azure_service_user);
        addItemType(3, R.layout.item_azure_service_company_head);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(final BaseViewHolder helper, QuestionSearchBean item) {
        switch (item.getItemType()) {
            case 0://客服
                helper.setText(R.id.tv_serviec_user, "客服小科")
                        .setText(R.id.tv_service_time, parseTiem())
                        .setText(R.id.tv_service_message, item.getDescription());
                //                helper.setText(R.id.tv_service_list, item.getH5Url());
                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                break;
            case 1: //用户
                helper.setText(R.id.tv_serviec_user_name, (String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))
                        .setText(R.id.tv_service_time, parseTiem())
                        .setText(R.id.tv_service_message, item.getDescription());
                //设置头像
                Glide.with(App.getContext()).load(SPUtil.get(App.getContext(), IConstants.USER_HEAD_PORTRAIT, ""))
                        .apply(new RequestOptions().placeholder(R.mipmap.azure_service_photo_w)).into((ImageView) helper.getView(R.id.iv_service_photo));

                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                break;
            case 3://标题:
                LogUtils.i("rmy", "item.getDescription() = " + item.getDescription());
                if (item.getDescription().equals("还可以进入科勒预约系统进行门店查询和预约点击进入 或 返回")) {
                    helper.setText(R.id.tv_serviec_user, "客服小科")
                            .setText(R.id.tv_service_time, parseTiem());
                    TextView textView = helper.getView(R.id.tv_service_message);
                    textView.setText(getClickableSpan());
                    textView.setMovementMethod(LinkMovementMethod.getInstance());
                    if (mHour >= 0 && mHour < 12) {
                        helper.setText(R.id.tv_flag, "AM");
                    } else {
                        helper.setText(R.id.tv_flag, "PM");
                    }
                } else {
                    helper.setText(R.id.tv_serviec_user, "客服小科")
                            .setText(R.id.tv_service_time, parseTiem())
                            .setText(R.id.tv_service_message, item.getDescription());
                    if (mHour >= 0 && mHour < 12) {
                        helper.setText(R.id.tv_flag, "AM");
                    } else {
                        helper.setText(R.id.tv_flag, "PM");
                    }
                }
                break;
            default:
                break;
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
        SpannableString spannableString = new SpannableString("还可以进入科勒预约系统进行门店查询和预约点击进入 或 返回");
        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 20, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                mContext.startActivity(new Intent(mContext, ReservationExperienceActivity.class));
            }
        }, 20, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(App.getContext().getResources().getColor(R.color.colorBluePrimary)), 20, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置下划线文字
        spannableString.setSpan(new UnderlineSpan(), 27, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的单击事件
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(App.getContext(), "返回", Toast.LENGTH_SHORT).show();
            }
        }, 27, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置文字的前景色
        spannableString.setSpan(new ForegroundColorSpan(App.getContext().getResources().getColor(R.color.colorBluePrimary)), 27, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }
}
