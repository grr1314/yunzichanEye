package com.zchk.yunzichan.ui.activity.repair;

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


public class RepairIndexActivity extends BaseActivity implements OnClickListener{

	private LinearLayout check_sao_cha;
	private TextView check_deviceid;
	private ImageView check_sao;
	private ImageView check_cha;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repair_activity);
		initView();
		initListener();
	}
	private void initListener() {
		// TODO Auto-generated method stub
		check_deviceid.setOnClickListener(this);
		check_sao.setOnClickListener(this);
		check_cha.setOnClickListener(this);
	}
	public void initView() {
		check_sao_cha = (LinearLayout) findViewById(R.id.check_sao_cha);
		check_deviceid = (TextView) findViewById(R.id.check_deviceid);
		check_sao = (ImageView) findViewById(R.id.check_sao);
		check_cha = (ImageView) findViewById(R.id.check_cha);


		initTopBar("报修单", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.check_deviceid:
			{
				//通过设备id来查询
				IntentToWards(RepairSearchByIdActivity.class);
			}
			break;
			case R.id.check_sao:
			{
				IntentToWards(MipcaActivityCapture.class);
			}
			break;
			case R.id.check_cha:
			{
				IntentToWards(RepairSearchActivity.class);
			}
			break;

			default:
				break;
		}
	}
	/**
	 * activity跳转
	 * @param cls
	 */
	private void IntentToWards(Class<?>cls)
	{
		Intent intent=new Intent();
		intent.setClass(RepairIndexActivity.this, cls);
		intent.putExtra("ClassNamecn", "RepairIndexActivity");
		startActivity(intent);
	}

}