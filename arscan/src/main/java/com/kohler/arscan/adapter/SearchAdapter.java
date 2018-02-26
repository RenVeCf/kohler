package com.kohler.arscan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kohler.arscan.R;
import com.kohler.arscan.bean.SearchItem;

import java.util.List;

/**
 * Created by chenchen on 18-1-2.
 */

public class SearchAdapter extends BaseAdapter {

    private final static String TAG = BaseAdapter.class.getSimpleName();

    private Context mContext;
    private List<SearchItem> mList;

    public SearchAdapter(Context context, List<SearchItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mList.get(position) != null && !mList.isEmpty()) {
            mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.search_item, parent, false);
            holder.iv_image = convertView.findViewById(R.id.iv_image);
            holder.tv_content = convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SearchItem item = mList.get(position);
        holder.iv_image.setImageResource(item.getImage());
        holder.tv_content.setText(item.getContent());

        return convertView;
    }

    private class ViewHolder {
        ImageView iv_image;
        TextView tv_content;
    }
}
