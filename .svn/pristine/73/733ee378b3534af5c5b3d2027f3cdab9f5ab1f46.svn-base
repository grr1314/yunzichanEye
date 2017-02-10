package com.zchk.yunzichan.ui.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zchk.yunzichan.R;

/**
 * ϵͳ��ҳ��GridView��Adapter
 * @author SenseTech
 *
 */
public class MainGridViewAdapter extends BaseAdapter {

	private List<Map<String,Object>>list=new ArrayList<Map<String, Object>>();
	private Context mContext;
	LayoutInflater mInflater;
	public MainGridViewAdapter(List<Map<String, Object>> list, Context mContext) {
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
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
	 
		ViewHolder mViewHolder=null;
		if (convertView==null) {
			mViewHolder=new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_main_gv, arg2, false);
			mViewHolder.img=(ImageView) convertView.findViewById(R.id.item_gv_img);
			mViewHolder.text=(TextView) convertView.findViewById(R.id.item_gv_tv);
			convertView.setTag(mViewHolder);
		}
		else
		{
			mViewHolder=(ViewHolder) convertView.getTag();
		}
		Map<String,Object>item=(Map<String, Object>) getItem(arg0);
		mViewHolder.img.setImageResource((Integer)item.get("img"));
		mViewHolder.text.setText(item.get("title").toString());
		return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView text;
		
	}

}
