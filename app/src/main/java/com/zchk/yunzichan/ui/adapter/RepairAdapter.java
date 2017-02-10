package com.zchk.yunzichan.ui.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;

/**
 * ���޵��б�������
 * 
 * @author SenseTech
 * 
 */
public class RepairAdapter extends BaseAdapter {

	MaintenanceOrderProperty item;

	private Context mContext;

	LayoutInflater mInflater;

	private List<MaintenanceOrderProperty> ls;

	public RepairAdapter(List<MaintenanceOrderProperty> ls, Context mContext) {
		super();
		this.ls = ls;
		this.mContext = mContext;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ls.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return ls.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder mViewHolder = null;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_repair, arg2, false);
			mViewHolder.item_repair_title = (TextView) convertView
					.findViewById(R.id.item_repair_title);
			mViewHolder.item_repair_reportPerson = (TextView) convertView
					.findViewById(R.id.item_repair_reportPerson);
			mViewHolder.item_repair_telephone = (TextView) convertView
					.findViewById(R.id.item_repair_telephone);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}

		item = (MaintenanceOrderProperty) getItem(arg0);
		mViewHolder.item_repair_title.setText("编号:" + item.orderCode);
		mViewHolder.item_repair_reportPerson
				.setText("点检人:" + item.reportPerson);
		mViewHolder.item_repair_telephone.setText("电话:" + item.telephone);
		return convertView;
	}

	class ViewHolder {
		TextView item_repair_title;
		TextView item_repair_reportPerson;// ������
		TextView item_repair_telephone;
	}

}
