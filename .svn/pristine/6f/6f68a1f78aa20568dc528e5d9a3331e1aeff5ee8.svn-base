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

public class CheckLocAdapter extends BaseAdapter {
	

	private List<Map<String,Object>>list;
	private LayoutInflater mInflater;
	private Context mContext;
	
	public CheckLocAdapter(Context mContext,List<Map<String,Object>> list) {
		super();
		this.list = list;
		this.mContext = mContext;
		mInflater=LayoutInflater.from(mContext);
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
		ViewHolder mViewHolder=null;
		if (convertView==null) {
			mViewHolder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_check, parent, false);
			mViewHolder.title=(TextView) convertView.findViewById(R.id.title);
//			AlarmFlag
			convertView.setTag(mViewHolder);
		}
		else
		{
			mViewHolder=(ViewHolder) convertView.getTag();
		}
		@SuppressWarnings("unchecked")
		Map<String,Object>ls= (Map<String, Object>) getItem(position);
		mViewHolder.title.setText(ls.get("AlarmLamp").toString());
		
		return convertView;
	}
	
	private class ViewHolder{
		TextView title;
	}

}
