package com.mengyang.kohler.whole_category.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.common.utils.LogUtils;
import com.mengyang.kohler.module.bean.NotSelectClassificationBean;
import com.mengyang.kohler.whole_category.activity.CommodityClassificationActivity;
import com.mengyang.kohler.whole_category.activity.SelectClassificationActivity;
import com.mengyang.kohler.whole_category.fragment.WholeCategoryFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJJ on 2017/loading3/7.
 */

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<NotSelectClassificationBean> datas;
//    private List<NotSelectClassificationBean> datasUsed = new ArrayList<>();
    private List<NotSelectClassificationBean> datasUsed ;
//    private OnWholeCategoryItem mListener;
    private Context context;
    private boolean vertical;

    public StackAdapter(List<NotSelectClassificationBean> datas) {
        this.datasUsed = datas;
//        for (int i = 0; i < datas.size(); i++) {
//            if (!TextUtils.isEmpty(datas.get(i).getKvUrl())) {
//                datasUsed.add(datas.get(i));
//            }
//        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(parent.getContext());
        }
        //        if (vertical)
        //            return new ViewHolder(inflater.inflate(R.layout.vertical_item_card, parent, false));
        return new ViewHolder(inflater.inflate(R.layout.item_whole_category_card, parent, false));
    }

    //    public StackAdapter vertical() {
    //        this.vertical = true;
    //        return this;
    //    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cardViewTotal.setBackgroundColor(Color.TRANSPARENT);//设置透明背景去掉cardview的背景颜色

        if (!TextUtils.isEmpty(datasUsed.get(position).getKvUrl())) {
            Glide.with(context).load(datasUsed.get(position).getKvUrl()).apply(new RequestOptions().placeholder(R.mipmap.queshengtu)).into(holder.cover);
        } else {
//            holder.cardViewTotal.setBackgroundColor(Color.WHITE);
        }

    }

//    public interface OnWholeCategoryItem {
//        // TODO: Update argument type and name
//        void onWholeCategoryItem(String data);
//    }

    @Override
    public int getItemCount() {
        return datasUsed == null ? 0 : datasUsed.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        CardView cardViewTotal;
        //        TextView index;

        public ViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            cardViewTotal = (CardView) itemView.findViewById(R.id.card_view_total);
            //            index = (TextView) itemView.findViewById(R.id.index);
            //全品类 item页 点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == (datasUsed.size() - 1)) {
//                        mListener.onWholeCategoryItem("科勒");
                        App.getContext().startActivity(new Intent(App.getContext(), SelectClassificationActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } else {
//                        mListener.onWholeCategoryItem(datasUsed.get(getAdapterPosition()).getNameCn());
                        App.getContext().startActivity(new Intent(App.getContext(), CommodityClassificationActivity.class).putExtra("id", datasUsed.get(getAdapterPosition()).getId() + "").addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                }
            });
        }
    }
}
