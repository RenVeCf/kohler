package com.mengyang.kohler.common.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.net.IConstants;
import com.mengyang.kohler.common.utils.SPUtil;
import com.mengyang.kohler.module.bean.QuestionSearchBean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by liusong on 2018/2/9.
 */

public class UserServiceAdapter extends BaseMultiItemQuickAdapter<QuestionSearchBean, BaseViewHolder> {

    private SimpleDateFormat mDateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");;
    private int mHour;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public UserServiceAdapter(List<QuestionSearchBean> data) {
        super(data);
        addItemType(0, R.layout.item_service_company);  //必须设置Item类型,否则空职指针异常
        addItemType(1,R.layout.item_service_user);
        addItemType(3,R.layout.item_service_company_head);
    }

    @Override
    protected void convert(BaseViewHolder helper, QuestionSearchBean item) {
        switch (item.getItemType()) {
            case 0: //标题:
                helper.setText(R.id.tv_serviec_user, "科勒机器人")
                .setText(R.id.tv_service_time, parseTiem())
                .setText(R.id.tv_service_message, item.getDescription());
                helper.setText(R.id.tv_service_list, item.getH5Url());
                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }

                if (!TextUtils.isEmpty(item.getH5Url())) {
                    helper.setText(R.id.tv_service_list, item.getH5Url());
                    helper.setVisible(R.id.tv_service_list, true);
                } else {
                    helper.setGone(R.id.tv_service_list, true);
                }


                break;
            case 1: //内容
                helper.setText(R.id.tv_serviec_user_name, (String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))
                .setText(R.id.tv_service_time, parseTiem())
                .setText(R.id.tv_service_message, item.getDescription());
            //设置头像
//                Glide.with(App.getContext()).load(SPUtil.get(App.getContext(), IConstants.USER_HEAD_PORTRAIT, ""))
//                        .apply(new RequestOptions().placeholder(R.mipmap.oval)).into((ImageView) helper.getView(R.id.iv_service_photo));

                if (mHour >= 0 && mHour < 12) {
                    helper.setText(R.id.tv_flag, "AM");
                } else {
                    helper.setText(R.id.tv_flag, "PM");
                }
                break;
            case 3:
                helper.setText(R.id.tv_serviec_user, "科勒机器人")
                        .setText(R.id.tv_service_time, parseTiem())
                        .setText(R.id.tv_service_message, item.getDescription());
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
        long time=System.currentTimeMillis();
        final Calendar mCalendar=Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        //		 取得小时：
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        //		 取得分钟：
        int mMinuts=mCalendar.get(Calendar.MINUTE);
        String mMinString = null;
        String mHourString = null;
        if (mMinuts<10){
            mMinString = "0"+mMinuts;
        }else{
            mMinString = ""+mMinuts;
        }

        if (mHour == 0) {
            mHourString = "0" + mHour;
        } else {
            mHourString = "" + mHour;
        }
        return mHourString +":"+mMinString;
    }
}
