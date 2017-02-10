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
import com.zchk.yunzichan.ui.activity.check.CheckByIdActivity;
import com.zchk.yunzichan.ui.activity.check.CheckQueryActivity;

/**
 * 点检页面
 *
 * @author SenseTech
 *
 */
public class HomeCheckIndexActivity extends BaseActivity implements
		OnClickListener {

	public LinearLayout check_sao_cha;
	public TextView check_deviceid;
	public ImageView check_sao;
	public ImageView check_cha;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_index);
		initView();
		initListener();
	}

	public void initView() {
		check_sao_cha = (LinearLayout) findViewById(R.id.check_sao_cha);
		check_deviceid = (TextView) findViewById(R.id.check_deviceid);
		check_sao = (ImageView) findViewById(R.id.check_sao);
		check_cha = (ImageView) findViewById(R.id.check_cha);

		initTopBar("设备巡检", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void initListener() {
		// 无法识别二维码，只能通过输入设备ID来查询点检信息
		check_deviceid.setOnClickListener(this);
		// 扫一扫
		check_sao.setOnClickListener(this);
		// 查询，是批量查询点检信息
		check_cha.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.check_sao: {
				intentFinish(R.id.check_sao, MipcaActivityCapture.class);
			}
			break;
			case R.id.check_cha: {
				intentFinish(R.id.check_cha, CheckQueryActivity.class);
			}
			break;

			case R.id.check_deviceid: {
				intentFinish(R.id.check_sao, CheckByIdActivity.class);
			}

			default:
				break;
		}
	}

	/**
	 * 进入扫码界面
	 */
	public void intentFinish(int pos, Class cls) {
		Intent intent = new Intent();
		intent.setClass(HomeCheckIndexActivity.this, cls);
		if (pos == R.id.check_sao) {
			intent.putExtra("ClassNamecn", "HomeCheckIndexActivity");
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

}
