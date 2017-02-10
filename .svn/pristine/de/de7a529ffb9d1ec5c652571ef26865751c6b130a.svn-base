package com.zchk.yunzichan.ui.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;

public class SpinnerAdapter extends BaseAdapter {

	private Context mContext;
	private List<String>list;
	private LayoutInflater mInflater;
	
	public SpinnerAdapter(Context mContext, List<String> list) {
		super();
		this.mContext = mContext;
		this.list = list;
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
			convertView=mInflater.inflate(R.layout.spinner_item, null);
			mViewHolder.item_tv_sp=(TextView) convertView.findViewById(R.id.item_tv_sp);
			convertView.setTag(mViewHolder);
		}
		else
		{
			mViewHolder=(ViewHolder) convertView.getTag();
		}
		mViewHolder.item_tv_sp.setText(getItem(position).toString());
		return convertView;
	}
	private class ViewHolder
	{
		TextView item_tv_sp;
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return super.getDropDownView(position, convertView, parent);
	}

}
