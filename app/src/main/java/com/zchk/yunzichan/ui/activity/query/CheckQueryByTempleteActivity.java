package com.zchk.yunzichan.ui.activity.query;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zchk.yunzichan.R;


public class CheckQueryByTempleteActivity extends Activity {

	private Button elv_btn;
	private Button fer_btn;
	private Button pom_btn;
	private Button lift_btn;
	private Button maintenance_btn;
	
	private Intent intent = new Intent();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.check_templete_item);
		initView();
		initEven();
	}

	public void initView() {
		elv_btn = (Button) findViewById(R.id.elv_btn);
		fer_btn = (Button) findViewById(R.id.fer_btn);
		pom_btn = (Button) findViewById(R.id.pom_btn);
		lift_btn = (Button) findViewById(R.id.lift_btn);
		maintenance_btn = (Button)findViewById(R.id.maintenance_btn);
	}

	public void initEven() {
		elv_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.putExtra("tableName", "SenseELV");
				intent.setClass(CheckQueryByTempleteActivity.this, CheckInfoByTempletelocalActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});
		
		fer_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.putExtra("tableName", "SenseFER");
				intent.setClass(CheckQueryByTempleteActivity.this, CheckInfoByTempletelocalActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});
		
		pom_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.putExtra("tableName", "SensePOM");
				intent.setClass(CheckQueryByTempleteActivity.this, CheckInfoByTempletelocalActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});
		
		lift_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.putExtra("tableName", "SenseLIFT");
				intent.setClass(CheckQueryByTempleteActivity.this, CheckInfoByTempletelocalActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});
		
		maintenance_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				intent.setClass(CheckQueryByTempleteActivity.this, MaintenanceInfoByTempletelocalActivity.class);
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			}
		});
	}
}
