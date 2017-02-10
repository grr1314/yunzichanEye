package com.zchk.yunzichan.ui.adapter;

import java.util.List;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.bean.check.CheckSearchManager;

public class CheckAccountAdapter extends BaseAdapter {
	
	private List<CheckSearchManager> mData;
    private LayoutInflater mInflater; 
    
    
    public CheckAccountAdapter(Context context,List<CheckSearchManager> data) {
      	 
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;  
		if (convertView == null) {  
           convertView = mInflater.inflate(R.layout.check_search_list_layout, null);  
           holder = new ViewHolder();  
           holder.check_deviceName_list = (TextView)convertView.findViewById(R.id.check_deviceName_list);
           holder.check_time_list = (TextView)convertView.findViewById(R.id.check_time_list);
           holder.check_state_list = (TextView)convertView.findViewById(R.id.check_state_list);
           convertView.setTag(holder);  
       } 
		else {  
           holder = (ViewHolder)convertView.getTag();  
       }  
       holder.check_deviceName_list.setText(mData.get(position).getDeviceID());
       holder.check_time_list.setText(mData.get(position).getTime());
       holder.check_state_list.setText(mData.get(position).getState());
       return convertView;  
	}
	
	public static class ViewHolder {  
	    public TextView check_deviceName_list;
	    public TextView check_time_list; 
	    public TextView check_state_list;
	}  

}