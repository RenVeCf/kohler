package com.mengyang.kohler.common.adapter;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
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
import com.mengyang.kohler.common.utils.SPUtil;
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
    public static final int TYPE_USER = 4;

    private SimpleDateFormat mDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private int mHour;
    private int mDataSize;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public AzureCustomerServiceAdapter(List<MultiItemEntity> data) {
        super(data);
        this.mDataSize = data.size();
        addItemType(TYPE_LEVEL_0, R.layout.item_azure_service_list_parent);
        addItemType(TYPE_LEVEL_1, R.layout.item_azure_service_list_child);
        addItemType(TYPE_TEXT, R.layout.item_azure_service_company_head); //必须设置Item类型,否则空职指针异常
        addItemType(TYPE_PRODUCT, R.layout.item_service_product);
        addItemType(TYPE_USER, R.layout.item_azure_service_user);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(final BaseViewHolder helper, final MultiItemEntity item) {
        switch (item.getItemType()) {
            case TYPE_LEVEL_0://父级
                final Level0Item level0Item = (Level0Item) item;
                helper.setText(R.id.tv_service_list_top_01, level0Item.getParrentLeft());
                helper.setText(R.id.tv_service_list_top_02, level0Item.getParrentRight());
//                if (helper.getLayoutPosition() == 3) {
//                    View llAzureListParent = helper.getView(R.id.ll_azure_list_parent);
//                    LinearLayout.LayoutParams params_2 = (LinearLayout.LayoutParams) llAzureListParent.getLayoutParams();
//
//                    params_2.setMargins(0, 0, 0, 300);
//                    llAzureListParent.setLayoutParams(params_2);
//                }
                helper.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = helper.getLayoutPosition();
                        if (level0Item.isExpanded()) {
                            collapse(position);
                        } else {
                            expand(position);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1: //子级
                final Level1Item level0Item1 = (Level1Item) item;
                helper.setText(R.id.tv_azure_list_child_content, level0Item1.getContent());
                break;
            case TYPE_TEXT://客服文本
                final QuestionSearchBean textBean = (QuestionSearchBean) item;
                helper.setText(R.id.tv_serviec_user, "客服小科")
                        .setText(R.id.tv_service_time, parseTiem());

                if (textBean.getDescription() != null) {
                    helper.setText(R.id.tv_service_message, textBean.getDescription());
                }

                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                break;
            case TYPE_PRODUCT:

                break;
            case TYPE_USER: //用户
                final QuestionSearchBean questionSearchBean = (QuestionSearchBean) item;
                helper.setText(R.id.tv_serviec_user_name, (String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))
                        .setText(R.id.tv_service_time, parseTiem())
                        .setText(R.id.tv_service_message, questionSearchBean.getDescription());
                //设置头像
                Glide.with(App.getContext()).load(SPUtil.get(App.getContext(), IConstants.USER_HEAD_PORTRAIT, ""))
                        .apply(new RequestOptions().placeholder(R.mipmap.azure_service_photo_w)).into((ImageView) helper.getView(R.id.iv_service_photo));

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
