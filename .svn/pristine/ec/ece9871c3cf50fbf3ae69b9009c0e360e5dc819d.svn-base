package com.zchk.yunzichan.ui.adapter;

import java.util.List;
import java.util.Map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;

public class RealTimeDataAdapter extends BaseAdapter {

	private Context mContext;
	private List<Map<String, Object>> list;
	private LayoutInflater mInflater;

	public RealTimeDataAdapter(Context mContext, List<Map<String, Object>> list) {
		super();
		this.mContext = mContext;
		this.list = list;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder = null;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_realtime, null);
			mViewHolder.item_nameCn = (TextView) convertView
					.findViewById(R.id.item_nameCn);
			mViewHolder.item_Value = (TextView) convertView
					.findViewById(R.id.item_Value);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		Map<String, Object> item = (Map<String, Object>) getItem(position);
		mViewHolder.item_nameCn.setText("设备名称" + item.get("nameCn").toString());
		mViewHolder.item_Value
				.setText("实时数据" + item.get("realdata").toString());
		return convertView;
	}

	private class ViewHolder {
		TextView item_nameCn;
		TextView item_Value;
	}
}
