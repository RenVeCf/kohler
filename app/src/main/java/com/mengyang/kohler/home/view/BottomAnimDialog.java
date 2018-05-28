package com.mengyang.kohler.home.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.mengyang.kohler.R;

/**
 * Description :
 * Author : rmy
 * Email : 942685687@qq.com
 * Time : 2018/3/19
 */

public class BottomAnimDialog extends Dialog {

    private final Context mContext;
    private final String mItem1Name;
    private final String mItem3Name;
    private BottonAnimDialogListener mListener;
    private Button mTvItem1;
    private Button mTvItem3;

    public BottomAnimDialog(Context context, String item1Name, String item3Name) {

        super(context, R.style.dialog);
        this.mContext = context;

        this.mItem1Name = item1Name;
        this.mItem3Name = item3Name;

        initView();

    }


    private void initView() {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.bottom_anim_dialog_layout, null);

        Window window = this.getWindow();
        if (window != null) { //设置dialog的布局样式 让其位于底部
//            window.setGravity(Gravity.BOTTOM);
//            WindowManager.LayoutParams lp = window.getAttributes();
//            lp.y = DensityUtil.dip2px(mContext, 10); //设置居于底部的距离
//            window.setAttributes(lp);
        }

        mTvItem1 = (Button) view.findViewById(R.id.tv_item1);
        mTvItem3 = (Button) view.findViewById(R.id.tv_item3);

        mTvItem1.setOnClickListener(new clickListener());
        mTvItem3.setOnClickListener(new clickListener());

        setContentView(view);

        setData();
    }


    private void setData() {
        mTvItem1.setText(mItem1Name);
        mTvItem3.setText(mItem3Name);
    }

    public void setItem1TextColor(int colorId) {//设置item的字体颜色
        if (mTvItem1 != null) {
            mTvItem1.setTextColor(colorId);
        }
    }

    public void setItem3TextColor(int colorId) {
        if (mTvItem3 != null) {
            mTvItem3.setTextColor(colorId);
        }
    }

    public void setClickListener(BottonAnimDialogListener listener) {
        this.mListener = listener;
    }

    public interface BottonAnimDialogListener {
        void onItem1Listener();

        void onItem3Listener();
    }


    private class clickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_item1:
                    if (mListener != null) {
                        mListener.onItem1Listener();
                    }
                    break;
                case R.id.tv_item3:
                    if (mListener != null) {
                        mListener.onItem3Listener();
                    }
                    break;
            }
        }
    }
}
