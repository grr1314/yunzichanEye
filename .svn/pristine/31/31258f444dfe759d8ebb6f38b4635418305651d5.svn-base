package com.zchk.yunzichan.ui.adapter;

import java.util.ArrayList;
import java.util.List;


import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.model.check.TemplateInfos;

/**
 * 点检信息的adapter
 *
 * @author SenseTech
 *
 */
public class CheckElevatorAdapter extends BaseAdapter {

	private Context mContext;
	List<TemplateInfos> list;
	LayoutInflater mInflater;
	private String temp;

	public CheckElevatorAdapter(Context mContext, List<TemplateInfos> list,
								String temp) {
		super();
		this.mContext = mContext;
		this.list = list;
		mInflater = LayoutInflater.from(mContext);
		this.temp = temp;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		// list.get(position).
		ViewHolder mViewHolder = null;
		if (convertView == null) {
			mViewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_checkelevator, null);
			mViewHolder.title = (TextView) convertView
					.findViewById(R.id.tv_title);
			mViewHolder.sp_content = (Spinner) convertView
					.findViewById(R.id.sp_status);
			mViewHolder.ed_content = (EditText) convertView
					.findViewById(R.id.ed_content);
			convertView.setTag(mViewHolder);
		} else {
			mViewHolder = (ViewHolder) convertView.getTag();
		}
		TemplateInfos ti = (TemplateInfos) getItem(position);

		// Log.e(temp, ti.allValue);
		mViewHolder.title.setText(ti.nameCn);

		// 如果数据类型是select是则显示Spinner
		if (ti.dataType.equals("select") || ti.dataType.equals("radio")) {
			int defaultPos = 0;
			if (mViewHolder.ed_content.getVisibility() == View.VISIBLE) {
				mViewHolder.ed_content.setVisibility(View.GONE);
				mViewHolder.sp_content.setVisibility(View.VISIBLE);
			}
			String[] s = dealData(ti.allValue);
			List<String> ls = new ArrayList<String>();
			for (int i = 0; i < s.length; i++) {
				ls.add(s[i]);
				if (s[i].equals(ti.defaultValue)) {
					defaultPos = i;
				}
			}
			// 填装Spinner数据
			SpinnerAdapter adapter = new SpinnerAdapter(mContext, ls);
			mViewHolder.sp_content.setAdapter(adapter);
			// 设置默认选中的值
			mViewHolder.sp_content.setSelection(defaultPos);

		}
		// 如果数据是chart是则显示文本框
		else if (ti.dataType.equals("chart")) {
			if (mViewHolder.sp_content.getVisibility() == View.VISIBLE) {
				mViewHolder.sp_content.setVisibility(View.GONE);
				mViewHolder.ed_content.setVisibility(View.VISIBLE);
			}
			if (temp.equals("SensePOM") && !ti.nameCn.equals("备注")) {
				mViewHolder.ed_content
						.setInputType(InputType.TYPE_CLASS_NUMBER);
			}
		}
		// 获取当前控件里面的数据
		return convertView;
	}

	class ViewHolder {
		TextView title;
		Spinner sp_content;
		EditText ed_content;
	}

	private String[] dealData(String str) {
		String s[];
		s = str.split(",");
		return s;
	}

}
