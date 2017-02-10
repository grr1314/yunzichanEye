package com.zchk.yunzichan.ui.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.FileNameMessage;

public class RelevantFileAdapter extends BaseAdapter {

	private List<FileNameMessage> mData;
	private LayoutInflater mInflater;
	private Context context;

	public RelevantFileAdapter(Context context, List<FileNameMessage> data) {

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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.relevant_file_item, null);
			holder = new ViewHolder();
			holder.relevan_file = (TextView) convertView
					.findViewById(R.id.file_name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.relevan_file.setText(mData.get(position).getFileName());

		return convertView;
	}

	public static class ViewHolder {

		public TextView relevan_file;
	}

}