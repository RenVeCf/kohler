package com.mengyang.kohler.home.adapter;

import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
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

public class BarrageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public BarrageAdapter(List<String> data) {
        super(R.layout.item_barrage_user, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        helper.setText(R.id.tv_barrage_user_name, (String) SPUtil.get(App.getContext(), IConstants.USER_NIKE_NAME, ""))
                .setText(R.id.tv_barrage_message, item);

        //设置头像
        Glide.with(App.getContext()).load(SPUtil.get(App.getContext(), IConstants.USER_HEAD_PORTRAIT, ""))
                .apply(new RequestOptions().placeholder(R.mipmap.oval)).into((ImageView) helper.getView(R.id.iv_barrage_photo));
    }

}
