package com.mengyang.kohler.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.main.activity.CustomerServiceActivity;
import com.mengyang.kohler.main.activity.SystemMsgActivity;

/**
 * Description : 标题栏
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/loading1
 */

public class TopView extends RelativeLayout implements View.OnClickListener {
    private TextView tvTopTitle;
    private ImageView ivTopTitle;
    private ImageView ivTopBack;
    private ImageView ivTopMenu;
    private ImageView ivTopSettings;
    private ImageView ivTopCustomerService;
    private ImageView ivTopSystemMsg;
    //各icon是否显示
    private Boolean isIvTopTitle;
    private Boolean isSystemMsg;
    private Boolean isCustomerService;
    private Boolean isBack;
    private Boolean isMenu;
    private Boolean isSettings;
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
        isIvTopTitle = typedArray.getBoolean(R.styleable.TopView_is_iv_top_title, false);
        isSystemMsg = typedArray.getBoolean(R.styleable.TopView_is_system_msg, false);
        isCustomerService = typedArray.getBoolean(R.styleable.TopView_is_customer_service, false);
        isBack = typedArray.getBoolean(R.styleable.TopView_is_back, false);
        isMenu = typedArray.getBoolean(R.styleable.TopView_is_menu, false);
        isSettings = typedArray.getBoolean(R.styleable.TopView_is_settings, false);

        ivTopTitle.setVisibility(isIvTopTitle ? View.GONE : View.VISIBLE);
        ivTopSystemMsg.setVisibility(isSystemMsg ? View.GONE : View.VISIBLE);
        ivTopCustomerService.setVisibility(isCustomerService ? View.GONE : View.VISIBLE);
        ivTopBack.setVisibility(isBack ? View.GONE : View.VISIBLE);
        ivTopMenu.setVisibility(isMenu ? View.GONE : View.VISIBLE);
        ivTopSettings.setVisibility(isSettings ? View.GONE : View.VISIBLE);
    }

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initValues(context);
    }

    private void initValues(final Context context) {
        mContext = context;
        View.inflate(context, R.layout.top_view, this);
        tvTopTitle = (TextView) this.findViewById(R.id.tv_top_title);
        ivTopTitle = (ImageView) this.findViewById(R.id.iv_top_title);
        ivTopBack = (ImageView) this.findViewById(R.id.iv_top_back);
        ivTopBack.setOnClickListener(this);
        ivTopMenu = (ImageView) this.findViewById(R.id.iv_top_menu);
        ivTopMenu.setOnClickListener(this);
        ivTopSettings = (ImageView) this.findViewById(R.id.iv_top_settings);
        ivTopSettings.setOnClickListener(this);
        ivTopCustomerService = (ImageView) this.findViewById(R.id.iv_top_customer_service);
        ivTopCustomerService.setOnClickListener(this);
        ivTopSystemMsg = (ImageView) this.findViewById(R.id.iv_top_system_msg);
        ivTopSystemMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                if (mContext instanceof Activity) {
                    ((Activity) mContext).finish();
                }
                break;
            case R.id.iv_top_menu:
                break;
            case R.id.iv_top_settings:
                break;
            case R.id.iv_top_customer_service:
                App.getContext().startActivity(new Intent(App.getContext(), CustomerServiceActivity.class));
                break;
            case R.id.iv_top_system_msg:
                App.getContext().startActivity(new Intent(App.getContext(), SystemMsgActivity.class));
                break;
        }
    }
}
