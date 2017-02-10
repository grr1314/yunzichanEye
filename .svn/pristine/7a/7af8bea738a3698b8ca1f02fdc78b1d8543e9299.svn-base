package com.zchk.yunzichan.ui.activity.repair;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.utils.JsonTools;

/**
 * 报修信息详情
 *
 * @author SenseTech
 *
 */
public class RepairDetailActivity extends BaseActivity {

	private MaintenanceOrderProperty items;
	private String dataStr;
	private TextView tv_Ordercode;
	private TextView tv_reportPerson;
	private TextView tv_telephone;
	private TextView tv_deviceNameCn;
	private TextView tv_deviceId;
	private TextView tv_building;
	private TextView tv_faultReport;
	private TextView tv_repairUser;

	private TextView tv_position;
	private TextView tv_disposeTime;
	private TextView tv_status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repair_detial_activity);
		Intent intent = getIntent();
		dataStr = intent.getStringExtra("listItem");
		initViews();
		initTopBar("详情", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void initViews() {
		// TODO Auto-generated method stub
		tv_Ordercode = (TextView) findViewById(R.id.tv_Ordercode);
		tv_reportPerson = (TextView) findViewById(R.id.tv_reportPerson);
		tv_telephone = (TextView) findViewById(R.id.tv_telephone);
		tv_deviceNameCn = (TextView) findViewById(R.id.tv_deviceNameCn);
		tv_reportPerson = (TextView) findViewById(R.id.tv_reportPerson);

		tv_deviceId = (TextView) findViewById(R.id.tv_deviceId);
		tv_building = (TextView) findViewById(R.id.tv_building);
		tv_faultReport = (TextView) findViewById(R.id.tv_faultReport);
		tv_repairUser = (TextView) findViewById(R.id.tv_repairUser);

		tv_position = (TextView) findViewById(R.id.tv_position);
		tv_disposeTime = (TextView) findViewById(R.id.tv_disposeTime);
		tv_status = (TextView) findViewById(R.id.tv_status);

		putData();
	}

	/**
	 * 装填数据
	 */
	private void putData() {
		// TODO Auto-generated method stub
		items = (MaintenanceOrderProperty) JsonTools.JsonToStruts(dataStr,
				MaintenanceOrderProperty.class);

		if (items == null) {
			return;
		}
		tv_Ordercode.setText("工单编号：" + items.orderCode);
		tv_reportPerson.setText("报修人：" + items.reportPerson);
		tv_telephone.setText("报修电话：" + items.telephone);
		tv_deviceNameCn.setText("设备名称:" + items.deviceNameCn);
		tv_deviceId.setText("设备编号:" + items.deviceId);
		tv_building.setText("所属建筑:" + items.building);
		tv_faultReport.setText("故障描述:" + items.faultReport);
		tv_repairUser.setText("维修人:" + items.repairUser);
		tv_position.setText("安装位置:" + items.position);
		tv_disposeTime.setText("申请时间:" + items.disposeTime);
		tv_status.setText("状态:" + items.status);
	}
}
