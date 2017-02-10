package com.zchk.yunzichan.ui.activity.system;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;

/**
 * @author SenseTech
 *
 */
public class TextSetActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_set_activity);
		initViews();
	}

	private void initViews() {
		initTopBar("字体设置", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {
			@Override
			public void onClick(View v) {
			finish();
			}
		});
	}
}
