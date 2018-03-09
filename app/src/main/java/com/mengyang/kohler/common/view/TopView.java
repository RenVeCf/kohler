package com.mengyang.kohler.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.activity.CustomerServiceActivity;
import com.mengyang.kohler.common.activity.SystemMsgActivity;

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
    //    private RelativeLayout rlTopBack;
    private ImageView ivTopMenu;
    private ImageView ivTopShare;
    private ImageView ivTopCustomerService;
    private ImageView ivTopSystemMsg;
    //各icon是否显示
    private Boolean isIvTopTitle;
    private Boolean isSystemMsg;
    private Boolean isCustomerService;
    private Boolean isBack;
    private Boolean isMenu;
    private Boolean isShare;
    private Context mContext;
    private PopupWindow mSharePopupWindow;
    private View mPopLayout;
    private View mShare;
    private ItemClickListenner mItemClickListenner;

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
        isShare = typedArray.getBoolean(R.styleable.TopView_is_share, false);

        ivTopTitle.setVisibility(isIvTopTitle ? View.GONE : View.VISIBLE);
        ivTopSystemMsg.setVisibility(isSystemMsg ? View.GONE : View.VISIBLE);
        ivTopCustomerService.setVisibility(isCustomerService ? View.GONE : View.VISIBLE);
        ivTopBack.setVisibility(isBack ? View.GONE : View.VISIBLE);
        ivTopMenu.setVisibility(isMenu ? View.GONE : View.VISIBLE);
        ivTopShare.setVisibility(isShare ? View.GONE : View.VISIBLE);

        mSharePopupWindow = new PopupWindow(this);
        mSharePopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mSharePopupWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        LayoutInflater inflater = LayoutInflater.from(App.getContext());
        mPopLayout = inflater.inflate(R.layout.popup_window_share, null);
        mSharePopupWindow.setContentView(mPopLayout);
        mSharePopupWindow.setBackgroundDrawable(new ColorDrawable(0x4c000000));
        mSharePopupWindow.setOutsideTouchable(false);
        mSharePopupWindow.setFocusable(true);
        mShare = mPopLayout.findViewById(R.id.iv_share_winxi);
        //        mShare.setOnClickListener(this);
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
        ivTopShare = (ImageView) this.findViewById(R.id.iv_top_share);
        ivTopShare.setOnClickListener(this);
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
                    if (((Activity) mContext).getCurrentFocus() != null) {
                        ((InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                break;
            case R.id.iv_top_menu:
                break;
            case R.id.iv_top_share:
                //                mSharePopupWindow.showAsDropDown(view, 0, 0);
                if (mItemClickListenner != null) {
                    mItemClickListenner.onItemClick();
                }
                break;
            case R.id.iv_top_customer_service:
                App.getContext().startActivity(new Intent(App.getContext(), CustomerServiceActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.iv_top_system_msg:
                App.getContext().startActivity(new Intent(App.getContext(), SystemMsgActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case R.id.iv_share_winxi:

                break;
            default:
                break;
        }
    }

    public interface ItemClickListenner {
        void onItemClick();
    }

    public void setItemClickListenner(ItemClickListenner itemClickListenner) {
        mItemClickListenner = itemClickListenner;
        mItemClickListenner = itemClickListenner;
    }
}
