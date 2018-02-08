package com.mengyang.kohler.whole_category.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.mengyang.kohler.App;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.NotSelectClassificationBean;
import com.mengyang.kohler.whole_category.activity.CommodityClassificationActivity;
import com.mengyang.kohler.whole_category.activity.SelectClassificationActivity;
import com.mengyang.kohler.whole_category.fragment.WholeCategoryFragment;

import java.util.List;

/**
 * Created by CJJ on 2017/loading3/7.
 */

public class StackAdapter extends RecyclerView.Adapter<StackAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<NotSelectClassificationBean> datas;
    private OnWholeCategoryItem mListener;
    private Context context;
    private boolean vertical;

    public StackAdapter(List<NotSelectClassificationBean> datas) {
        this.datas = datas;
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
        Glide.with(context).load(datas.get(position).getKvUrl()).into(holder.cover);
        //        Glide.with(context).load(imageUrls.get(position)).into(holder.cover);
        //        holder.index.setText(datas.get(holder.getAdapterPosition()));
    }

    public interface OnWholeCategoryItem {
        // TODO: Update argument type and name
        void onWholeCategoryItem(String data);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cover;
        //        TextView index;

        public ViewHolder(View itemView) {
            super(itemView);
            cover = (ImageView) itemView.findViewById(R.id.cover);
            //            index = (TextView) itemView.findViewById(R.id.index);
            //全品类 item页 点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == (datas.size() - 1)) {
                        mListener.onWholeCategoryItem("科勒");
                        App.getContext().startActivity(new Intent(App.getContext(), SelectClassificationActivity.class));
                    } else {
                        mListener.onWholeCategoryItem(datas.get(getAdapterPosition()).getNameCn());
                        App.getContext().startActivity(new Intent(App.getContext(), CommodityClassificationActivity.class).putExtra("id", datas.get(getAdapterPosition()).getId() + ""));
                    }
                }
            });
        }
    }
}
