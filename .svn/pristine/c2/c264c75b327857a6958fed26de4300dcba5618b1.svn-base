package com.zchk.yunzichan.ui.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.check.CheckOneDevice;

public class CheckOneDeviceAdapter extends BaseAdapter {

	private List<CheckOneDevice> list;
	private Context mContext;
	private LayoutInflater mInflater;


	public CheckOneDeviceAdapter(List<CheckOneDevice> list, Context mContext) {
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
			convertView=mInflater.inflate(R.layout.item_check_onedevice,parent, false);
			mViewHolder.item_tv_time=(TextView) convertView.findViewById(R.id.item_tv_time);
			mViewHolder.item_tv_factory=(TextView) convertView.findViewById(R.id.item_tv_factory);
			mViewHolder.item_tv_number=(TextView) convertView.findViewById(R.id.item_tv_number);
			convertView.setTag(mViewHolder);
		}
		else
		{
			mViewHolder=(ViewHolder) convertView.getTag();
		}
		CheckOneDevice check=(CheckOneDevice) getItem(position);
		mViewHolder.item_tv_time.setText("点检时间："+check.getCheckTime());
		mViewHolder.item_tv_factory.setText("设备厂商："+check.getDeviceManufacturer());
		mViewHolder.item_tv_number.setText("设备名称："+check.getDeviceName());
		return convertView;
	}
	class ViewHolder
	{
		TextView item_tv_time;
		TextView item_tv_factory;
		TextView item_tv_number;
	}

}

