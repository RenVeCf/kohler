package com.mengyang.kohler.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;

/**
 * Created by MengYang on 2018/5/26.
 */
public class AzureBotListChildViewHolder extends BaseViewHolder {
    private Context mContext;
    private View view;
    private TextView childLeftText;

    public AzureBotListChildViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final DataBean dataBean, final int pos) {

        childLeftText = (TextView) view.findViewById(R.id.tv_azure_bot_child);
        childLeftText.setText(dataBean.getChildLeftTxt());

    }
}
