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
import android.widget.LinearLayout;
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
import com.mengyang.kohler.common.entity.TextBean;
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

public class AzureCustomerServiceAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_TEXT = 2;
    public static final int TYPE_PRODUCT = 3;

    private SimpleDateFormat mDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mHour;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AzureCustomerServiceAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.item_service_modify);
        addItemType(TYPE_LEVEL_1, R.layout.item_service_level1);
        addItemType(TYPE_TEXT, R.layout.item_azure_service_company_head); //必须设置Item类型,否则空职指针异常
        addItemType(TYPE_PRODUCT, R.layout.item_service_product);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (item.getItemType()) {
            case TYPE_LEVEL_0://客服
                final Level0Item level0Item = (Level0Item) item;
                helper.setText(R.id.tv_service_list_top_01, level0Item.getParrentLeft());
                    helper.setText(R.id.tv_service_list_top_02, level0Item.getParrentRight());
                    helper.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int position = helper.getAdapterPosition();
                            if (level0Item.isExpanded()) {
                                collapse(position);
                            } else {
                                expand(position);
                            }
                        }
                    });
                break;
            case TYPE_LEVEL_1: //用户
                final Level1Item level0Item1 = (Level1Item) item;
                helper.setText(R.id.tv_content, level0Item1.getContent());
                break;
            case TYPE_TEXT://标题:
                final TextBean textBean = (TextBean) item;
                helper.setText(R.id.tv_serviec_user, "客服小科")
                        .setText(R.id.tv_service_time, parseTiem());

                if (textBean.getDescription() != null) {
                    helper.setText(R.id.tv_service_message, textBean.getDescription());
                }

                //                helper.setText(R.id.tv_service_list, item.getH5Url());
                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                break;
            case TYPE_PRODUCT:

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
