package com.zchk.yunzichan.ui.activity.main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.zchk.yunzichan.R;
import com.zchk.yunzichan.application.MyApplication;
import com.zchk.yunzichan.db.DBUtils;
import com.zchk.yunzichan.entity.bean.UserInfo;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.ui.activity.BaseActivity;
import com.zchk.yunzichan.ui.activity.login.Login_Activity;
import com.zchk.yunzichan.utils.NetWorkUtils;

/**
 * app首启动界面 职责：1、广告宣传 2、进行耗时的初始化加载 3、检查用户的登录状态 4、检测版本更新
 *
 * @author SenseTech
 *
 */
public class IndexActivity extends BaseActivity {

	private static final int SUCCESS = 0;// 表示成功
	private static final int FAIL = 1;// 表示失败
	private static final int OFFLINE = 2;// 表示进入离线模式

	private static final long waitTime = 3000;// 默认的等待时间
	private int time = (int) (waitTime / 1000);// 倒计时所用的时间

	MyApplication application;
	DBUtils dbu;

	private TextView tv_time;
	private Thread wait;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index_activity);
		dbu = new DBUtils(initApplication());
		initViews();
		hideTopBar();
		wait = new ThreadWait();
		wait.start();
		checkNetWorkConnection();
		// 启动ui动画

	}

	private void initViews() {
		// TODO Auto-generated method stub
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_time.setText("倒计时：" + time);
	}

	/**
	 * 检测是否含有新版本
	 */
	private void checkNewVersion() {

	}

	/**
	 * 检测网络连接状态
	 */
	private void checkNetWorkConnection() {
		boolean isConn = NetWorkUtils.isNetworkConnected(IndexActivity.this);
		if (isConn) {// 有网处于在线状态
			share.setMode(IndexActivity.this, 0);
			return;
		}
		share.setMode(IndexActivity.this, 1);

	}

	final Handler handler = new Handler() { // handle
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0: {
					time--;
					if (time < 0) {
						tv_time.setVisibility(View.GONE);
						return;
					}
					tv_time.setText("倒计时：" + time);
				}
				break;
				case 1:
					// 检查登录状态，以及是否是首次启动app
					boolean b = share.getLoginStatus(IndexActivity.this);
					if (b) {
						// activity跳转
						IntentToWards(MainActivity.class);
						UserInfo infos = share.getUserInfo(IndexActivity.this);
						MyApplication.userInfo = infos;
						return;
					}
					IntentToWards(Login_Activity.class);
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * 进行耗时加载
	 *
	 * @author SenseTech
	 *
	 */
	class ThreadWait extends Thread {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (true) {
					sleep(1000);
					handler.sendEmptyMessage(0);
					if (time == 0) {
						handler.sendEmptyMessage(1);
					}
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * activity跳转
	 *
	 * @param cls
	 */
	private void IntentToWards(Class<?> cls) {
		Intent intent = new Intent();
		intent.setClass(IndexActivity.this, cls);
		startActivity(intent);
		finish();
	}

}