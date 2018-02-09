package com.mengyang.kohler.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mengyang.kohler.R;
import com.mengyang.kohler.module.bean.QuestionSearchBean;

import java.util.List;

/**
 * Created by liusong on 2018/2/9.
 */

public class CompanyServiceAdapter extends RecyclerView.Adapter<CompanyServiceAdapter.MyViewHolder> {
    private List<QuestionSearchBean> mData ;
    private Context mContext ;
    private LayoutInflater inflater;

    public void addData() {

    }

//    public CompanyServiceAdapter(Context context, List<QuestionSearchBean> data) {
    public CompanyServiceAdapter(Context context, List<QuestionSearchBean> data) {
        mData = data;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return new CompanyServiceAdapter.MyViewHolder(inflater.inflate(R.layout.item_service_company, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTvServiecUser.setText("liusong");
        holder.mTvServiceTime.setText("16:16");
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
   }
}
