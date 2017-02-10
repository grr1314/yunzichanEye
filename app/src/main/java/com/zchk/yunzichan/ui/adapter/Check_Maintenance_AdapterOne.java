package com.zchk.yunzichan.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.check.CheckMaintenanceSearchOneManager;

import java.util.List;

public class Check_Maintenance_AdapterOne extends BaseAdapter {

	private List<CheckMaintenanceSearchOneManager> mData;
	private LayoutInflater mInflater;

	public Check_Maintenance_AdapterOne(Context context,
										List<CheckMaintenanceSearchOneManager> data) {

		mInflater = LayoutInflater.from(context);
		this.mData = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.check_maintenance_list_layoutone, null);
			holder = new ViewHolder();
			holder.check_deviceName_list = (TextView) convertView
					.findViewById(R.id.check_maintenance_devicename);
			holder.check_time_list = (TextView) convertView
					.findViewById(R.id.check_maintenance_time);
			holder.check_state_list = (TextView) convertView
					.findViewById(R.id.check_maintenance_state);
			holder.check_account = (TextView) convertView
					.findViewById(R.id.check_maintenance_account);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.check_deviceName_list.setText("设备名称："+mData.get(position).deviceID);
		holder.check_time_list.setText("时间："+mData.get(position).time);
		holder.check_state_list.setText(mData.get(position).state);
		holder.check_account.setText("点检人："+mData.get(position).account);

		// 给Button添加单击事件 添加Button之后ListView将失去焦点 需要的直接把Button的焦点去掉
//		holder.check_account.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				new AlertDialog.Builder(mInflater.getContext())
//						.setTitle(mData.get(position).account + "维保详细信息")
//						.setMessage(
//								"设备名称 : " + mData.get(position).deviceID + "\n"
//										+ "点检时间 : " + mData.get(position).time
//										+ "\n" + "点检转态 : "
//										+ mData.get(position).state).show();
//			}
//		});

		return convertView;
	}

	public static class ViewHolder {

		public TextView check_deviceName_list;
		public TextView check_time_list;
		public TextView check_state_list;
		public TextView check_account;
	}
}
