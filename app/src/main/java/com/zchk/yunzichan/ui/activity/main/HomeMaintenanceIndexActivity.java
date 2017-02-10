package com.zchk.yunzichan.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.MipcaActivityCapture;
import com.zchk.yunzichan.ui.activity.maintenance.MaintenanceByIdActivity;
import com.zchk.yunzichan.ui.activity.maintenance.MaintenanceQueryActivity;


/**
 * 维保界面
 *
 * @author SenseTech
 *
 */
public class HomeMaintenanceIndexActivity extends BaseActivity implements
		OnClickListener {
	public LinearLayout check_sao_cha;
	public TextView maintenance_deviceid;
	public ImageView maintenance_sao;
	public ImageView maintenance_cha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintenance_index);
		initView();
		initEven();
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	public void initView() {
		check_sao_cha = (LinearLayout) findViewById(R.id.check_sao_cha);
		maintenance_deviceid = (TextView) findViewById(R.id.maintenance_deviceid);
		maintenance_sao = (ImageView) findViewById(R.id.maintenance_sao);
		maintenance_cha = (ImageView) findViewById(R.id.maintenance_cha);
		// 初始化topbar
		initTopBar("设备维修", true, false, true);
	}

	public void initEven() {
		check_sao_cha.setOnClickListener(this);
		maintenance_deviceid.setOnClickListener(this);
		maintenance_sao.setOnClickListener(this);
		maintenance_cha.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.maintenance_deviceid: {
				intentFinish(R.id.maintenance_deviceid,
						MaintenanceByIdActivity.class);
			}
			break;
			case R.id.maintenance_sao: {
				intentFinish(R.id.maintenance_sao, MipcaActivityCapture.class);
			}
			break;
			case R.id.maintenance_cha: {

				intentFinish(R.id.maintenance_cha, MaintenanceQueryActivity.class);
			}
			break;
			default:
				break;
		}
	}
	public void intentFinish(int pos, Class cls) {
		Intent intent = new Intent();
		intent.setClass(HomeMaintenanceIndexActivity.this, cls);
		if (pos == R.id.maintenance_sao) {
			intent.putExtra("ClassNamecn", "HomeMaintenanceIndexActivity");
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}
}
