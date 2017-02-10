package com.zchk.yunzichan.ui.activity.system;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;


public class AboutUsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_activity);
		initViews();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		initTopBar("关于我们", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
