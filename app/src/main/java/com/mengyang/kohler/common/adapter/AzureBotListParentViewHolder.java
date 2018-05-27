package com.mengyang.kohler.common.adapter;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;

/**
 * Created by MengYang on 2018/5/26.
 */
public class AzureBotListParentViewHolder extends BaseViewHolder {
    private Context mContext;
    private View view;
    private LinearLayout containerLayout;
    private TextView parentLeftView;
    private TextView parentRightView;
    private ImageView expand;

    public AzureBotListParentViewHolder(Context context, View itemView) {
        super(itemView);
        this.mContext = context;
        this.view = itemView;
    }

    public void bindView(final DataBean dataBean, final int pos, final ItemClickListener listener) {

        containerLayout = (LinearLayout) view.findViewById(R.id.ll_azure_bot_parent_container);
        parentLeftView = (TextView) view.findViewById(R.id.tv_azure_bot_parent_start);
        parentRightView = (TextView) view.findViewById(R.id.tv_azure_bot_parent_end);
        expand = (ImageView) view.findViewById(R.id.iv_azure_bot_parent);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) expand
                .getLayoutParams();
        expand.setLayoutParams(params);
        parentLeftView.setText(dataBean.getParentLeftTxt());
        parentRightView.setText(dataBean.getParentRightTxt());

//        if (dataBean.isExpand()) {
//            expand.setRotation(90);
//        } else {
//            expand.setRotation(0);
//        }

        //父布局OnClick监听
        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    if (dataBean.isExpand()) {
                        listener.onHideChildren(dataBean);
                        dataBean.setExpand(false);
                        rotationExpandIcon(90, 0);
                    } else {
                        listener.onExpandChildren(dataBean);
                        dataBean.setExpand(true);
                        rotationExpandIcon(0, 90);
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void rotationExpandIcon(float from, float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);//属性动画
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    expand.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }
}
