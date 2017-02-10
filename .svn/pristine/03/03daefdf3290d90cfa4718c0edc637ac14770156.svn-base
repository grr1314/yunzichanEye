package com.zchk.yunzichan.ui.activity;

import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.entity.bean.UserInfo;


public class UserInfoActivity extends BaseActivity {

	ListView text;
	private DBUtils dbu;
	List<Map<String, Object>> ls;
	private UserInfo userInfo = null;
	private MyApplication application;

	private TextView tv_userName;
	private TextView tv_Permission;
	private TextView tv_Company;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		userInfo=MyApplication.userInfo;
		setContentView(R.layout.text);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		initTopBar("用户信息", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		tv_userName = (TextView) findViewById(R.id.tv_userName);
		tv_Permission = (TextView) findViewById(R.id.tv_Permission);
		tv_Company=(TextView) findViewById(R.id.tv_Company);
		text = (ListView) findViewById(R.id.text);
		dbu = new DBUtils(initApplication());
		ls = dbu.selectDevice();
		text.setAdapter(new MyAdapter());
		text.setVisibility(View.GONE);
		putData();
	}

	private void putData() {
		// TODO Auto-generated method stub
		tv_userName.setText("账号:"+userInfo.getAccount());
		tv_Permission.setText("职位:"+userInfo.getRoleName());
		tv_Company.setText("用户名:"+userInfo.getUserName());
	}

	/**
	 *
	 * @author SenseTech
	 * 
	 */
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ls.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return ls.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder mViewHolder = null;
			if (convertView == null) {
				mViewHolder = new ViewHolder();
				convertView = LayoutInflater.from(UserInfoActivity.this).inflate(
						R.layout.item_check, null);
				mViewHolder.title = (TextView) convertView
						.findViewById(R.id.title);
				convertView.setTag(mViewHolder);
			} else {
				mViewHolder = (ViewHolder) convertView.getTag();
			}
			Map<String, Object> item = (Map<String, Object>) getItem(position);
			mViewHolder.title.setText(item.get("lableCode").toString());
			return convertView;
		}

	}

	class ViewHolder {
		TextView title;
	}
}
