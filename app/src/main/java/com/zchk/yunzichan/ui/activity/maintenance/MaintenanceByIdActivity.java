package com.zchk.yunzichan.ui.activity.maintenance;

import com.alibaba.fastjson.JSON;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.entity.model.check.CheckDeviceInfosByDeviceIdMessage;
import com.zchk.yunzichan.entity.model.maintenance.MaintainDeviceInfosByDeviceIdMessage;
import com.zchk.yunzichan.http.HttpRequest;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.ToastUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MaintenanceByIdActivity extends BaseActivity {

	private String url = GlobalDefine.CmdPath.cmdPath
			+ "MaintainInfosByDeviceIdAddCmd";

	private Thread newThread;
	private Handler handler;
	private Runnable networkTask;

	private String respJson;

	private MaintainDeviceInfosByDeviceIdMessage persons;

	public EditText deviceid;
	public Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.maintenance_byid);
		initView();
		initEven();
		handlerByID();
	}

	public void initView() {
		deviceid = (EditText) findViewById(R.id.maintenance_text);
		button = (Button) findViewById(R.id.maintenance_button_luru);

		initTopBar("设备维修", true, false, true);
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	public void initEven() {
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (checkMode()==Constant.NETWORKOFF) {
					ToastUtil.showToast(getApplicationContext(), "请检查网络连接");
					return;
				}
				initThread();
			}
		});
	}

	public void initThread() {
		newThread = new Thread(networkTask);
		newThread.start();
	}

	public void handlerByID() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				Bundle data = msg.getData();
				String val = data.getString("json");
				respJson = val;
				Log.i("mylog", "请求结果为-->" + val);
				// UI界面的更新等相关操作
				if (!val.equals("")) {
					JsonAnalysis(respJson);
					if (persons.responseCommand.equals("OK")) {
						IntentFinish();
					} else if (persons.responseCommand.equals("noDeviceId")) {
						Toast.makeText(MaintenanceByIdActivity.this,
								"系统中无此设备ID", Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(MaintenanceByIdActivity.this,
								"服务器出现故障，请重新提交。", Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(MaintenanceByIdActivity.this,
							"抱歉！当前处于断线状态。", Toast.LENGTH_SHORT).show();
					IntentFinish();
				}
			}
		};

		/* 网络操作相关的子线程 */
		networkTask = new Runnable() {

			@Override
			public void run() {
				// 在这里进行 http request.网络请求相关操作
				CheckDeviceInfosByDeviceIdMessage request = new CheckDeviceInfosByDeviceIdMessage();
				request.deviceId = deviceid.getText().toString().trim();
				request.userName = initApplication().userInfo.getAccount();

				String param = JsonTool(request);
				System.out.println("param JsonStr:" + param);
				String resp = HttpRequest.sendPost(url.toString(), param);

				Message msg = new Message();
				Bundle data = new Bundle();
				data.putString("json", resp);
				msg.setData(data);
				handler.sendMessage(msg);
			}
		};
	}

	public void IntentFinish() {
		String deviceID = deviceid.getText().toString();
		Intent intent = new Intent();
		intent.putExtra("ID", deviceID);
		intent.setClass(MaintenanceByIdActivity.this,
				MaintenanceInsertActivity.class);
		startActivity(intent);
		// 设置切换动画，从左边进入，右边退出
		overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		this.finish();
	}

	// ARRAY to JSON
	private String JsonTool(CheckDeviceInfosByDeviceIdMessage request) {

		String jsonString = JSON.toJSONString(request);

		int lastIndexOf = jsonString.indexOf("null");
		while (lastIndexOf != -1) {

			if (lastIndexOf != -1) {
				jsonString = jsonString.substring(0, lastIndexOf)
						+ jsonString.substring(lastIndexOf + 4,
						jsonString.length());
			}
			if (lastIndexOf == -1) {
				break;
			}
			lastIndexOf = jsonString.indexOf("null");
		}
		System.out.println("集合对象生成:" + jsonString);

		return jsonString;
	}

	// JSON to ARRAY
	private void JsonAnalysis(String jsonString) {
		persons = JSON.parseObject(jsonString,
				MaintainDeviceInfosByDeviceIdMessage.class);
	}
}
