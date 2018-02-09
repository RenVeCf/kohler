package com.mengyang.kohler.common.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.UserMsg;

import java.util.List;

/**
 * Created by liusong on 2018/2/9.
 */

//public class UserServiceAdapter extends RecyclerView.Adapter<UserServiceAdapter.MyViewHolder> {
public class UserServiceAdapter extends BaseQuickAdapter<UserMsg, BaseViewHolder> {

    public UserServiceAdapter(List<UserMsg> data) {
        super(R.layout.item_service_user,data);
    }


    @Override
    protected void convert(BaseViewHolder helper, UserMsg item) {
        helper.setText(R.id.tv_service_message,item.getContent());
    }










/*    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return new UserServiceAdapter.MyViewHolder(inflater.inflate(R.layout.item_service_user, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvServiecUser.setText("liusong");
        holder.mTvServiceTime.setText("16:16");
        holder.mTvServiceMessage.setText(mData.get(position));
    }

    @Override
    protected void convert(MyViewHolder helper, List<String> item) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvServiecUser;
        private TextView mTvServiceTime;
        private TextView mTvServiceMessage;
        private RecyclerView mRecyclerViewServiceList;

        public MyViewHolder(View itemView) {
           super(itemView);

            mTvServiecUser = itemView.findViewById(R.id.tv_serviec_user);
            mTvServiceTime = itemView.findViewById(R.id.tv_service_time);
            mTvServiceMessage = itemView.findViewById(R.id.tv_service_message);
            mRecyclerViewServiceList = itemView.findViewById(R.id.recycler_view_service_list);
       }
   }*/
}
