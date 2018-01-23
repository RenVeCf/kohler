package com.mengyang.kohler.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.ToastUtil;
import com.mengyang.kohler.main.activity.MainActivity;

import butterknife.OnClick;

/**
 * Description : 标题栏
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2017/11/1
 */

public class TopView extends RelativeLayout implements View.OnClickListener {
    private TextView tvTopTitle;
    private ImageView ivTopBack;
    private ImageView ivTopMenu;
    private ImageView ivTopSettings;
    private ImageView ivTopCustomerService;
    private ImageView ivTopSystemMsg;
    //各icon是否显示
    private Boolean isSystemMsg;
    private Boolean isCustomerService;
    private Boolean isBack;
    private Boolean isMenu;
    private Boolean isSettings;

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
        isCustomerService = typedArray.getBoolean(R.styleable.TopView_is_customer_service, false);
        isBack = typedArray.getBoolean(R.styleable.TopView_is_back, false);
        isMenu = typedArray.getBoolean(R.styleable.TopView_is_menu, false);
        isSettings = typedArray.getBoolean(R.styleable.TopView_is_settings, false);

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

    private void initValues(Context context) {
        View.inflate(context, R.layout.top_view, this);
        tvTopTitle = findViewById(R.id.tv_top_title);
        tvTopTitle.setOnClickListener(this);
        ivTopBack = findViewById(R.id.iv_top_back);
        ivTopBack.setOnClickListener(this);
        ivTopMenu = findViewById(R.id.iv_top_menu);
        ivTopMenu.setOnClickListener(this);
        ivTopSettings = findViewById(R.id.iv_top_settings);
        ivTopSettings.setOnClickListener(this);
        ivTopCustomerService = findViewById(R.id.iv_top_customer_service);
        ivTopCustomerService.setOnClickListener(this);
        ivTopSystemMsg = findViewById(R.id.iv_top_system_msg);
        ivTopSystemMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_top_back:
                break;
            case R.id.iv_top_menu:
                MainActivity mainActivity = new MainActivity();
                mainActivity.rlHome.openPane();
                break;
            case R.id.iv_top_settings:
                break;
            case R.id.iv_top_customer_service:
                break;
            case R.id.iv_top_system_msg:
                break;
        }
    }
}
