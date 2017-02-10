package com.zchk.yunzichan.ui.activity.repair;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;


public class RepairSearchByIdActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repair_search_byid);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		initTopBar("报修单", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}