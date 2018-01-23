package com.mengyang.kohler.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mengyang.kohler.R;

/**
 * Description : 标题栏
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/1
 */

public class TopView extends RelativeLayout implements View.OnClickListener {
    private ImageView ivTopSystemMsg;
    private TextView tvTopTitle;
    private ImageView ivTopCustomerService;
    private Boolean isSystemMsg;
    private Context mContext;

    public TopView(Context context) {
        super(context);
        initValues(context);
    }

    public TopView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initValues(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopView);
        tvTopTitle.setText(typedArray.getString(R.styleable.TopView_title));
        tvTopTitle.setTextColor(typedArray.getColor(R.styleable.TopView_title_color, 0x000000));
        isSystemMsg = typedArray.getBoolean(R.styleable.TopView_is_system_msg, false);
        ivTopSystemMsg.setVisibility(isSystemMsg ? View.GONE : View.VISIBLE);
        ivTopCustomerService.setVisibility(isSystemMsg ? View.GONE : View.VISIBLE);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValues(context);
    }

    private void initValues(Context context) {
        mContext = context;
        View.inflate(context, R.layout.top_view, this);
        tvTopTitle = this.findViewById(R.id.tv_top_title);
        ivTopSystemMsg = this.findViewById(R.id.iv_top_system_msg);
        ivTopSystemMsg.setOnClickListener(this);
        ivTopCustomerService = this.findViewById(R.id.iv_top_customer_service);
        ivTopCustomerService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_system_msg:

                break;
            case R.id.iv_top_customer_service:

                break;
        }
    }
}
