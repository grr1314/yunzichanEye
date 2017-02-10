package com.zchk.yunzichan.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/2.
 */
public class CheckAllRouteAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Map<String, Object>> list;

    public CheckAllRouteAdapter(Context mContext, List<Map<String, Object>> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder mViewHolder = null;
        if (convertView == null) {
            mViewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_check_allroute, null);
            mViewHolder.tv_routeName = (TextView) convertView.findViewById(R.id.tv_routeName);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.tv_routeName.setText(list.get(position).get("routeName").toString());
        return convertView;
    }


    private class ViewHolder {
        TextView tv_routeName;
    }
}
