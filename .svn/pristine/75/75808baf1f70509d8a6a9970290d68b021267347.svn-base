package com.zchk.yunzichan.ui.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.service.FxService;

/**
 * �������
 * @author SenseTech
 *
 */
public class NetworkCheckActivity extends Activity {

	private Button start;
	private Button remove;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_main_layout);
		initView();
		initEven();
	}

	private void initView() {
		start = (Button) findViewById(R.id.network_open);
		remove = (Button) findViewById(R.id.network_end);
	}

	private void initEven() {
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(NetworkCheckActivity.this,
						FxService.class);
				startService(intent);
			}
		});

		remove.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(NetworkCheckActivity.this,
						FxService.class);
				stopService(intent);
			}
		});
	}
}
