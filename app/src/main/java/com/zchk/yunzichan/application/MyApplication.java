package com.zchk.yunzichan.application;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.zchk.yunzichan.db.DatabaseHelper;
import com.zchk.yunzichan.entity.bean.UserInfo;
import com.zchk.yunzichan.location.share;
import com.zchk.yunzichan.ui.activity.main.MainActivity;
import com.zchk.yunzichan.utils.ToastUtil;


/**
 * @author SenseTech
 * 
 */
public class MyApplication extends Application implements Thread.UncaughtExceptionHandler{
	private static MyApplication instance;

	public int mode;
	public static final int ON_LINE = 0;
	public static final int OFF_LINE = 1;

	public static MyApplication getInstance() {
		return instance;
	}

	private SharedPreferences sp;
	DatabaseHelper helper;
	private SQLiteDatabase db;
	private int dbVersion;
	public static UserInfo userInfo;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		createDatabase();
		instance = this;

		mode = ON_LINE;
		share.setMode(getApplicationContext(), mode);
		//设置全局异常捕获
//		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 *
	 */
	private void createDatabase() {
		// TODO Auto-generated method stub
		sp = getSharedPreferences("CSHCache", MODE_PRIVATE);
		int v = sp.getInt("max",1);
		helper = new DatabaseHelper(getApplicationContext(), "DevOpsDevelop1",v);
		helper.getWritableDatabase();
	}

	public DatabaseHelper getHelper() {
		return helper;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		//当捕获异常的时候此方法将会被调用,此时做相应的操作
		ToastUtil.showToast(this,"抱歉程序出现未知异常");
		System.exit(0);
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
				Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
