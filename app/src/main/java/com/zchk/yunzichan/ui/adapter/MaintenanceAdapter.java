package com.zchk.yunzichan.ui.adapter;

import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.MaintenanceSearchManager;
import com.zchk.yunzichan.ui.activity.file.RelevantFileActivity;

public class MaintenanceAdapter extends BaseAdapter {

	private List<MaintenanceSearchManager> mData;
	private LayoutInflater mInflater;
	public Context context;

	public MaintenanceAdapter(Context context,
			List<MaintenanceSearchManager> data) {

		mInflater = LayoutInflater.from(context);
		this.mData = data;
		this.context = context;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.maintenance_search_list_layout, null);
			holder = new ViewHolder();
			holder.maintenance_deviceName_list = (TextView) convertView
					.findViewById(R.id.maintenance_devicename_list);
			holder.maintenance_time_list = (TextView) convertView
					.findViewById(R.id.maintenance_time_list);
			holder.maintenance_note_list = (TextView) convertView
					.findViewById(R.id.maintenance_note_list);
			holder.maintenance_ziliao = (TextView) convertView
					.findViewById(R.id.maintenance_text_list);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		String NameCn = "设备名称：" + mData.get(position).deviceID;
		holder.maintenance_deviceName_list.setText(NameCn);
		holder.maintenance_time_list.setText(mData.get(position).time);
		holder.maintenance_note_list.setText(mData.get(position).note);

		holder.maintenance_ziliao
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						Intent intent = new Intent();
						intent.setClass(context, RelevantFileActivity.class);
						context.startActivity(intent);
						((Activity) context).overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
					}
				});

		return convertView;
	}

	public static class ViewHolder {

		public TextView maintenance_deviceName_list;
		public TextView maintenance_time_list;
		public TextView maintenance_note_list;
		public TextView maintenance_ziliao;
	}

}
