package com.zchk.yunzichan.ui.activity.repair;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

import com.alibaba.fastjson.JSON;
import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.entity.model.repair.ApplyMaintenancePropertyMessage;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderProperty;
import com.zchk.yunzichan.entity.model.repair.MaintenanceOrderPropertyView;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.adapter.RepairAdapter;
import com.zchk.yunzichan.utils.Constant;
import com.zchk.yunzichan.utils.GlobalDefine;
import com.zchk.yunzichan.utils.JsonTools;
import com.zhy.http.okhttp.callback.StringCallback;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

/**
		* 報修主界面
		*
		* @author SenseTech
		*
		*/
public class RepairSearchActivity extends BaseActivity implements
		View.OnClickListener, OnItemClickListener {
	private static final String TAG = "RepairSearchActivity";

	// 查询报修单信息
	private static final String IP = GlobalDefine.CmdPath.cmdPath
			+ "MaintenanceOrderSearchCmd";

	ListView lv_repair;
	// 空数据时候显示的提示文字
	TextView tv_null;

	MaintenanceOrderPropertyView persons;
	private List<String> list;

	private RepairAdapter adapter;

	List<MaintenanceOrderProperty> ls = new ArrayList<MaintenanceOrderProperty>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repair_search_activity);
		initViews();
		initListeners();
		loadData();

	}

	private void initViews() {
		// TODO Auto-generated method stub
		lv_repair = (ListView) findViewById(R.id.lv_repair);
		// lv_repair.setSelector(Color.rgb(0, 0, 0));
		tv_null = (TextView) findViewById(R.id.tv_null);
		// pb_load = (ProgressBar) findViewById(R.id.pb_load);
		// 空数据显示提示
		// isShowProgressBar(true);
		// 初始化topbar
		initTopBar("报修查询", true, false, true);
		// 初始化topbar的事件
		initTopBarListener(null, null, new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	/**
	 * 加载数据
	 */
	private void loadData() {
		// TODO Auto-generated method stub
		showDialog();
		if (checkMode() == Constant.NETWORKOFF) {
			// 如果处于离线，查询本地数据
			LoadLocData();
			return;
		}
		// 加载网络数据
		initData();
	}

	class ThreadWait extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				// 模拟3秒钟的加载时间
				sleep(3000);
				DBUtils dbu = new DBUtils(initApplication());
				MaintenanceOrderProperty mop;
				list = dbu.selectRepair();
				ls.clear();
				for (int i = 0; i < list.size(); i++) {
					mop = JSON.parseObject(list.get(i),
							MaintenanceOrderProperty.class);
					ls.add(mop);
				}
				Message message = Message.obtain(handler, 1, ls);
				message.sendToTarget();

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 查询本地数据
	 */
	private void LoadLocData() {
		// TODO Auto-generated method stub
		new ThreadWait().start();
	}

	/**
	 * 初始化事件
	 */
	private void initListeners() {
		// TODO Auto-generated method stub
		lv_repair.setOnItemClickListener(this);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			dialogDismiss();
			switch (msg.what) {
				case 1: {
					@SuppressWarnings("unchecked")
					List<MaintenanceOrderProperty> ls = (List<MaintenanceOrderProperty>) msg.obj;
					adapter = new RepairAdapter(ls, RepairSearchActivity.this);
					lv_repair.setAdapter(adapter);
					dialogDismiss();
				}
				break;

				default:
					break;
			}
		}
	};

	/**
	 * 将数据填充到界面中
	 */
	private void putData() {
		// TODO Auto-generated method stub
		ls.clear();
		for (int i = 0; i < persons.items.length; i++) {
			ls.add(persons.items[i]);
		}
		adapter = new RepairAdapter(ls, RepairSearchActivity.this);
		lv_repair.setAdapter(adapter);
	};

	/**
	 * 显示listView的null
	 *
	 * @param isShow
	 */
	protected void isShowDataNull(boolean isShow) {
		// TODO Auto-generated method stub
		if (isShow) {
			// 表示显示
			tv_null.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 判断返回的数据是否为空
	 *
	 * @param res
	 * @return
	 */
	@SuppressLint("NewApi")
	protected boolean isShow(String res) {
		// TODO Auto-generated method stub
		if (res.equals("") && res.isEmpty()) {
			// false
			return false;
		}
		return true;
	}

	/**
	 * 加载数据
	 */
	private void initData() {
		// TODO Auto-generated method stub
		ApplyMaintenancePropertyMessage request = new ApplyMaintenancePropertyMessage();
		// 暂时将以下数据写死
		request.userAccountName = MyApplication.userInfo.getAccount();
		request.status = 0;
		request.startTime = "2016-06-10";
		request.endTime = "2016-07-10";
		request.address = "";
		request.keyword = "";
		String param = JsonTools.StringToJson_Repair(request);

		http(IP, 1, param, new MyStringCallback());
	}

	/**
	 * Callback
	 *
	 * @author SenseTech
	 *
	 */
	public class MyStringCallback extends StringCallback {
		@Override
		public void onBefore(Request request,int code) {
			super.onBefore(request,code);
			showDialog();
		}

		@Override
		public void onAfter(int code) {
			super.onAfter(code);
			dialogDismiss();
		}

		@Override
		public void onError(Call call, Exception e,int code) {
			// show Error page
		}

		@Override
		public void onResponse(String response,int code) {
			Log.e(TAG,response);
			// 返回的结果
			persons = (MaintenanceOrderPropertyView) JsonTools.JsonToStruts(
					response, MaintenanceOrderPropertyView.class);
			if (persons.responseCommand.equals("OK")) {
				if (persons.items.length == 0) {
					// 设置空数据图片
					isShowDataNull(true);
					lv_repair.setEmptyView(tv_null);
					return;
				}
				putData();
			} else {
				// 设置空数据图片
				isShowDataNull(true);
				lv_repair.setEmptyView(tv_null);
			}
		}
}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
//			case R.id.iv_back: {
//				finish();
//			}
			default:
				break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
							long id) {
		// TODO Auto-generated method stub
		IntentToWords(position);
	}

	/**
	 * 页面跳转
	 *
	 * @param pos
	 */
	private void IntentToWords(int pos) {
		// TODO Auto-generated method stub
		ArrayList<MaintenanceOrderProperty> list = new ArrayList<MaintenanceOrderProperty>();
		MaintenanceOrderProperty item = null;
		if (checkMode() == Constant.NETWORKONLINE) {
			for (int i = 0; i < persons.items.length; i++) {
				list.add(persons.items[i]);
			}
			item = (MaintenanceOrderProperty) list.get(pos);
		} else {
			for (int i = 0; i < ls.size(); i++) {
				item = (MaintenanceOrderProperty) ls.get(pos);
			}
		}

		String str = JsonTools.StringToJson_Back(item);
		Intent intent = new Intent();
		intent.setClass(RepairSearchActivity.this, RepairDetailActivity.class);
		intent.putExtra("listItem", str);
		startActivity(intent);
	}

}

