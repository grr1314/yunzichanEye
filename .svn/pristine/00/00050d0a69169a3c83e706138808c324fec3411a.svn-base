package com.zchk.yunzichan.ui.activity.realtime;

import android.os.Bundle;

import android.view.View;


import com.zchk.yunzichan.R;

import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.fragment.leader.CheckQueryFragment;
import com.zchk.yunzichan.utils.Constant;

public class realTimeDataActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.leader_charts_activity);
		if (savedInstanceState == null) {
			Bundle args = new Bundle();
			args.putInt("id", Constant.realTimeDataActivity);
			CheckQueryFragment fragment = new CheckQueryFragment();
			fragment.setArguments(args);
			getSupportFragmentManager().beginTransaction().add(R.id.leader_chect_content, fragment).commit();
		}
		initViews();

	}

	private void initViews() {
		initTopBar("实时数据", true, false, true);
		initTopBarListener(null, null, new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
	}

}
